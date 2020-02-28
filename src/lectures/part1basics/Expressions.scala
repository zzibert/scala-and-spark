package lectures.part1basics

object Expressions extends App{
  val x = 1 + 2
  // Instruction vs Expression
  // if Expression
  val aCondition = true
  val aConditionValue = if(aCondition) 5 else 3
  var aVariable = 0
  val aWeirdValue = (aVariable = 3)


  val aCodeBlock = {
    val y = 2
    val z = y -1
    if (z < y) "hello" else "goodbye"
  }
  println(aCodeBlock)
}
