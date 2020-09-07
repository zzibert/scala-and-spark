package lectures.OOP

object OOBasics extends App {

  val author = new Writer("Charles", "Dickens", 1812)

  val imposter = new Writer("Charles", "Dickens", 1812)


  val novel = new Novel("Great Expectations", 1861, author)

  var counter = new Counter

  counter = counter.increment(5)

  println(counter.currentCount)

  counter = new Counter

  counter = counter.decrement(9)

  println(counter.currentCount())

  counter = counter.increment.increment

  println(counter.currentCount())
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

// Novel and a Writer class
// writer, first name, surname, year
// - method fullname

class Writer(name: String, surname: String, val year: Int) {

  def fullname(): String = s"$name $surname"
}

class Novel(name: String, var year: Int, author: Writer) {

  def authorAge(): Int = year - author.year

  def isWrittenBy(author: Writer): Boolean = author == this.author

  def copy(newYear: Int): Novel = new Novel(name, newYear, author)
}

class Counter(x: Int = 0) {

  def currentCount(): Int = x

  def increment(): Counter = {
    println("incrementing")
    new Counter(x+1)
  } // immutability

  def increment(y: Int): Counter = {
    if (y <= 0) this
    else increment.increment(y-1)
  }

  def decrement(): Counter = {
    println("decrementing")
    new Counter(x-1)
  } // immutability

  def decrement(y: Int): Counter = {
    if (y <= 0) this
    else decrement.decrement(y-1)
  }
}
