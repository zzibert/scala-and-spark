package lectures.FunctionalProgramming

object AnonymousFunctions extends App {

  // anonymous function - lambda
  val doubler: Int => Int = x => x * 2

  // multiple parameters
  val adder: (Int, Int) => Int = (a: Int, b: Int) => a + b

  // no params
  val justDoSomething: () => Int = () => 3

  println(justDoSomething)
  println(justDoSomething())

  // curly braces
  val stringToInt = { (str: String) =>
    str.toInt
  }

  // more syntactic sugar
  val niceIncrementer: Int => Int = _ + 1

  val niceAdder: (Int, Int) => Int = _ + _ // === (a, b) => a + b

  // 1. myList: replace all functionX calls with lambdas
  // 2. Rewrite special Adder as a anonymous function
}
