package lectures.FunctionalProgramming

object WhatIsAnFunction extends App {

  // use functions as first class elements
  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }

  println(doubler(2))

  val stringToIntConverter = new Function[String, Int] {
    override def apply(string: String): Int = string.toInt
  }

  println(stringToIntConverter("3") + 4)

  val adder: ((Int, Int) => Int) = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b
  }

  // 1. takes 2 strings and concatenates them

  val concat: (String, String) => String = new Function2[String, String, String] {
    override def apply(a: String, b: String): String = a + " " + b
  }

  println(concat("hello", "world"))
  // 2. transform myPredicate and MyTransformer into function Types
  // 3. Define a function which takes an argument int and retuns another function which takes int and returns int
  // - define the type of the function

  val multiplyNumber: (Int => (Int => Int)) = new Function[Int, Function1[Int, Int]] {
    override def apply(x: Int): Function1[Int, Int] = new Function[Int, Int] {
      override def apply(y: Int): Int = x * y
    }
  }

  val multiplyFive = multiplyNumber(5)

  println(multiplyFive(11))
  println(multiplyNumber(15)(10)) // curried function
}

trait MyFunction[A, B] {
  def apply(element: A): B
}