package lectures.part1basics

object Recursion extends App {
  // When you need loop, use tail recursion
  def factorial(n: Int): Int =
    if (n < 1) 1
    else {
      println("Computiong factorial of " + n )
      n * factorial(n-1)
    }
  def anotherFactorial(n: Int): BigInt = {
    def factorialHelper(x: BigInt, accumulator: BigInt): BigInt =
      if (x == 1) accumulator
      else factorialHelper(x-1, x * accumulator)

    factorialHelper(n, 1)
  }
   // TAIL RECURSIVE

  def concatString(s: String, n: Int): String = {
    def concatHelper(x: Int, accumulator: String): String =
      if (x <1) accumulator
      else concatHelper(x-1, accumulator + s)

    concatHelper(n, "")
  }

  def isPrime(n: Int): Boolean = {
    def primeHelper(x: Int): Boolean =
      if (x == 1) true
      else if ( n % x == 0) false
      else primeHelper(x-1)

    primeHelper(n-1)
  }

  def fibonacci(n: Int): Int = {
    def fibHelper(fib1: Int, fib2: Int, x: Int): Int =
      if (x == 0) fib2
      else fibHelper(fib2, fib1+fib2, x-1)

    fibHelper(1, 1, n)
  }

}
