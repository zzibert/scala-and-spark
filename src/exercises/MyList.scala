package exercises

object Exercises extends App {
  val listOfIntegers: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
  val listOfStrings: MyList[String] = new Cons("a", new Cons("b", new Cons("c", Empty)))
  println(listOfIntegers.filter(EvenPredicate))
  println(listOfIntegers.filter(OddPredicate))
  println(listOfIntegers.map(MultiplyTwo))
  println(listOfIntegers.map(MultiplyThree))

  println(listOfIntegers.flatMap(ListGen))
}

trait MyPredicate[-T] {
  def test(t: T): Boolean
}

trait MyTransformer[-A, B] {
  def transform(a: A): B
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
  def map[B](transformer: MyTransformer[A, B]): MyList[B]
  def ++[B >: A](list: MyList[B]): MyList[B]
  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B]
  def filter(predicate: MyPredicate[A]): MyList[A]
  def printElements: String
  // toString -> a string representation
  override def toString: String = "[" + printElements + "]"
}

case class Cons[+A](h: A, t: MyList[A]) extends MyList[A]  {
  def head(): A = h
  def tail: MyList[A] = t
  def isEmpty(): Boolean = false
  def add[B >: A](element: B): MyList[B] = new Cons[B](element, this)
  def filter(predicate: MyPredicate[A]): MyList[A] = {
    if (predicate.test(h)) new Cons[A](h, t.filter(predicate))
    else t.filter(predicate)
  }
  def map[B](transformer: MyTransformer[A, B]): MyList[B] = {
    new Cons[B](transformer.transform(h), t.map(transformer))
  }
  def ++[B >: A](list: MyList[B]): MyList[B] = new Cons(h, tail ++ list)
  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] = {
    transformer.transform(h) ++ t.flatMap(transformer)
  }
  def printElements: String = {
    if (t.isEmpty) "" + h
    else h + " " + t.printElements
  }
}

case object Empty extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: Nothing = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)
  def map[B](transformer: MyTransformer[Nothing, B]): MyList[Nothing] = Empty
  def ++[B](list: MyList[B]): MyList[B] = list
  def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = Empty
  def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = Empty
  def printElements: String = ""
}

object EvenPredicate extends MyPredicate[Int] {
  def test(element: Int) = {
    element % 2 == 0
  }
}

object OddPredicate extends  MyPredicate[Int] {
  def test(element: Int) = {
    element % 2 != 0
  }
}

object MultiplyTwo extends MyTransformer[Int, Int] {
  def transform(element: Int) = 2 * element
}

object MultiplyThree extends MyTransformer[Int, Int] {
  def transform(element: Int) = 3 * element
}

object ListGen extends MyTransformer[Int, MyList[Int]] {
  def transform(element: Int) = new Cons[Int](element, new Cons[Int](element+1, Empty))
}
