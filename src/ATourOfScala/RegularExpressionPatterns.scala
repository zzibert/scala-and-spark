package ATourOfScala

import scala.util.matching.Regex

object RegularExpressionPatterns extends App {

  val numberPattern: Regex = "[0-9]+".r

  numberPattern.findAllMatchIn("awesomepassword99f9") match {
    case Seq(number) => println("password OK", number)
    case _ => println("password must contain a number!")
  }
}
