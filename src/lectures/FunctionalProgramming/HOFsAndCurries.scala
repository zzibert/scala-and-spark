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

}
