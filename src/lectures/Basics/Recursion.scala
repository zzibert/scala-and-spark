package lectures.Basics

object Recursion extends App {

  def factorial(n: Int): Int =
    if (n <= 1) 1
    else {
      println("computing factorial of " + n)
      val result = n * factorial(n-1)
      println("computed factorial of n " + n)

      result
    }

  def anotherFactorial(n: Int): Int = {
    def factHelper(x: Int, accumulator: Int): Int =
      if (x <= 1) accumulator
      else factHelper(x-1, accumulator * x) // tail recursion - use recursive call as the last expression

    factHelper(n, 1)
  }

  // when you need loops , use tail recursion
  println(anotherFactorial(10))

  // 1. concatenate n times using tail recursion
  def concatenate(str: String, n: Int): String = {
    def concHelper(str: String, n: Int, acc: String): String =
      if (n <= 0) acc
      else concHelper(str, n-1, acc + str)

    concHelper(str, n, "")
  }

  // 2. isPrime tail Recursive
  def isPrime(n: Int): Boolean = {
    def isPrimeTailRec(t: Int, isStillPrime: Boolean): Boolean = {
      if (!isStillPrime) false
      else if (t <= 1) true
      else isPrimeTailRec(t - 1, n % t != 0)
    }
    isPrimeTailRec(n / 2, true)
  }

  // 3. fibonacci tail recursive
  def fibonacci(n: Int): Int = {
    def fibHelper(x: Int, fib1: Int, fib2: Int): Int = {
      if (x >= n) fib2
      else fibHelper(x+1, fib2, fib1+fib2)
    }

    fibHelper(2, 1, 1)
  }

  println(fibonacci(8))



}
