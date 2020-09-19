package ATourOfScala

object ForComprehensions extends App {

  case class User(name: String, age: Int)

  val userBase = List(
    User("Travis", 28),
    User("Kelly", 33),
    User("Jennifer", 44),
    User("Dennis", 23))

  val twentySomethings =
    for (user <- userBase if user.age >= 20 && user.age < 30)
      yield user.name

  twentySomethings.foreach(name => println(name))

  def foo(n: Int, v: Int) =
    for (i <- 0 until n;
         j <- 0 until n if i + j == v)
      println((i, j))

  foo(10, 10)
}
