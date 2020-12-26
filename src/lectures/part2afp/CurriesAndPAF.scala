package lectures.part2afp

object CurriesAndPAF extends App {

  // curried functions
  val superAdder: Int => Int => Int =
    x => y => x + y

  val add3 = superAdder(3) // Int => Int = y => 3 + y
  println(add3(5))
  println(superAdder(3)(5))

  // METHOD !!
  def curriedAdder(x: Int)(y: Int): Int = x + y // curried method

  val add4: Int => Int = curriedAdder(4)
  // lifting = ETA-EXPANSION

  // functions != methods (JVM limitation)
  def inc(x: Int) = x+ 1
  List(1, 2, 3).map(inc) // ETA-EXPANSION

  // Partial function applications
  val add5 = curriedAdder(5) _ // Int => Int

  // Exercises
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int) = x + y
  def curriedAddMethod(x: Int)(y: Int) = x + y

  // add7: Int => Int = y => 7 + y
  // as many different implementations of add7 using the above

  val add71 = (x: Int) => simpleAddFunction(7, x)
  val add72 = simpleAddFunction(7, _)
  val add73 = (x: Int) => simpleAddMethod(7, x)
  val add74 = simpleAddMethod(7, _)
  val add75 = curriedAddMethod(7) _
  val add76 = (x: Int) => curriedAddMethod(7)(x)
  val add77 = simpleAddFunction.curried(7)

  // underscores are powerful
  def concatenator(a: String, b: String, c: String) = a + b + c
  val insertName = concatenator("Hello, Im ", _: String, ", how are you")

  println(insertName("Daniel"))

  val fillInTheBlanks = concatenator("Hello, ", _: String, _: String) // (x, y) => concatenator("Hello", x, y)

  println(fillInTheBlanks("Daniel ", "Scala is awesome"))

  // EXERCISES
  // 1. Process a list of number and return their string representations with different formats
  // use the %4.2f, %8.6f and %14.12f with curried formatter function.
  
  def curriedFormatter(s: String)(number: Double): String = s.format(number)
  // 2. difference between
  // functions vs methods
  // parameters by-name vs 0-lambda
  def byName(n: => Int) = n + 1
  def byFunction(f: () => Int) = f() + 1

  def method: Int = 42
  def parenMethod(): Int = 42

  println(byName(1))
  println(method)
  println(parenMethod())
  println(byFunction(() => 42))
  /*
  * Calling byName and ByFunction
  *  -int
  * -method
  * -parenMethod
  * -lambda
  * -PAF*/


}
