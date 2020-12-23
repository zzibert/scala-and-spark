package lectures.PatternMatching

import scala.util.Random

object PatternMatching extends App {

  val random = new Random
  val x = random.nextInt(10)

  val description = x match {
    case 1 => "the one"
    case 2 => "double or nothing"
    case _ => "trololo"
  }

  println(description , x)

  // decompose values
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)

  val greeting = bob match {
    case Person(name, age) if age < 21 => s"Hi my name is $name and i cant drink in the us"
    case Person(name, age) => s"Hi my name is $name and i am $age years old"
    case _ => 42
  }

  println(greeting)

  // 1. cases are matched in order
  // 2. if no cases match -> Match error
  // 3. type of the PM expression -> unified type of all the types in all the cases
  // 4. PM works really well with case classes*

  // PM on sealed hierarchies
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(greet: String) extends Animal

  val animal: Animal = Dog("Terra Nova")

  animal match {
    case Dog(someBreed) => println(s"Matched a dog of the breed $someBreed")
  }

  // Exercises
  // simple function uses pm takes and expression as param => human readable form
  // sum(2, 3) => 2 + 3
  // Sum(2, 3, 4) => 2 + 3 + 4
  // Prod(Sum(Number(2), Number(1)), Number(3)) => (2 + 1) * 3
  // Sum(Prod())

  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Product(e1: Expr, e2: Expr) extends Expr

  def show(expr: Expr): String = {
    expr match {
      case Number(n: Int) => n.toString
      case Sum(e1: Expr, e2: Expr) => show(e1) + " + " + show(e2)
      case Product(e1: Sum, e2: Sum) => "(" + show(e1) + ") * (" + show(e2) + ")"
      case Product(e1, e2) => {
        def maybeShowParentheses(exp: Expr) = exp match {
          case Product(_, _) => show(exp)
          case Number(_) => show(exp)
          case _ => "(" + show(exp) + ")"
        }

        maybeShowParentheses(e1) + " * " + maybeShowParentheses(e2)
      }
    }
  }

  println(show(Sum(Number(2), Number(3))))
  println(show(Sum(Sum(Number(2), Number(3)), Number(4))))
  println(show(Product(Sum(Number(2), Number(1)), Number(3))))
  println(show(Sum(Product(Number(2), Number(1)), Number(3))))


}
