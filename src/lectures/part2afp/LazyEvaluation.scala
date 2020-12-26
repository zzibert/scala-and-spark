package lectures.part2afp

object LazyEvaluation extends App {

  // lazy delays the evaluation of values
  lazy val x: Int = {
    println("hello")
    42
  }

  println(x)
  println(x)

  //examples of implications
  // side effects
  def sideEffectCondition: Boolean = {
    println("Boo")
    true
  }

  def simpleCondition: Boolean = false

  lazy val LazyCondition = sideEffectCondition

  println(if (simpleCondition && LazyCondition) "yes" else "no")

  // in conjuction with call by name
  // call by need
  def byNameMethod(n: => Int): Int = {
    lazy val t = n
    t + t + t + 1
  }

  def retrieveMagicValue = {
    Thread.sleep(1000)
    println("waiting")
    42
  }

  println(byNameMethod(retrieveMagicValue))

  // use lazy vals


  // filtering with lazy vals
  def lessThan30(i: Int): Boolean = {
    println(s"$i is less than 30?")
    i < 30
  }

  def greaterThan20(i: Int): Boolean = {
    println(s"$i is greater than 20?")
    i > 20
  }

  val numbers = List(1, 25, 40, 5, 23)
  val lt30 = numbers.filter(lessThan30)
  val gt20 = lt30.filter(greaterThan20)
  println(gt20)

  val lt30lazy = numbers.withFilter(lessThan30)
  val gt20lazy = lt30lazy.withFilter(greaterThan20)
  println
  gt20lazy.foreach(println)

  // for-comprehensions use withFilter with guards
  for {
    a <- List(1, 2, 3) if a % 2 == 0 // use lazy vals
  } yield a + 1

  List(1, 2, 3).withFilter(_ % 2 == 0).map(_ + 1) // List[Int]


  // exercise implement a lazily evaluated, singly linked stream of elements.
  // naturals = MyStream.from(1)(x => x + 1) = stream of natural numbers (potentialy infinite!)
  // naturals.take(100).foreach(println)  = lazily evaluated stream of the first 100 naturals (finite stream)
  // naturals.foreach(println) = this will crash because its infinite
  // naturals.map(: * 2) // stream of all even numbers(potentially infinite)
  

  abstract class MyStream[+A] {
    def isEmpty: Boolean
    def head: A
    def tail: MyStream[A]

    def #::[B >: A](element: B): MyStream[B] // prepend operator
    def ++[B >: A](anotherStream: MyStream[B]): MyStream[B] // concatenate two streams

    def foreach(f: A => Unit): Unit
    def map[B](f: A => B): MyStream[B]
    def flatMap[B](f: A => MyStream[B]): MyStream[B]
    def filter(predicate: A => Boolean): MyStream[A]

    def take(n: Int): MyStream[A] // takes the first n elements out of this stream
    def takeAsList(n: Int): List[A]
  }

  object MyStream {
    def from[A](start: A)(generator: A => A): MyStream[A] = ???
  }
}
