package lectures.part2oop

object CaseClasses extends App {
  case class Person(name: String, age: Int)

  val jim = new Person("Jim", 34)
  println(jim.name)
  println(jim)
  // 3. equals and hashcode implemented out of the box
  val jim2 = new Person("Jim", 34)

  println(jim == jim2)

  // 4. Case Classes have handy copy methods
  val jim3 = jim.copy(age = 45)

  val thePerson = Person

  case object UnitedKingdom {
    def name: String = "The UK of GB and NI"
  }

  // Expand MyList
}
