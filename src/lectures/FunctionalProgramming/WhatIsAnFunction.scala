package lectures.FunctionalProgramming

object WhatIsAnFunction extends App {

  // use finctions as first class elements
  // problem: oop

  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }

  println(doubler(2))

  // function types = Function1[A, B]
  val stringToIntCoverter = new Function1[String, Int] {
    override def apply(string: String): Int = string.toInt
  }

  println(stringToIntCoverter("9") + 1)

  val adder: ((Int, Int) => Int) = new Function2[Int, Int, Int] {
    override def apply(v1: Int, v2: Int): Int = v1 + v2
  }

  // Function types Function2[A, B, R] === (A, B) => R
  // ALL SCALA FUNCTIONS ARE OBJECTS OR INSTANCES OF CLASSES

  // 1. a function which takes 2 strings and concatenates them
  val concatenate: ((String, String) => String) = new Function2[String, String, String] {
    override def apply(v1: String, v2: String): String = v1 + v2
  }

  println(concatenate("Hello", "Scala"))

  // 2. Transform myPredicate and MyTransformer into function types.

  // 3. define a function which takes an int and returns another function which takes an int and returns an int
  // - whats the type of this function
  // - how to do it

  val Hof: (Int => (Int => Int)) = new (Int => (Int => Int)) {
    override def apply(v1: Int): (Int => Int) = new (Int => Int) {
      override def apply(v2: Int): Int = v1 + v2
    }
  }

  val multByFive = Hof(3)
  val multByFour = Hof(4)
  val multByTen = Hof(10)

  println(multByFive(4))
  println(multByFour(10))
  println(multByTen(10))
  println(Hof(10)(10)) // currying

}

trait MyFunction[A, B] {
  def apply(element: A): B
}

