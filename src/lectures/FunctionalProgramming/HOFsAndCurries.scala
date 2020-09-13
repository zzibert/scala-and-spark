package lectures.FunctionalProgramming

object HOFsAndCurries extends App {


  // function that applies a function n times over a value x
  val nTimes: (Int, Int, (Int => Int)) => Int = (x, n, func) => {
    if (n < 1) x
    else nTimes(func(x), n-1, func)
  }

  val nTimesSecond: (Int, (Int => Int)) => (Int => Int) = (n, func) => {
    if (n <= 0) (x: Int) => x
    else (x: Int) => nTimesSecond(n-1, func)(func(x))
  }

  val multiplyByTwo: (Int => Int) = x => x * 2

  val addTwo: (Int => Int) = x => x + 2

  val plusOne: Int => Int = _ + 1

  val multiplyThreeTimes = nTimesSecond(3, x => x * 2)
  val divideByTwoFiveTimes = nTimesSecond(5, x => x / 2)

  println(multiplyThreeTimes(2))
  println(divideByTwoFiveTimes(128))

  val increment10 = nTimesSecond(10, plusOne)

  println(increment10(1))

  // curried functions
  val superAdder: Int => Int => Int = (x: Int) => (y: Int) => x + y

  println(superAdder(5)(4))

  val addFive = superAdder(5)

  println(addFive(10))

  // functions with multiple parameter lists
  def curriedFormatter(c: String)(x: Double): String = c.format(x)

  val standardFormat: (Double => String) = curriedFormatter("%4.2f")
  val preciseFormat: (Double => String) = curriedFormatter("%10.8f")

  println(standardFormat(math.Pi))
  println(preciseFormat(math.Pi))


}
