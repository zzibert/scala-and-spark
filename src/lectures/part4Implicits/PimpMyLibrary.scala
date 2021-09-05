package lectures.part4Implicits

object PimpMyLibrary extends App {

  // 2. isPrime

  implicit class RichInt(val value: Int) extends AnyVal {
    def isEven: Boolean = value % 2 == 0

    def sqrt: Double = Math.sqrt(value)

    def times(function: () => Unit): Unit = {
      for (i <- 1 to value) {
        function()
      }
    }

    def *[T](list: List[T]): List[T] = {
      def multiplyAux[T](times: Int, list: List[T]): List[T] = {
        if (times < 1) {
          List.empty[T]
        } else {
          list ::: multiplyAux(times-1, list)
        }
      }
      multiplyAux(value, list)
    }
  }

  implicit class RicherInt(richerInt: RichInt) {
    def isOdd = richerInt.isEven
  }

  new RichInt(42).sqrt

  println(42.isEven) // TYPE enrichement -> pimping // new RichInt(42).isEven

  import scala.concurrent.duration._

  3 seconds

  // compiler doesnt do multiple implicit searches

//  42.isOdd

  // Enrich the String class
  // asInt
  // encrypt  "john" -> "lnjp"

  // keep enriching the int class
  // times (function) 3. times do something
  // multiply(list) = 3 * List(1, 2) -> List(1, 2, 1, 2, 1, 2)

  implicit class RichString(value: String) {
    def encrypt(delay: Int): String = value.map(c => (c.toInt + delay).toChar)
  }

  println("John".encrypt(2))

  3.times(() => println("scala rocks"))


  println(4 * List('a', 'b', 'c'))

  // "3" / 4

  implicit def stringToInt(string: String): Int = {
    Integer.valueOf(string)
  }

  println("6" / 2)


  class RichAlternativeInt(value: Int)
  implicit def enrich(value: Int): RichAlternativeInt = new RichAlternativeInt(value)

  // danger zone
  implicit def intToBoolena(i: Int): Boolean = i % 2 == 0

  println(if (43) "even" else "odd")

  val aCondition = if (4) "OK" else "Something wrong"
  println(aCondition)

  // AVOID IMPLICIT DEFS , USE TYPE CLASSES
}
