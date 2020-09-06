package lectures.Basics

object Expressions extends App {

  val x = 1 + 2
  println(x)

  println(2 + 3 * 4)

  println(1 == x)

  println(!(1 == x))

  var aVariable = 2

  aVariable += 3 // side effects!

  println(aVariable)

  val aCondition = true
  val aConditionedValue = if(!aCondition) 5 else 3 // IF expression
  println(aConditionedValue)
  println(if(aCondition) 5 else 3)

  // everything in Scala is an expression

  val aWeirdValue = (aVariable = 3)
  println(aWeirdValue)

  // side effects, printing, whiles, reassignment

  // code blocks

  val aCodeBlock = {
    val y = 2
    val z = y + 1

    if (z > 2) "hello" else "goodbye"
  }

  // 1. difference between "hello world" and println("Hello world" -> first is a string the other one is Unit)

  // 2. someValue = true

  // 3. the value is 42
}
