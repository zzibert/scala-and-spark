package lectures.FunctionalProgramming

object MapFlatMapFilterFor extends App {

  val list = List(1, 2, 3)

  println(list)

  // flatMap
  val toPair = (x: Int) => List(x, x+1)

  println(list.flatMap(toPair))

  // print all combinations between two lists
  val numbers = List(1, 2, 3, 4)
  val characters = List('a', 'b', 'c', 'd')

  val combinations: List[String] = for {
    char <- characters
    number <- numbers if number % 2 == 0
  } yield char.toString + number

  println(combinations)

  val combinations2 = numbers.flatMap(n => characters.map(c => "" + c + n))

  println(combinations2)

  // syntax overload
  list.map { x =>
    x * 2
  }

  /*
    1. Mylist supports for comprehensions YES
    2. A small collection of at most one element - Maybe[+T]
    3. map, flatmap, filter
   */

  val collection = for (x <- 1 to 20) yield Some(x)

  println(collection)
}

