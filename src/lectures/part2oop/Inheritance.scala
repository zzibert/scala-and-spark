package lectures.part2oop

object Inheritance extends App{
  // Single Class Inheritance
  sealed class Animal {
    val creatureType = "wild"
    def eat = println("nomnom")
  }

  class Cat extends Animal {
    def crunch = {
      eat
    }
  }

  val cat = new Cat

  cat crunch

  // constructors
  class Person(name: String, age: Int) {
    def this(name: String) = this(name, 0)
  }
  class Adult(name: String, age: Int, idCard: String) extends Person(name)

  // OVERRIDING
  class Dog(override val creatureType: String) extends Animal {
    override def eat = {
      super.eat
      println("pujka pujka")
    }
  }

  val dog = new Dog("domestic")
  dog.eat
  println(dog.creatureType)

  val unknownAnimal: Animal = new Dog("K9")

  unknownAnimal eat

  // OVERRIDING VS. OVERLOADING
  // SUPER

  // PREVENTING OVERRIDES
  // use keyword final on member
  // use keyword final on entire class
  // seal the class = extends classes in this file, prevent extension in other files.

}
