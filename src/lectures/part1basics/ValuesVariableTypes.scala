package lectures.part1basics

object ValuesVariableTypes extends App {
  // Vals are immutable

  // compiler infers types
  val x = 42
  println(x)

  val aString: String = "Hello"

  val aBoolean: Boolean = false

  val aChar: Char =  'a'

  val aInt: Int = x

  val aShort: Short = 46

  val aLong: Long = 44444444444444L

  val aFloat: Float = 434.0

  // variable

  var variable: Int = 4
  variable = 5 // side effects


}
