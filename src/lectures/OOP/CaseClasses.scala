package lectures.OOP

object CaseClasses extends App {
  case class Person(name: String, age: Int)

  // 12. class parameters are promoted to fields
  val jim = new Person("Jim", 34)
  println(jim.name)

  // 2. sensible toString
  println(jim)

  // 3. equals and hashcode implemented out of the box
  val jim2 = new Person("Jim",34)

  println(jim == jim2)

  // case classes have handy copy methods
  val jim3 = jim.copy(age = 45)
  println(jim3)

  // case classes have companion objects
  val thePerson = Person
  val mary = Person("mary", 23)

  // 6. Case classes are serializable
  // akka framework

  // 7. Case classes have extractor patterns == CCs can be used in pattern matching

  case object UnitedKingdom {
    def name: String = "The UK and northern ireland"
  }

  // Expand Mylist - use case classes and case objects.
}
