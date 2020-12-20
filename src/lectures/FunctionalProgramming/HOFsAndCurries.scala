package lectures.FunctionalProgramming

object HOFsAndCurries extends App {

  // function that applies a function n times over a value x
  //nTimes(f, n, x)
  //nTimes(f, 3, x) => f(f(f(x)))

  def nTimes(f: (Int => Int), n: Int, x: Int): Int = {
    if (n <= 1) f(x)
    else nTimes(f, n-1, f(x))
  }

  println(nTimes(_ * 3, 3, 1))

  def nTimesBetter(f: Int => Int, n: Int): (Int => Int) = { (x: Int) =>
    if (n <= 1) f(x)
    else nTimesBetter(f, n - 1)(f(x))
  }

  val multiplyByTwoThreeTimes = nTimesBetter(_ * 2, 3)

  println(multiplyByTwoThreeTimes(1))
  println(multiplyByTwoThreeTimes(2))
  println(multiplyByTwoThreeTimes(3))

  // curried functions
  val superAdder: Int => (Int => Int) = (x: Int) => (y: Int) => x + y
  val add3 = superAdder(3)
  println(add3(10))

  // functions with multiple parameter lists
  def curriedFormatter(c: String)(x: Double): String = c.format(x)

  val standardFormat: (Double => String) = curriedFormatter("%4.2f")
  val preciseFormat: (Double => String) = curriedFormatter("%10.8f")

  println(standardFormat(Math.PI))
  println(preciseFormat(Math.PI))

  // 1. Expand MyList
    // foreach method  A => Unit
    // [1, 2, 3].foreach(x => println(x))
    // sort function ((A, A) => Int) => MyList
    // [1, 2, 3].sort((x, y) => y - x) => [3, 2, 1]
    // zipWith (list, (A, A) => B) => MyList[B] DONE
    // [1, 2, 3].zipWith([4, 5, 6], x * y) => [4, 10, 18] DONE
    // fold (start)(function) => a value
    // [1, 2, 3].fold(0)(x + y) = 6

  // 2. toCurry(f: (Int, Int) => Int) =>  (Int => Int => Int) DONE
  // fromCurry(f: (Int => Int => Int)) => (Int, Int) => Int DONE

  def toCurry(f: (Int, Int) => Int): (Int => Int => Int) = {
    (x: Int) => (y: Int) => f(x, y)
  }

  val multiply = toCurry(_ + _)

  println(multiply(10)(11))

  def fromCurry(f: (Int => Int => Int)): (Int, Int) => Int = {
    (x: Int, y: Int) => f(x)(y)
  }

  val nonSuperAdder = fromCurry(superAdder)

  println(nonSuperAdder(10, 3))



  // 3. compose(f, g) => f(g(x))
  // andThen(f, g) => x => g(f(x))

  def compose[A, B, C](f: B => C, g: A => B): A => C = {
    (x: A) => f(g(x))
  }

  def andThen[A, B, C](f: A => B, g: B => C): A => C = {
    (x: A) => g(f(x))
  }

  val firstAdd2ThenMultiply2 = compose((x: Int) => x * 2, (y: Int) => y + 2)

  val firstMultiplyBy2ThenAdd2 = andThen((x: Int) => x * 2, (y: Int) => y + 2)

  def superAdder2: (Int => Int => Int) = toCurry(_ + _)

  def add4 = superAdder2(4)
  println(add4(17))

  val simpleAdder = fromCurry(superAdder)
  println(simpleAdder(4, 17))

  val add2 = (x: Int) => x + 2

  val time3 = (x: Int) => x * 3

  val composed = compose(add2, time3)

  val ordered = andThen(add2, time3)

  println(composed(4))

  println(ordered(4))

}
