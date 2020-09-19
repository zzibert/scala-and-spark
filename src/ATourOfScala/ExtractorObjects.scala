package ATourOfScala

import scala.util.Random

object ExtractorObjects extends App {
  object CustomerID {
    def apply(name: String) = s"$name--${Random.nextLong}"

    def unapply(customerID: String): Option[String] = {
      val stringArray: Array[String] = customerID.split("--")
      if (stringArray.tail.nonEmpty) Some(stringArray.head) else None
    }
  }

  val customer1ID = CustomerID("--asdfasdfasdf")
  customer1ID match {
    case CustomerID(name) => println(name)
    case "" => println("Could not extract a CustomerID")
  }

  val customer2ID = CustomerID("Nico")
  val CustomerID(name) = customer2ID
  println(name)  // prints Nico
}
