package lectures.Basics

object StringOps extends App {
  val str: String = "hello, I am learning Scala"

  println(str.charAt(2))

  println(str.substring(7, 11))

  println(str.split(" ").toList)

  println(str.startsWith("Hello"))

  println(str.replace(" ", "-"))

  println(str.length)

  val aNumberString = "45"
  val aNumber = aNumberString.toInt

  println('a' +: aNumberString :+ 'b')

  println(str.reverse)

  // Scala specific interpolators

  val name = "David"
  val age = 12
  val greeting = s"hello, my name $name and i am $age years old"

  // F interpolators
  val speed = 1.2f
  val myth = f"$name can eat $speed%2.2f burgers per minute"

  println(myth)

  // raw-interpolator
  println(raw"this is a \n newline")
}
