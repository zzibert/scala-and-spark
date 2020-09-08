package lectures.OOP

object Notatios extends App{

  class Person(val name: String, favouriteMovie: String, val age: Int = 0) {
    def likes(movie: String): Boolean = movie == favouriteMovie

    def +(title: String): Person = new Person(name + " the " + title, favouriteMovie, age)

    def unary_+ : Person = new Person(name, favouriteMovie, age+1)

    def isAlive : Boolean = true

    def apply(times: Int): String = s"${this.name} watched ${this.favouriteMovie} $times times"

    def learns(subject: String) = s"${this.name} learns $subject"

    def learnsScala : String = this learns "Scala"
  }

  var mary = new Person("mary", "inception")

  // "operators" in Scala
  val tom = new Person("Tom", "fight club")


  // prefix notation
  val x = -1 // is same as 1.unary_-
  val y = 1.unary_-
  // unary_prefix only works with a few operators -, +, ~, !


  // overload the + operator
  println((mary + "rockstar")(3))

  // 2. add an age to the person class
  // add a unary + operator => new person with the age + 1
  mary = +mary
  println(mary.age)

  // 3. add a learns method => mary learns
  // add a learnScala method , calls the learns method with "Scaka"
  // use it in postfix notation
  println(mary.learnsScala)

  // overload apply method  mary.apply 2 => "mary watched inception 2 times"
  println(mary(10))

}
