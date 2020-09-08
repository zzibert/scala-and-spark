package lectures.OOP

object Notatios extends App{

  class Person(val name: String, favouriteMovie: String) {
    def likes(movie: String): Boolean = movie == favouriteMovie

    def +(person: Person): String = s"${this.name} is hanging out with ${person.name}"

    def unary_! : String = s"${this.name} was here"

    def isAlive : Boolean = true

    def apply(): String = s"Hi, my name is $name and i like $favouriteMovie"
  }

  val mary = new Person("mary", "inception")

  println(mary.likes("inception"))
  println(mary likes "inception") // infix notation - operator notation

  // "operators" in Scala
  val tom = new Person("Tom", "fight club")
  println(mary + tom)

  println(1.+(2)) // all operators are methods

  // prefix notation
  val x = -1 // is same as 1.unary_-
  val y = 1.unary_-
  // unary_prefix only works with a few operators -, +, ~, !

  println(!tom)
  println(tom.unary_!)

  // postfix notation
  println(mary.isAlive)

  println(mary())


}
