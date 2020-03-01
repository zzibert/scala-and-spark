package lectures.part2oop

object Notations extends App {
  class Person(val name: String, favoriteMovie: String) {
    def likes(movie: String): Boolean = movie == favoriteMovie
    def hangOutWith(person: Person): String = s"${this.name} is hanging out with ${person.name}"
    def unary_! : String = s"$name, what the heck?"
    def isAlive: Boolean = true
    def apply(): String = s"hi, my name is $name and I like $favoriteMovie"
  }

  val mary = new Person("Mary", "Inception")
  println(mary likes "Inception") // Infix notation

  // Operators in Scala
  val tom = new Person("Tom", "Fight Club")
  println(mary hangOutWith tom)

  // Prefix notation
  val x = -1
  println(!mary)

  // Postfix notation
  println(mary.isAlive)
  println(mary isAlive)

  println(mary.apply())
  println(mary()) // Equivalent
}
