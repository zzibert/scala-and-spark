package exercises

abstract class Maybe[+T] {
  def isEmpty: Boolean
  def map[B](transformer: T => B): Maybe[B]
  def flatMap[B](transformer: T => Maybe[B]): Maybe[B]
  def filter(predicate: T => Boolean): Maybe[T]
}

case object MaybeNot extends Maybe[Nothing] {
  def isEmpty: Boolean = true
  def map[B](transformer: Nothing => B): Maybe[B] = MaybeNot
  def flatMap[B](transformer: Nothing => Maybe[B]): Maybe[B] = MaybeNot
  def filter(predicate: Nothing => Boolean): Maybe[Nothing] = MaybeNot
}

case class Just[+T](value: T) extends Maybe[T] {
  def isEmpty: Boolean = false
  def map[B](transformer: T => B): Maybe[B] = Just(transformer(value))
  def flatMap[B](transformer: T => Maybe[B]): Maybe[B] = transformer(value)
  def filter(predicate: T => Boolean): Maybe[T] = {
    if (predicate(value)) {
      this
    } else {
      MaybeNot
    }
  }
}

object MaybeTest extends App {
  val just3 = Just(3)
  println(just3) // just3
  println(just3.map(_ * 2)) // Just(6)
  println(just3.flatMap(x => Just(x % 2 == 0))) // Just(false)
  println(just3.filter(_ % 2 == 0)) // NaybeNot
}
