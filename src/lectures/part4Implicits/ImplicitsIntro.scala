package lectures.part4Implicits

object ImplicitsIntro extends App {

  val pair = "Daniel" -> "555"

  val intPair = 1 -> 2

  case class Person(name: String) {
    def greet: String = s"hi my name is $name"
  }

  implicit def fromStringToPerson(str: String): Person = Person(str)

  println("Peter".greet)

  // implicit parameters
  def increment(x: Int)(implicit amount: Int) = x + amount
  implicit val defaultAmount = 100

  println(increment(50))
  // not the same thing as default values, search scope
}
