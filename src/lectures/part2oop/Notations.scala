package lectures.part2oop

object Notations extends App {
  class Person(val name: String, favoriteMovie: String, var age: Int = 0) {
    def likes(movie: String): Boolean = movie == favoriteMovie
    def hangOutWith(person: Person): String = s"${this.name} is hanging out with ${person.name}"
    def unary_! : String = s"$name, what the heck?"
    def unary_+ : Person = new Person(name, favoriteMovie, age+1)
    def isAlive: Boolean = true
    def apply(): String = s"hi, my name is $name and I like $favoriteMovie"
    def apply(number: Int): Unit = println(s"$name watcher her favourite movie $favoriteMovie $number times")
    def +(nickname: String): Person = new Person(s"$name $nickname", favoriteMovie)
    def learns(stuff: String): Unit = println(s"$name learns $stuff")
    def learnsScala(): Unit = this learns "Scala"
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

  println((mary + "the rockstar").name)
  println(mary.age)
  println((+(+mary)).age)

  mary learnsScala

  mary(3)
}
