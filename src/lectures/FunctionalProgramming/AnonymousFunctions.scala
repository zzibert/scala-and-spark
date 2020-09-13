package lectures.FunctionalProgramming

object AnonymousFunctions extends App {

  val doubler = (x: Int) => x * 2 // syntactic sugar - anonymous function - lambda

  // multiple params in a lambda
  val adder: (Int, Int) => Int = (a: Int, b: Int) => a + b

  println(adder(1, 9))

  // no params
  val justDoSomething: () => Int = () => 3

  println(justDoSomething)
  println(justDoSomething()) // you must call lambdas with parenthesises

  // curly braces with lambdas
  val stringToInt: (String => Int) = {
    (str: String) => str.toInt
  }

  val niceIncrementer: Int => Int = _ + 1

  val niceAdder: (Int, Int) => Int = _ + _ // equivalent to (a, b) = > a + b

  // 1. Mylist replace


}
