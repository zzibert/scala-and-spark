package exercises

object Exercise extends App {

  var listOfIntegers: MyList[Int] = new Cons(1, new Cons(2, Empty))
  listOfIntegers = listOfIntegers.add(3)

  println(listOfIntegers)

  val doubler: (Int => Int) = new Function1[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }


  val isOdd: (Int => Boolean) = new Function1[Int, Boolean] {
    override def apply(element: Int) = element % 2 == 1
  }

  val nAndNPlus: (Int => MyList[Int]) = new Function1[Int, MyList[Int]] {
    override def apply(element: Int) = new Cons[Int](element, new Cons[Int](element+1, Empty))
  }

  println(listOfIntegers.map(doubler))

  println(listOfIntegers.filter(isOdd))

  println(listOfIntegers.map(nAndNPlus))

  println(listOfIntegers.flatMap(nAndNPlus))

  var anotherList = new Cons[Int](4, listOfIntegers)

  var thirdList = new Cons[Int](1, new Cons[Int](2, new Cons[Int](3, Empty)))

  println(thirdList.flatMap(nAndNPlus))


}

abstract class MyList[+A] {

  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
  def printElements: String
  override def toString: String = "[" + printElements + "]"

  // Higer order functions
  def map[B](transformer: A => B): MyList[B]
  def filter(predicate: A => Boolean): MyList[A]
  def ++[B >: A](secondList: MyList[B]): MyList[B]
  def flatMap[B](transformer: A => MyList[B]): MyList[B]

}

case object Empty extends MyList[Nothing] {

  def head: Nothing = throw new NoSuchElementException

  def tail: MyList[Nothing] = throw new NoSuchElementException

  def isEmpty: Boolean = true

  def add[B >: Nothing](element: B): MyList[B] = Cons(element, Empty)

  def printElements: String = ""

  def map[B](transformer: Nothing => B): MyList[B] = Empty

  def filter(predicate: Nothing => Boolean): MyList[Nothing] = Empty

  def ++[B >: Nothing](secondList: MyList[B]): MyList[B] = secondList

  def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = Empty
}

case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  def head: A = h

  def tail: MyList[A] = t

  def isEmpty: Boolean = false

  def add[B >: A](element: B): MyList[B] = Cons(element, this)

  def printElements: String = {
    if (t.isEmpty) s"$h"
    else h + " " + t.printElements
  }

  def map[B](transformer: A => B): MyList[B] = {
    Cons(transformer(h), t.map(transformer))
  }

  def filter(predicate: A => Boolean): MyList[A] = {
    if (predicate(h)) Cons(h, t.filter(predicate))
    else t.filter(predicate)
  }

  def ++[B >: A](secondList: MyList[B]): MyList[B] = Cons(h, t ++ secondList)


  def flatMap[B](transformer: A => MyList[B]): MyList[B] = {
    transformer(h) ++ t.flatMap(transformer)
  }
}




