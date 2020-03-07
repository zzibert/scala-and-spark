package exercises

object Exercises extends App {
  val listOfIntegers: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
  val listOfStrings: MyList[String] = new Cons("a", new Cons("b", new Cons("c", Empty)))
  println(listOfIntegers)
  println(listOfStrings)
}

abstract class MyList[+A] {
  // head - first element of the list
  def head(): A
  // tail - remainder of the list
  def tail(): MyList[A]
  // isEmpty: Boolean
  def isEmpty(): Boolean
  // add: Int -> new List with element added
  def add[B >: A](n: B): MyList[B]
  // toString -> a string representation
  def printElements: String
  override def toString: String = "[" + printElements + "]"
}

class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  def head(): A = h
  def tail: MyList[A] = t
  def isEmpty(): Boolean = false
  def add[B >: A](element: B): MyList[B] = new Cons[B](element, this)
  def printElements: String = {
    if (t.isEmpty) "" + h
    else h + " " + t.printElements
  }
}

object Empty extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: Nothing = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)
  def printElements: String = ""
}
