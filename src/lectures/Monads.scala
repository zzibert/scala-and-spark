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

  class Compute[+A](value: =>  A)  {

    lazy val a = value

    def flatMap[B](f: (=> A) => Compute[B]): Compute[B] = {
      f(a)
    }

    def use: A = a

    def map[B](f: A => B): Compute[B] = flatMap(value => Compute(f(value)))
  }

  object Compute {
    def apply[A](a: => A): Compute[A] = new Compute(a)
    def flatten[A](m: Compute[Compute[A]]): Compute[A] = m.flatMap(value => value)
  }

  val lazyInstance = Compute {
    println("dfdfkdkfdfdfd")
    42
  }

  val flatMappedInstance = lazyInstance.flatMap(x => Compute {
    10 * x
  })

  val flatMappedInstance2 = lazyInstance.flatMap(x => Compute {
    10 * x
  })

  flatMappedInstance.use
  flatMappedInstance2.use

}
