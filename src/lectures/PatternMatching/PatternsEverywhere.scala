package lectures.PatternMatching

object PatternsEverywhere extends App {

  // partial Function
  val list = List(1, 2, 3, 4, 5, 6)

  // partial functions based on pattern matching
  val mappedList = list.map {
    case v if v % 2 == 0 => v + " is even"
    case 1 => "the one"
    case _ => "something else"
  }

  println(mappedList)

  // equivelant to
  val mappedList2 = list.map { x => x match {
      case v if v % 2 == 0 => v + " is even"
      case 1 => "the one"
      case _ => "something else"
    }
  }
}
