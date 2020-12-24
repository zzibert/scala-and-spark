package lectures

import scala.util.Try

object DarkSugars extends App {

  // methods with single params
  def singleArgMethod(arg: Int): String = s"$arg little ducks..."

  val description = singleArgMethod {
    // write some code
    42
  }

  val aTryInstance = Try { // java's try {...}
    throw new RuntimeException
  }

  List(1, 2, 3).map { x =>
    x + 1
  }

  // syntax sugar #2: single abstract method
  trait Action {
    def act(x: Int): Int
  }

  val anInstance: Action = new Action {
    override def act(x: Int): Int = x + 1
  }

  val aFunkyInstance: Action = (x: Int) => x + 1 // magic

  // examplke: Runnables
  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("hello, Scala")
  })

  val aSweeterThread = new Thread(() => println("sweet, Scala!"))

  abstract class AnAbstractType {
    def implemented: Int = 23
    def f(a: Int): Unit
  }

  val anAbstractInstance: AnAbstractType = (a: Int) => println("sweet")

  // syntax sugar #3 the :: and #:: methods are special

  val prependedList = 2 :: List(3, 4)

  // List(3,4).::(2)
  // scala spec: last char decides associativity of method
  1 :: 2 :: 3 :: List(4, 5)
  List(4, 5).::(3).::(2).::(1)

  class MyStream[T] {
    def -->:(value: T): MyStream[T] = this
  }

  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]

  // syntax sugar #4 multi-word method naming

  // syntax sugar #5: infix types
  class Composite[A, B]
  val composite: Int Composite String = ???

  class -->[A, B]
  val towards: Int --> String = ???

  // syntax sughar #6 update() is very special like apply

  val anArray = Array(1, 2, 3)
  anArray(2) = 7 // anArray.update(2, 7)

  // syntax sugar #7 setters for mutable containes
  class Mutable {
    private var internalMember: Int = 0 // private for OO encapsulation
    def member: Unit = internalMember
    def member_=(value: Int): Unit =
      internalMember = value // setter
  }

  val aMutableContainer = new Mutable
  aMutableContainer.member = 42 // rewritten as aMutableContainer.member_=(42)
}


