package lectures.part2oop

object AbstractDataTypes extends App{
  // abstract
  abstract class Animal {
    val creatureType: String
    def eat: Unit
  }

  class Dog extends Animal {
    override val creatureType: String = "Canine"

    override def eat: Unit = println("pujka pujka")
  }
  // traits
  trait Carnivore {
    def eat(animal: Animal): Unit

  }

  class Crocodile extends Animal with Carnivore {
    override val creatureType: String = "croc"

    override def eat: Unit = "crocks"
    def eat(animal: Animal): Unit = println(s"im a croc and im eating a ${animal.creatureType}")
  }

  val dog = new Dog
  val croc = new Crocodile

  croc eat dog

  // traits vs abstract classes
  // traits do not have constructor parameters
  // multiple traits maybe inherited by the same class
  // traits are behavior, abstract class is a type of thing
}
