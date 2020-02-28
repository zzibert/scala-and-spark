package lectures.part1basics

object Functions extends App{
  def aFunction(a: String, b: Int): String =
    a + ' ' + b

  def aParameterless(): Int = 42

  def aRepeatedFunction(aString: String, n: Int): String =
    if(n == 1) aString
    else aString + aRepeatedFunction(aString, n-1)

  // When you need a loop, use recursion
  println(aRepeatedFunction("hello", 3))

  def greeting(name: String, age: Int): String =
    s"Hi, my name is $name and i am $age years old!"

  def factorial(n: Int): Int =
    if (n == 1) 1
    else n * factorial(n-1)

  def fibonnaci(n: Int): Int =
    if (n == 1) 1
    else if (n == 2) 1
    else fibonnaci(n - 1) + fibonnaci(n - 2)

  def isPrime(prime: Int, n: Int): Boolean =
    if (n == 1) true
    else if (prime % n == 0) false
    else isPrime(prime, n-1)

}
