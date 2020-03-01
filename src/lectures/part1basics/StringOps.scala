package lectures.part1basics

object StringOps extends App{
  val str: String = "Hello, im learning Scala"

  println(str.charAt(2))
  println(str.substring(7, 11))
  println(str.split(" ").toList)
  println(str.startsWith("Hello"))
  println(str.toLowerCase())
  println(str.length)

  val aNumberString = "2"
  val aNumber = aNumberString.toInt
  println('a' +: aNumberString :+ 'z')
  println(str.reverse)
  println(str.take(2))

  // Scala specific
  val name = "David"
  val age = 12
  val greeting = s"Hello, my name is $name and i am ${age + age} old"
  println(greeting)

  // F-interpolators
  val speed = 1.20f
  val myth = f"$name can eat $speed%2.3f burgers per minute"
  println(myth)

  // raw-interpolator
  println(raw"this is a \n newline")
  val escaped = "This is a \n newline"
  println(raw"$escaped")
}
