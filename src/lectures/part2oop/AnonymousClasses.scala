package lectures.part2oop

object AnonymousClasses extends App {
  abstract class Animal {
    def eat: Unit
  }

  // Anonymous Class
  val funnyAnimal: Animal = new Animal {
    override def eat: Unit = println("hahahahahahaha")
  }

  println(funnyAnimal.getClass())

  class Person(name: String) {
    def sayHi: Unit = println(s"hi my name is $name, how can i help?")
  }
  val jim = new Person("Jim") {
    override def sayHi: Unit = println(s"hi my name is $name, how can i help?")
  }
}
