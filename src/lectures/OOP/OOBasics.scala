package lectures.OOP

object OOBasics extends App {

  val person = new Person("John", 26)

  println(person.x)

  person.greet("Tommy")

  person.greet
}

class Person(name: String, val age: Int) {
  val x = 2

  println(1 + 3)

  def greet(name: String): Unit = println(s"${this.name} says hi $name")

  // overloading
  def greet(): Unit = println(s"hi, I am $name")

  // multiple constructors
  def this(name: String) = this(name, 0)

  def this() = this("john Doe")
}

// class parameters are not fields
