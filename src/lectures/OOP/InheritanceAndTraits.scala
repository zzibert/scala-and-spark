package lectures.OOP

object InheritanceAndTraits extends App {

  // single class inheritance
 sealed class Animal {
    val creatureType = "wild"
    def eat = println("nom nom nom")
  }

  class Cat extends Animal {
    def crunch = {
      eat
      println("crunch crunch")
    }
  }

  val cat = new Cat
  cat.crunch

  // constructors
  class Person(name: String, age: Int) {
    def this(name: String) = this(name, 0)
  }

  class Adult(name: String, age: Int, idCard: String) extends Person(name) {

  }

  // overriding
  class Dog(override val creatureType: String) extends Animal {
    override def eat = {
      super.eat
      println("crunch crunch")
    }
  }

  val dog = new Dog("K9")
  dog.eat
  println(dog.creatureType)

  // type substitution - broad sense - polymorphism
  val unknownAnimal: Animal = new Dog("K9")

  unknownAnimal.eat

  // overriding vs overloading

  // super

  // preventing overrides
  // 1 - use final on member
  // 2 - use final on the entire class
  // 3 - seal the class  = extend classes in this file only, prevents extension in other files

}
