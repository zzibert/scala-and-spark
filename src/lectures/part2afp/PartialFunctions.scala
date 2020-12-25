package lectures.part2afp

import scala.util.Try

object PartialFunctions extends App {

  val aFunction = (x: Int) => x + 1 // Function[Int, Int] === Int => Int

  val aFussyFunction = (x: Int) =>
    if (x == 1) 42
    else if (x == 2) 56
    else if (x == 5) 999
    else throw new FunctionNotApplicableException

  class FunctionNotApplicableException extends RuntimeException

  val aNicerFussyFunction = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
    case 3 => 999
  }

  // {1, 2, 5} => Int

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 3 => 999
  } // partialFunction value

  println(aPartialFunction(2))
//  println(aPartialFunction(345))


  // PF utilities
  println(aPartialFunction.isDefinedAt(675))

  // lift
  val lifted = aPartialFunction.lift // Int => Option[Int]
  println(lifted(2))
  println(lifted(234234234))

  val pfChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 67
  }

  println(pfChain(2))
  println(pfChain(45))

  // PF extend normal functions
  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

  // HOFs accept partial functions as well
  val aMappedList = List(1, 2, 3).map {
    case 1 => 42
    case 2 => 78
    case 3 => 1000
  }

  println(aMappedList)

  /*
  * PF can only have ONE parameter type
  *  1 - construct pf instance yourself (anonymous class)
  *  2 - implement chatbot as a partial function
  *
  */

  class CustomPF[A, B](v1: Function[A, B]) extends PartialFunction[A, B] {
    def isDefinedAt(x: A): Boolean = {
      val isDefined = Try {
        v1(x)
      }
      isDefined.isSuccess
    }
    def apply(x: A): B = v1(x)
  }

  class ChatBot(pf: PartialFunction[String, String]) {
    def apply(input: String): String = pf(input)
  }

  val chatBot: PartialFunction[String, String] = {
    case "Hello" => "fuck off"
    case "sam hyde" => "yeeeees"
    case "disney bullshit" => "blows"
  }

  scala.io.Source.stdin.getLines().map(chatBot).foreach(println)




}
