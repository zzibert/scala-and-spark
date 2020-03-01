package lectures.part2oop

object Objects {
  def main(args: Array[String]): Unit = {
    // Scala object = SINGLETON INSTANCE
    val mary = new Person("Mary")
    val John = new Person("John")
    println(mary == John)

    val bobbie = Person(mary, John)
  }
  // SCALA DOES NOT HAVE CLASS LEVEL FUNCTIONALITY ("static")
  object Person {
    // Static level functionality
    val N_EYES = 2
    def canFly: Boolean = false
    // Factory model
    def apply(mother: Person, father: Person) = new Person("Bobbie")
  }
  class Person(val name: String) {
    // Instance level functionality
  }
  // COMPANIONS
  println(Person.N_EYES)
  println(Person.canFly)



  // Scala Application - Scala Object
  // def main(args: Array(String)): Unit

}
