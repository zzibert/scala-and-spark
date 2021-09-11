package exercises

import lectures.part4Implicits.TypeClasses.{HTMLSerializer, User}

object EqualityPlayground extends App {

  trait Equal[T]  {
    def apply(a: T, b: T): Boolean = equal(a, b)
    def equal(a: T, b: T): Boolean
  }

  implicit object CompareUsersByName extends Equal[User] {
    override def equal(a: User, b: User): Boolean = a.name == b.name
  }

  object CompareUsersByNameAndEmail extends Equal[User] {
    override def equal(a: User, b: User): Boolean = (a.name == b.name) && (a.email == b.email)
  }

  object Equal {
    def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean = equalizer.equal(a, b)
  }

  val john = User("John", 60, "john@john.com")

  val anotherJohn = User("John", 45, "john2@john.com")

  println(Equal(john, anotherJohn))

  // AD-HOC POLYMORPHISM

  /*
  * Add Implicit conversion class
  * same thing as htmlSerializer enrichement thing
  * Improve the Equal TC with an implicit conversion class
  * === (anothervalue: T)
  * != (anothervalue: T)
  * */

  implicit class EqualEnrichment[T](value: T) {
    def ===(anotherValue: T)(implicit equalizer: Equal[T]): Boolean = equalizer(value, anotherValue)

    def !=(anotherValue: T)(implicit equalizer: Equal[T]): Boolean = !equalizer(value, anotherValue)
  }

  println(john === anotherJohn)

  // TYPE SAFE
//  println(john === 43)  compiler prevents from compiling this code

  // context bounds
//  def htmlBoilerPlate[T](content: T)(implicit serializer: HTMLSerializer[T]): String =
//    s"<html><body> ${content.toHTML(serializer)}</body></html>"
//
//  def htmlSugar[T: HTMLSerializer](content: T): String =
//    s"<html><body> ${content.toHTML}</body></html>"

  // implicitly
  case class Permissions(mask: String)
  implicit val defaultPermissions: Permissions = Permissions("0744")

  // in some other part of the code
  val standardPerms = implicitly[Permissions]

  def findImplicit[T](implicit impl: T) = impl

  println(standardPerms)

  println(findImplicit[Permissions])



}
