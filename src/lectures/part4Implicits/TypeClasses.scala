package lectures.part4Implicits

object TypeClasses extends App {

  trait HTMLWritable {
    def toHtml: String
  }

  case class User(name: String, age: Int, email: String) extends HTMLWritable {
    override def toHtml: String = s"<div>$name ($age yo) <a href=$email/> </div>"
  }

  /*
  * 1 - only works for types that we write
  * 2 - ONE implementation out of quite a number
  * */

  // option 2 - use pattern matching

  object HTMLSerializerPM {
    def serializeToHTML(value: Any) = value match {
      case User(n, a, e) =>
      case _ =>
    }
  }

  /*
  * 1 - lost the type safety
  * 2 - need to modify code every time
  * 3 - one implementation out of many
  * */

  trait HTMLSerializer[T] {
    def serialize(value: T): String
  }

  object UserSerializer extends HTMLSerializer[User] {
    override def serialize(value: User): String = s"<div>${value.name} (${value.age} yo) <a href=${value.email}/> </div>"
  }

  val john = User("John", 32, "john@rockthejvm.com")

  println(UserSerializer.serialize(john))
  // 1 - we can define serializers for other types

  import java.util.Date

  object DateSerializer extends HTMLSerializer[Date] {
    override def serialize(value: Date): String = s"<div>${value.toString}</div>"
  }

  // 2 - multiple serializers for a certain type
  object PartialUserSerializer extends HTMLSerializer[User] {
    override def serialize(value: User): String = s"<div>${value.name}</div>"
  }

  // TYPE CLASS -> TYPE CLASS INSTANCES
  trait MyTypeClassTemplate[T] {
    def action(value: T): String

  }

  /*
  * Equality type class
  * */
  trait Equal[T]  {
    def equal(a: T, b: T): Boolean
  }

  object CompareUsersByName extends Equal[User] {
    override def equal(a: User, b: User): Boolean = a.name == b.name
  }

  object CompareUsersByNameAndEmail extends Equal[User] {
    override def equal(a: User, b: User): Boolean = (a.name == b.name) && (a.email == b.email)
  }
}
