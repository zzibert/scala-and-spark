package lectures.part3Concurrency

import scala.concurrent.{Await, Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Random, Success, Try}
import scala.concurrent.duration._

object FuturesPromises extends App {

  def calculateMeaningOfLife: Int = {
    Thread.sleep(2000)
    42
  }

  val aFuture = Future {
    calculateMeaningOfLife
  }

//  aFuture onComplete { t =>
//    t match {
//      case Success(value) => println(value)
//      case Failure(exception) => println(exception)
//    }
//  }

//  Thread.sleep(3000)

  case class Profile(id: String, name: String) {
    def poke(anotherProfile: Profile): Unit = println(s"${this.name} poking ${anotherProfile.name}")
  }

  object SocialNetwork {
    // "database"
    val names = Map(
      "fb.id.1-zuck" -> "Mark",
      "fb.id.2-bill" -> "Bill",
      "fb.id.0-dummy" -> "Dummy"
    )
    val friends = Map(
      "fb.id.1-zuck" -> "fb.id.2-bill"
    )
    val random = new Random()

    // API
    def fetchProfile(id: String): Future[Profile] = Future {
      Thread.sleep(random.nextInt(300))
      Profile(id, names(id))
    }

    def fetchBestFriend(profile: Profile): Future[Profile] = Future {
      Thread.sleep(random.nextInt(400))
      val bfId = friends(profile.id)
      Profile(bfId, names(bfId))
    }
  }

  // client mark to poke bill
  val mark = SocialNetwork.fetchProfile("fb.id.1-zuck")

//  mark onComplete {
//    case Success(markProfile) => {
//      val bill = SocialNetwork.fetchBestFriend(markProfile)
//      bill onComplete {
//        case Success(billProfile) => markProfile poke billProfile
//        case Failure(e) => e.printStackTrace()
//      }
//    }
//    case Failure(e) => e.printStackTrace()
//  }

  // functional composition of futures
  // map, flatMap, filter

  val nameOnTheWall = mark.map(profile => profile.name)

  val marksBestFriend = mark.flatMap(profile => SocialNetwork.fetchBestFriend(profile))

  val zucksBestFriendRestricted = marksBestFriend.filter(profile => profile.name.startsWith("Z"))

  // for-comprehensions
  for {
    mark <- SocialNetwork.fetchProfile("fb.id.1-zuck")
    bill <- SocialNetwork.fetchBestFriend(mark)
  } mark.poke(bill)

  // fallbacks
  val aProfileNoMatter = SocialNetwork.fetchProfile("unknown id").recover {
    case e: Throwable => Profile("fb.id.0-dummy", "forever alone")
  }

  val aFetchedProfileNoMatterWhat = SocialNetwork.fetchProfile("unknown id").recoverWith {
    case e: Throwable => SocialNetwork.fetchProfile("fb.id.0-dummy")
  }

  val fallbackResult = SocialNetwork.fetchProfile("unknown id").fallbackTo(SocialNetwork.fetchProfile("fb.id.0-dummy"))

  // online banking app
  case class User(name: String)
  case class Transaction(sender: String, receiver: String, amount: Double, status: String)

  object BankingApp {
    val name = "Rock the JVM banking"

    def fetchUser(name: String): Future[User] = Future {
      // simulate fetching from the DB
      Thread.sleep(500)
      User(name)
    }

    def createTransaction(user: User, merchantName: String, amount: Double): Future[Transaction] = Future {
      // simulate some processes
      Thread.sleep(1000)
      Transaction(user.name, merchantName, amount, "SUCCESS")
    }

    def purchase(username: String, item: String, merchantName: String, cost: Double): String = {
      // fetch the user from the DB
      // create a transaction
      // wait for the transaction to finish
      val transactionStatusFuture = for {
        user <- fetchUser(username)
        transaction <- createTransaction(user, merchantName, cost)
      } yield transaction.status

      Await.result(transactionStatusFuture, 2.seconds)
    }
  }

//  println(BankingApp.purchase("Daniel", "IPhone 12", "rock the jvm store", 3000))

  // PROMISES
  val promise = Promise[Int]()
  val future = promise.future

  // Thread 1 - "consumer"
  future onComplete {
    case Success(r) => println("[consumer] Ive received " + r)
  }

  // thread 2 - "producer"
  val producer = new Thread(() => {
    println("[producer] crunching numbers...")
    Thread.sleep(1000)
    promise.success(42)
    println("[producer] done")
  })

  /*
  * 1. fulfill a future Immediately with a value
  * 2. inSequence(fa, fb) runs fb after it makes sure that fa completed
  * 3. first(fa, fb) => which ever finished first value
  * 4. last(fa, fb) => new future with the last value
  * 5. retryUntil[T](action: () => Future[T], condition: T => Boolean): Future[T]
  * */

  def fullfiledFuture(number: Int, duration: Int) = Future {
    Thread.sleep(duration)
    number
  }

  def inSequence(fa: Future[Int], fb: Future[Int]): Future[Int] = {
    fa.flatMap(a => fb)
  }

  def first[A](fa: Future[A], fb: Future[A]): Future[A] = {
    val p = Promise[A]

    Seq(fa, fb).foreach {future =>
      future onComplete {
        case Success(result) => p.trySuccess(result)
      }
    }

    p.future
  }

  def last[A](fa: Future[A], fb: Future[A]): Future[A] = {
    val first = Promise[A]
    val second = Promise[A]

    val checkAndComplete = (result: Try[A]) =>
      if(!first.tryComplete(result))
        second.complete(result)

    fa.onComplete(checkAndComplete)
    fb.onComplete(checkAndComplete)

    second.future
  }

  // retryUntil: return the first value that satisfies the condition, retry action until
  // run the action, check if the condition is met , if not retry
  def retryUntil[T](action: () => Future[T], condition: T => Boolean): Future[T] = {
    action()
      .filter(condition)
      .recoverWith {
        case _ => retryUntil(action, condition)
      }
  }

   var counter = 0

  val random = new Random()
  val action = () => Future {
    Thread.sleep(5)
    val nextValue = random.nextInt(10000)
    println(s"counter $counter generated " + nextValue)
    counter += 1
    nextValue
  }

  retryUntil[Int](action, _ > 9998).foreach(println)

  Thread.sleep(50000)

}
