package lectures

object Monads extends App {

  // our own Tru monad

  trait Attempt[+A] {
    def flatMap[B](f: A => Attempt[B]): Attempt[B]
  }

  object Attempt {
    def apply[A](a: => A): Attempt[A] = try {
      Success(a)
    } catch {
      case e: Throwable => Fail(e)
    }
  }

  case class Success[A](value: A) extends Attempt[A] {
    def flatMap[B](f: A => Attempt[B]): Attempt[B] = try {
      f(value)
    } catch {
      case e: Throwable => Fail(e)
    }
  }

  case class Fail(e: Throwable) extends Attempt[Nothing] {
    def flatMap[B](f: Nothing => Attempt[B]): Attempt[B] = this
  }

  // left-identity
  // unit.flatMap(f) = f(x)


  // 1 .Exercise implement a lazy[T] monad = computation which will only be executed when its needed
  // unit/apply
  // flatMap
  // 2. Monads = unit + flatmap
  /*
  * monads = unit + map + flatten
  * def map[B](f: T => B): Monad[B] = ???
  * def flatten(m: Monad[Monad[T]]): Monad[T] = ???
  * */


}
