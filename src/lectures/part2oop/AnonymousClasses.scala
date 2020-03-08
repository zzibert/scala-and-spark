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
    override def sayHi: Unit = println(s"hi my name is $this.name, how can i help?")
  }
  /*
  1. Generic trait MyPredicate[-T] - method if T passed that condition test(T): Boolean
  2. Generic trait MyTransformer[-A, B], method transform(A): B to convert value of type A into type B
  3. MyList function:
    map(transformer) -> MyList
    filter(predicate) -> MyList
    flatMap(transformer from A to MyList[B]) -> MyList[B]

   class EvenPredicate extends MyPredicate[Int]
   class StringToIntTransformer extends MyTransformer[String, Int]

   [1, 2, 3].map(n * 2) = [2, 4, 6]
   [1, 2, 3, 4].filter(n % 2) = [2, 4]
   [1, 2, 3].flatmap(n => [n, n+1]) => [[1, 2], [2, 3], [3, 4]]
   */
}
