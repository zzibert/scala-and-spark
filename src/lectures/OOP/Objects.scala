package lectures.OOP

object Objects {

  def main(args: Array[String]): Unit = {
    println(Person.N_EYES)
    println(Person.canFly)

    // Scala object = SINGLETON INSTANCE
    val mary = new Person("mary")
    val john = new Person("john")

    println(mary == john)

    val bobby = Person(mary, john)

    // Scala applications = Scala object with
    // def main(args: Array[String]): Unit
  }

  // SCALA DOES NOT HAVE CLASS-LEVEL FUNCTIONALITY("static")
  object Person { // type + only instance
    // class "static" level functionality
    val N_EYES = 2
    def canFly: Boolean = false

    // factory method
    def apply(mother: Person, father: Person): Person = new Person("Bobbie")
  }

  class Person(val name: String) {
    // instance level functionality
  }

  // COMPANIONS





}
