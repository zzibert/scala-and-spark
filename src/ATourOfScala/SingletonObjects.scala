package ATourOfScala

import scala.math.{Pi, pow}

object SingletonObjects extends App {

  object Box

  object Logger {
    def info(message: String): Unit = println("sINFO: $message")
  }

  class Project(name: String, daysToComplete: Int)

  class Test {
    val project1 = new Project("TPS Reports", 1)
    val project2 = new Project("Website Redesign", 5)
    Logger.info("Created projects")
  }

  // COMPANION OBJECTS

  case class Circle(radius: Double) {
    import Circle._
    def area: Double = calculateArea(radius)
  }

  object Circle {
    private def calculateArea(radius: Double): Double = Pi * pow(radius, 2.0)
  }

  val circle1 = Circle(5.0)

  println(circle1.area)

  class Email(val username: String, val domainName: String)

  object Email {
    def fromString(emailString: String): Option[Email] = {
      emailString.split('@') match {
        case Array(a, b) => Some(new Email(a, b))
        case _ => None
      }
    }
  }

  val scalaCenterEmail = Email.fromString("scala.center@epfl.ch")
  scalaCenterEmail match {
    case Some(email) => println(
      s"""Registered an email
         |Username: ${email.username}
         |Domain name: ${email.domainName}
     """.stripMargin)
    case None => println("Error: could not parse email")
  }


}
