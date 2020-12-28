package lectures.part3Concurrency

import java.util.concurrent.Executors

object Intro extends App {
  /*
  * interface Runnable {
  *   public void run()
  * }
  * */
  // JVM threads

  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("running in parallel")
  })

  aThread.start() // gives the signal to the JVM to start a JVM thread
  // create a JVM thread => OS thread
  aThread.join() // blocks until aThread finished running

  val threadHello = new Thread(() => (1 to 5).foreach(_ => println("hello")))
  val threadGoodbye = new Thread(() => (1 to 5).foreach(_ => println("Goodbye")))
//  threadHello.start()
//  threadGoodbye.start()

  // different runs produce different results

  // executors
  val pool = Executors.newFixedThreadPool(10)

  def runInParallel: Int = {
    var x = 0

    val thread1 = new Thread(new Runnable {
      override def run(): Unit = x = 1
    })

    val thread2 = new Thread(new Runnable {
      override def run(): Unit = x = 2
    })

    thread1.start()
    thread2.start()

    x
  }

//  println(for (_ <- 1 to 100) yield runInParallel) // race condition

  class BankAccount(@volatile var amount: Int) {
    override def toString: String = "" + amount
  }

  def buy(account: BankAccount, thing: String, price: Int) = {
    account.amount -= price
//    println("I bought " + thing)
//    println("Account is now " + account)
  }

//  for (_ <- 1 to 10000) {
//    val account = new BankAccount(50000)
//    val thread1 = new Thread(new Runnable {
//      override def run(): Unit = buySafe(account, "shoes", 3000)
//    })
//    val thread2 = new Thread(new Runnable {
//      override def run(): Unit = buySafe(account, "iphone12", 4000)
//    })
//    thread1.start()
//    thread2.start()
//    Thread.sleep(10)
//    if (account.amount != 43000) println("AHA1" + account.amount)
//    println()
//  }

  // option #1 : use synchronized()
  def buySafe(account: BankAccount, thing: String, price: Int) = {
    account.synchronized {
      // no two threads can evaluate this at the same time
      account.amount -= price
      println("i bought " + thing)
      println("i have  " + account.amount)
    }
  }

  // option #2 : use @volatile

  /*
  * construct  50 "inception" threads thread1 -> thread2 -> thread3
  * println(hello from thread #3)
  * IN REVERSE ORDER
  * simple recursive function start and join methods
  * */

  def inception(number: Int): Thread = new Thread(() => {
    if (number <= 50) {
      val newThread = inception(number+1)
      newThread.start()
      newThread.join()
    }
    println("Hello from thread number #" + number)

  })

  inception(1).start()

  var x = 0
  val threads = (1 to 100).map(_ => new Thread(() => x += 1))
  threads.foreach(_.start())

  println(x)

  // What is the biggest value possible for x 100
  // what is the smallest value possible for x 1

  /*
  * sleep fallacy
  * */

  var message = ""
  val awesomeThread = new Thread(() => {
    Thread.sleep(1000)
    message = "Scala is awesome"
  })

  message = "Scala blows"
  awesomeThread.start()
  Thread.sleep(2000)
  println(message)
  // what is the value of the message awesome most of the time
  // is it guaranteed no
  // why why not depends on the distribution of time on different threads



}
