package lectures.part2oop

object OOBasics extends App{
  val person = new Person("John", 26)
  println(person.x)
  person.greet("Yolo")
}

// constructor
class Person(name: String, val age: Int) {
  // body
  val x = 2

  println(1 + 3)

  // Method
  def greet(name: String): Unit = println(s"${this.name} says hi, $name")

  def this(name: String) = this(name, 0)
  def this() = this{"John Doe"}
}

// Class parameters are not class fields!