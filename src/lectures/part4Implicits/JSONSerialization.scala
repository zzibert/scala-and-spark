package lectures.part4Implicits

import java.util.Date

object JSONSerialization extends App {

  /*
  * Users, posts, feeds
  * serialize to JSON
  *
  * */

  case class User(name: String, age: Int, email: String)

  case class Post(content: String, createdAt: Date)

  case class Feed(user: User, posts: List[Post])

  // TYPE CLASS PATTERN
  /*
  * 1. Intermidiate data types - Int, String, List, Date
  * 2. Type classes for conversion to indermediate data types
  * 3. serialize those intermediate data types to JSON
  * */

  sealed trait JSONValue { // intermediate data type
    def stringify: String
  }

  final case class JSONString(value: String) extends JSONValue {
    override def stringify: String = "\"" + value + "\""
  }

  final case class JSONNumber(value: Int) extends JSONValue {
    override def stringify: String = value.toString
  }

  final case class JSONArray(value: List[JSONValue]) extends JSONValue {
    override def stringify: String = value.map(_.stringify).mkString("[", ",", "]")
  }

  final case class JSONObject(values: Map[String, JSONValue]) extends JSONValue {
    override def stringify: String = values.map {
      case (key, value) => "\"" + key + "\"" + s": ${value.stringify}"
    }.mkString("{", ",", "}")

    /*
    * {
    *   name: "John",
    *   friends: [ ... ],
    *   latestPosts: {
    *     content: "Scala rocks"
    *     date: ...
    *   }
    * }
    * */
  }

  val data = Map(
    "user" -> "Daniel",
    "posts" -> List("Scala Rocks", 453)
  )



  // type class

  /*
  * Type class itself
  * Type class instances which are implicits
  * method
  * pimp my library to use type class instances
  *
  * */

  // 2.1
  trait JSONConverter[T] {
    def convert(value: T): JSONValue
  }

//  implicit class JSONConverter[T](value: T) {
//    def convert(implicit converter: JSONConverter[T]): JSONValue = converter.convert(value)
//    def stringify: String = convert(value).stringify
//  }

  // 2.3
  implicit object NumberConverter extends JSONConverter[Int] {
    override def convert(value: Int): JSONValue = JSONNumber(value)
  }

  // 2.2
  implicit object StringConverter extends JSONConverter[String] {
    override def convert(value: String): JSONValue = JSONString(value)
  }

  // custom data types

  implicit object UserConverter extends JSONConverter[User] {
    override def convert(value: User): JSONValue = JSONObject(Map(
      "name" -> JSONString(value.name),
      "age" -> JSONNumber(value.age),
      "email" -> JSONString(value.email)
    ))
  }

  implicit object PostConverter extends JSONConverter[Post] {
    override def convert(value: Post): JSONValue = JSONObject(Map(
      "content" -> JSONString(value.content),
      "createdAt" -> JSONString(value.createdAt.toString)
    ))
  }

  implicit object FeedConverter extends JSONConverter[Feed] {
    override def convert(value: Feed): JSONValue = JSONObject(Map(
      "user" -> value.user.toJSON,
      "posts" -> JSONArray(value.posts.map(_.toJSON))
    ))
  }

  // CONVERSION

  implicit class JSONOps[T](value: T) {
    def toJSON(implicit converter: JSONConverter[T]): JSONValue = converter.convert(value)
  }

  implicit class ArrayConverter[T](values: List[T]) {
    def convert(implicit converter: JSONConverter[T]): JSONArray = JSONArray(values.map(converter.convert(_)))
  }

  implicit class ObjectConverter[T](values: Map[String, T]) {
    def convert(implicit converter: JSONConverter[T]): JSONObject = JSONObject(values.map {
      case (key, value) => key -> converter.convert(value)
    })
  }

  val now = new Date(System.currentTimeMillis())
  val john = User("John", 34, "john@rockthejvm.com")
  val feed = Feed(john, List(Post("Look at this cute puppy", now), Post("hey!", now)))

  println(feed.toJSON.stringify)
//  implicit object ArrayConverter extends JSONConverter[List[JSONValue]] {
//    override def convert(value: List[JSONValue]): JSONValue = JSONArray(value)
//  }
//
//  implicit object ObjectConverter extends JSONConverter[Map[String, JSONValue]] {
//    override def convert(value: Map[String, JSONValue]): JSONValue = JSONObject(value)
//  }

  // call stringify on result
}
