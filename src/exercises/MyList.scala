package exercises

object Exercise extends App {

  var list = Empty.add(1)

  list = list.add(2)

  list = list.add(3)

  list = list.add(4)

  list = list.add(5)

  println(list)
}

abstract class MyList {

  def head: Int
  def tail: MyList
  def isEmpty: Boolean
  def add(element: Int): MyList
  def printElements: String
  override def toString: String = "[" + printElements + "]"

}

object Empty extends MyList {

  def head: Int = throw new NoSuchElementException

  def tail: MyList = throw new NoSuchElementException

  def isEmpty: Boolean = true

  def add(element: Int): MyList = new Cons(element, Empty)

  def printElements: String = ""
}

class Cons(h: Int, t: MyList) extends MyList {
  def head: Int = h

  def tail: MyList = t

  def isEmpty: Boolean = false

  def add(element: Int): MyList = new Cons(element, this)

  def printElements: String = {
    if (t.isEmpty) s"$h"
    else h + " " + t.printElements
  }
}




