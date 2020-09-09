package exercises

object Exercise extends App {

  var listOfIntegers: MyList[Int] = new Cons(1, new Cons(2, Empty))
  listOfIntegers = listOfIntegers.add(3)

  println(listOfIntegers)

  println(listOfIntegers.map(Double))

  println(listOfIntegers.filter(IsOdd))

  println(listOfIntegers.map(NAndNPlus))

  println(listOfIntegers.flatMap(NAndNPlus))

  var anotherList = new Cons[Int](4, listOfIntegers)

  var thirdList = new Cons[Int](1, new Cons[Int](2, new Cons[Int](3, Empty)))

  println(thirdList.flatMap(NAndNPlus))


}

trait MyPredicate[-T] {
  def test(element: T): Boolean
}

object IsOdd extends MyPredicate[Int] {
  def test(element: Int): Boolean = {
    element % 2 == 1
  }
}

trait MyTransformer[-A, B] {
  def transform(element: A): B
}

object Double extends MyTransformer[Int, Int] {
  def transform(element: Int): Int = element * 2
}

object NAndNPlus extends MyTransformer[Int, MyList[Int]] {
  def transform(element: Int) = new Cons[Int](element, new Cons[Int](element+1, Empty))
}

abstract class MyList[+A] {

  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
  def printElements: String
  override def toString: String = "[" + printElements + "]"

  def map[B](transformer: MyTransformer[A, B]): MyList[B]
  def filter(predicate: MyPredicate[A]): MyList[A]
  def ++[B >: A](secondList: MyList[B]): MyList[B]
  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B]

}

object Empty extends MyList[Nothing] {

  def head: Nothing = throw new NoSuchElementException

  def tail: MyList[Nothing] = throw new NoSuchElementException

  def isEmpty: Boolean = true

  def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)

  def printElements: String = ""

  def map[B](transformer: MyTransformer[Nothing, B]): MyList[B] = Empty

  def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = Empty

  def ++[B >: Nothing](secondList: MyList[B]): MyList[B] = secondList

  def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = Empty
}

class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  def head: A = h

  def tail: MyList[A] = t

  def isEmpty: Boolean = false

  def add[B >: A](element: B): MyList[B] = new Cons(element, this)

  def printElements: String = {
    if (t.isEmpty) s"$h"
    else h + " " + t.printElements
  }

  def map[B](transformer: MyTransformer[A, B]): MyList[B] = {
    new Cons(transformer.transform(h), t.map(transformer))
  }

  def filter(predicate: MyPredicate[A]): MyList[A] = {
    if (predicate.test(h)) new Cons(h, t.filter(predicate))
    else t.filter(predicate)
  }

  def ++[B >: A](secondList: MyList[B]): MyList[B] = new Cons(h, t ++ secondList)


  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] = {
    transformer.transform(h) ++ t.flatMap(transformer)
  }
}




