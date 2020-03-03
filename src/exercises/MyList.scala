package exercises

object Exercises extends App {
  val list = new Cons(1, new Cons(2, new Cons(3, Empty)))
  println(list.tail.head)
  println(list.add(4))
  println(list)
}

abstract class MyList {
  // head - first element of the list
  def head(): Int
  // tail - remainder of the list
  def tail(): MyList
  // isEmpty: Boolean
  def isEmpty(): Boolean
  // add: Int -> new List with element added
  def add(n: Int): MyList
  // toString -> a string representation
  def printElements: String
  override def toString: String = "[" + printElements + "]"
}

class Cons(h: Int, t: MyList) extends MyList {
  def head: Int = h
  def tail: MyList = t
  def isEmpty(): Boolean = false
  def add(element: Int): MyList = new Cons(element, this)
  def printElements: String = {
    if (t.isEmpty) "" + h
    else h + " " + t.printElements
  }
}

object Empty extends MyList {
  def head: Int = throw new NoSuchElementException
  def tail: MyList = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add(element: Int): MyList = new Cons(element, Empty)
  def printElements: String = ""
}
