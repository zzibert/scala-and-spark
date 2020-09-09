package lectures.OOP

object AnonymousClasses extends App {

  abstract class Animal {
    def eat: Unit
  }

  // Anonymous class
  val funnyAnimal: Animal = new Animal {
    override def eat: Unit = println("HAHAHAHAH")
  }

  println(funnyAnimal.getClass)

  class Person(name: String) {
    def sayHi: Unit = println(s"Hi my name is $name, how can i help")
  }

  val jim = new Person("Jim") {
    override def sayHi: Unit = println(s"hi my name is jim, how can i help")
  }

  // 1. generic trait MyPredicate[T] test[T]() => boolean
  // 2. Generic trait MyTransformer[A, B]
  // 3. Mylist map(MyTransformer) => myList, filter(predicate) => mylist, flatmap(transformer A =)
}
