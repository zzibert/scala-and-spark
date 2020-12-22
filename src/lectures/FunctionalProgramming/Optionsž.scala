package lectures.FunctionalProgramming

import scala.util.Random

object Options extends App {

  val myFirstOption: Option[Int] = Some(4)

  val noOption: Option[Int] = None

  // unsafe APIs
  def unsafeMethod(): String = null

//  val result = Some(unsafeMethod()) // WRONG

  val result = Option(unsafeMethod()) // Some or None

  println(result)

  // chained methods
  def backupMethod(): String = "A valid result"

  val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))

  println(backupMethod())

  // DESIGN unsafe APIs
  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("A valid result")

  val betterChainedResult = betterUnsafeMethod() orElse betterBackupMethod()

  // functions on Options
  println(myFirstOption.isEmpty)
  println(myFirstOption.get) // UNSAFE - DO NOT USE THIS

  // map, flatmap, filter
  println(myFirstOption.map(_ * 2))
  println(myFirstOption.filter(_ > 10))
  println(myFirstOption.flatMap(x => Option(x * 10)))

  val config: Map[String, String] = Map(
    // fetched from elsewhere
    "host" -> "176.45.36.1",
    "port" -> "80"
  )

  class Connection {
    def connect = "Connected" // connect to the same server
  }

  object Connection {
    val random = new Random(System.nanoTime())
    def apply(host: String, port: String): Option[Connection] = {
      if (random.nextBoolean()) Some(new Connection)
      else None
    }
  }

  // try to establish a connection, if so print the connect method
  def connectionAux(host: String, port: String): Option[Connection] = {
    val connection = Connection(host, port)
    if (!connection.isEmpty) {
      connection
    } else {
      Connection(host, port)
    }
  }
  // for - comprehensions
  val connectionStatus = for {
    h <- config.get("host")
    p <- config.get("port")
    connection <- Connection(h, p)
  } yield connection.connect

  connectionStatus foreach(println)



}
