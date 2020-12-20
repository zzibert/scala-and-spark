package exercises

object Exercise extends App {

  var listOfIntegers: MyList[Int] = new Cons(2, new Cons(3, Empty))
  listOfIntegers = listOfIntegers.add(1)

  println(listOfIntegers)

  val doubler: (Int => Int) =  {
    element => element * 2
  }


  val isOdd: (Int => Boolean) =  {
    element => element % 2 == 1
  }

  val nAndNPlus: (Int => MyList[Int]) = {
    element => new Cons[Int](element, new Cons[Int](element+1, Empty))
  }

  println(listOfIntegers.map(doubler))

  println(listOfIntegers.filter(isOdd))

  println(listOfIntegers.map(nAndNPlus))

  println(listOfIntegers.flatMap(nAndNPlus))

  var anotherList = new Cons[Int](4, listOfIntegers)

  var thirdList = anotherList.copy()

  println(thirdList.flatMap(nAndNPlus))

  val print: (Int => Unit) = x => println(x)

  println(thirdList)

  val reversedThirdList = thirdList.sort((x, y) => x - y)

  val listOfString = new Cons("one", new Cons("two", new Cons("three", new Cons("four", Empty))))

  println(reversedThirdList.zipWith(thirdList, (x: Int, y: Int) => x * y))

  println(thirdList.zipWith(listOfString, (x: Int, word: String) => x + " " + word))

  println(thirdList.fold(0)(_ + _))

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
  def forEach(transformer: A => Unit): Unit
  def sort(compare: (A, A) => Int): MyList[A]
  def zipWith[B, C](secondList: MyList[B], adder: (A, B) => C): MyList[C]
  def fold[B >: A](start: B)(func: (A, B) => B): B

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

  def forEach(transformer: Nothing => Unit): Unit = ()

  def sort(compare: (Nothing, Nothing) => Int) = Empty

  def zipWith[B, C](secondList: MyList[B], adder: (Nothing, B) => C): MyList[C] = Empty

  def fold[B >: Nothing](start: B)(func: (Nothing, B) => B): B = start
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

  def forEach(transformer: A => Unit): Unit = {
    transformer(h)
    t.forEach(transformer)
  }

  def sort(compare: (A, A) => Int): MyList[A] = {
    def insert(x: A, sortedList: MyList[A]): MyList[A] =
      if (sortedList.isEmpty) new Cons(x, Empty)
      else if (compare(x, sortedList.head) <= 0)  new Cons(x, sortedList)
      else new Cons(sortedList.head, insert(x, sortedList.tail))

    val sortedTail = t.sort(compare)
    insert(h, sortedTail)
  }

  def zipWith[B, C](secondList: MyList[B], zip: (A, B) => C): MyList[C] = {
    new Cons[C](zip(h, secondList.head), t.zipWith(secondList.tail, zip))
  }

  def fold[B >: A](start: B)(func: (A, B) => B): B = {
    t.fold(func(h, start))(func)
  }

}




