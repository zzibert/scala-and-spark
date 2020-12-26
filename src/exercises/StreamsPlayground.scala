package exercises

object StreamsPlayground extends App {
  val naturals = MyStream.from(1)(_ + 1)
}

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

    final def toList[B >: A](acc: List[B] = Nil): List[B] =
      if (isEmpty) acc.reverse
      else tail.toList(head :: acc)
  }

  object EmptyStream extends MyStream[Nothing] {
    def isEmpty: Boolean = true
    def head: Nothing = throw new NoSuchElementException
    def tail: Nothing = throw new NoSuchElementException

    def #::[B >: Nothing](element: B): MyStream[B] = new Cons[B](element, this)
    def ++[B >: Nothing](anotherStream: MyStream[B]): MyStream[B] = anotherStream

    def foreach(f: Nothing => Unit): Unit = ()
    def map[B](f: Nothing => B): MyStream[B] = this
    def flatMap[B](f: Nothing => MyStream[B]): MyStream[B] = this
    def filter(predicate: Nothing => Boolean): MyStream[Nothing] = this

    def take(n: Int): MyStream[Nothing] = this // takes the first n elements out of this stream
    def takeAsList(n: Int): List[Nothing] = Nil
  }

  class Cons[+A](h: A, t: => MyStream[A]) extends MyStream[A] {
    def isEmpty: Boolean = false
    override val head: A = h

    override lazy val tail: MyStream[A] = t // call by need

    def #::[B >: A](element: B): MyStream[B] = new Cons[B](element, this)
    def ++[B >: A](anotherStream: MyStream[B]): MyStream[B] = new Cons(head, tail ++ anotherStream)

    def foreach(f: A => Unit): Unit = {
      f(head)
      tail foreach f
    }

    def map[B](f: A => B): MyStream[B] = new Cons[B](f(h), tail.map(f)) // preserves lazy evaluation

    def flatMap[B](f: A => MyStream[B]): MyStream[B] = f(head) ++ tail.flatMap(f)

    def filter(predicate: A => Boolean): MyStream[A] = {
      if(predicate(head)) new Cons(head, tail.filter(predicate))
      else tail.filter(predicate)
    }

    def take(n: Int): MyStream[A] = {
      if (n <= 0) EmptyStream
      else if (n == 1) new Cons(head, EmptyStream)
      else new Cons(head, tail.take(n-1))
    }

    def takeAsList(n: Int): List[A] = {
      if (n <= 0) List()
      else h :: tail.takeAsList(n-1)
    }
  }

  object MyStream {
    def from[A](start: A)(generator: A => A): MyStream[A] = new Cons(start, MyStream.from(generator(start))(generator))
  }

