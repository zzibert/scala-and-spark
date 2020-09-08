package lectures.OOP

object AbstractDataTypes extends App {

  // abstract
  // cannot be instantiated
  abstract class Animal {
    val creatureType: String = "wild"

    def eat: Unit
  }

  class Dog extends Animal {
    override val creatureType: String = "Canine"

    def eat: Unit = println("crunch crunch")
  }

  // traits
  trait Carnivore {
    def eat(animal: Animal): Unit
    val preferedMeal: String = "fresh meat"
  }

  trait ColdBlooded

  class Crocodile extends Animal with Carnivore with ColdBlooded {
    override val creatureType: String = "croc"
    def eat: Unit = println("nomnomnom")
    def eat(animal: Animal): Unit = println(s"I am a croc and i eat ${animal.creatureType}")

  }

  val dog = new Dog

  val croc = new Crocodile

  croc eat dog

  // 1 - traits do not have constructor parameters
  // 2 - multiple traits maybe inherited by the same class
  // 3 - traits = behavior, abstract class = "thing"
}
