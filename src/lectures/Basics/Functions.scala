package lectures.Basics

object Functions extends App {

  def aFunction(a: String, b: Int): String = {
    a + " " + b
  }

  println(aFunction("hello", 3))

  def aParameterlessFunc(): Int = 42

  aParameterlessFunc()
  println(aParameterlessFunc)

  // when you need loops, use recursions
  def aRepeatedFunction(aString: String, n: Int): String = {
    if (n == 1) aString
    else aString + " " + aRepeatedFunction(aString, n-1)
  }

  println(aRepeatedFunction("hello", 3))

  def aFuncWithSideEffects(aString: String): Unit  = println(aString)

  def aBigFunc(n: Int): Int = {
    def aSmallerFunc(a: Int, b: Int): Int = a + b

    aSmallerFunc(n, n-1)
  }

  // 1. a greeting func (name, age) = > "hi my name is age years old"
  def greeting(name: String, age: Int): String = s"Hi. my name is $name and I am $age years old."

  // 2. factorial func
  def factorial(n: Int): Int = {
    if (n <= 0) 1
    else n * factorial(n-1)
  }


  // 3. Fibonnaci
  def fibonnaci(n: Int): Int = {
    if (n < 3) 1
    else fibonnaci(n-1) + fibonnaci(n-2)
  }


  //4. Tests if a number is prime
  def isPrime(n: Int): Boolean = {
    def isPrimeHelper(number: Int): Boolean = {
      if (number <= 1) true
      else n % number != 0 && isPrimeHelper(number-1)
    }

    isPrimeHelper(n / 2)
  }

  println("37" + " " + isPrime(37))
  println("10" + " " + isPrime(10))
  println("9" + " " + isPrime(9))
  println("8" + " " + isPrime(8))
  println("7" + " " + isPrime(7))
  println("6" + " " + isPrime(6))
  println("5" + " " + isPrime(5))
  println("4" + " " + isPrime(4))
  println("3" + " " + isPrime(3))
  println("2" + " " + isPrime(2))
  println("1" + " " + isPrime(1))



}
