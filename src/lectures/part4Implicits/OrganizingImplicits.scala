package lectures.part4Implicits

object OrganizingImplicits extends App {

  implicit def reverseOrdering(): Ordering[Int] = Ordering.fromLessThan(_ > _)
//  implicit val normalOrdering: Ordering[Int] = Ordering.fromLessThan(_ < _)

//  println(List(1,4,2,3,6).sorted)

  // scala.Predef -> automatically imported when writing code

  /*
  * Implicit parameters:
  * val/var
  * - objects
  * - accessor methods = defs with no parentheses
  * */


  // Exercise
  case class Person(name: String, age: Int)

  val persons = List(Person("Steve", 30), Person("Amy", 22), Person("John", 66))

//  object Person {
//    implicit val personOrdering: Ordering[Person] = Ordering.fromLessThan(_.name < _.name)
//
//  }

//  implicit val ageOrdering: Ordering[Person] = Ordering.fromLessThan(_.age < _.age)

  import AgeOrdering._

//  println(persons.sorted)

  /*
  * Implicit Scope
  * - normal scope = local scope -> highest scope
  * - imported scope
  * - companion object of all types involved in the method signature
  * - List
  * - Ordering
  * - all the types involved : A or any supertype
  * */

  // def sorted[B >: A](implicit ord : scala.math.Ordering[B]) : List

  /*
  * BEST PRACTICES
  * - if there is a single possible value for it, and you can edit the code, define in the companion object
  * - if more then one value, but a single good one -> the good implicit in the companion , other in local scope
  *
  * */

  object AlphabeticNameOrdering {
    implicit val personOrdering: Ordering[Person] = Ordering.fromLessThan(_.name < _.name)
  }

  object AgeOrdering {
    implicit val personOrdering: Ordering[Person] = Ordering.fromLessThan(_.age < _.age)
  }

  /*
  * Exercise
  * three orderings:
  * - total price = most used ordering (50%)
  * - unit count = some of your team for analytics
  * - unit price = other 25 %
  * - organize them in the proper place
  * */
  case class Purchase(nUnits: Int, unitPrice: Double)

  object Purchase {
    implicit val totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((a, b) => (a.nUnits * a.unitPrice) < (b.nUnits * b.unitPrice))
  }

  object UnitPriceOrdering {
    implicit val unitPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((a, b) => a.unitPrice < b.unitPrice)
  }

  object UnitCountOrdering {
    implicit val unitPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((a, b) => a.nUnits < b.nUnits)
  }

  import UnitPriceOrdering._

  println(List(Purchase(5, 3.75), Purchase(10, 2), Purchase(20, 8), Purchase(1, 15)).sorted)
}
