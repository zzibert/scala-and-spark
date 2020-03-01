package lectures.part2oop

object OOBasics extends App{
  var counter = new Counter(5)
  counter = counter.increment(5)
  counter = counter.decrement(10)
  println(counter.currentCount())
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

class Writer(firstName: String, surname: String, val year: Int) {
  def fullname() = println(s"$firstName $surname")
}

class Novel(name: String, yearOfRelease: Int, author: Writer) {
  def authorAge() = (yearOfRelease - author.year)
  def isWrittenBy(): Unit = author.fullname()
  def copy(newYearOfRelease: Int): Novel = {
    new Novel(name, newYearOfRelease, author)
  }
}

class Counter(val x: Int) {
  def currentCount(): Int = x
  def increment(): Counter = {
    println("incrementing")
    new Counter(x+1) // immutability
  }
  def decrement(): Counter = {
    println("decrementing")
    new Counter(x-1)
  }

  def increment(amount: Int): Counter = {
    if (amount == 0) this
    else increment.increment(amount-1)
  }
  def decrement(amount: Int): Counter = {
    if (amount == 0) this
    else decrement.decrement(amount - 1)
  }
}