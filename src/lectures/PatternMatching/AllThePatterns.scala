package lectures.PatternMatching

import exercises.{Cons, MyList, Empty}

object AllThePatterns extends App {

  // constants
  val x: Any = "Scala"
  val constants = x match {
    case 1 => "a number"
    case "Scala" => "The Scala"
    case true => "The truth"
    case AllThePatterns => "A sigleton object"
  }

  // 2 - match anything
  // 2. wildcard
  val matchAnything = x match {
    case _ =>
  }

  // 2. 2. variable
  val matchAVariable = x match {
    case something => s"i ve found $something"
  }

  // 3. tuples
  val aTuple = (1, 2)
  val matcATuple = aTuple match {
    case (1, 1) =>
    case (something, 2) =>
  }

  val nestedTuple = (1, (2, 3))

  val matchAnestedTuple = nestedTuple match {
    case (_, (2, v)) =>
  }

  // 4. case classes - constructor patterns
  val aList: MyList[Int] = Cons(1, Cons(2, Empty))

  val matchAList = aList match {
    case Empty =>
    case Cons(head, Cons(subhead, subtail)) =>
  }

  // 5. list patterns
  val aStandardList = List(1, 2, 3, 42)
  val standardListNatching = aStandardList match {
    case List(1, _, _, _) => // extractor
    case List(1, _*) => // list of arbitrary length
    case 1 :: List(_) => // infix pattern
    case List(1, 2, 3) += 42 => // infix pattern
  }

  // 6 - type specifiers
  val unknown: Any = 2
  val unknownMatch = unknown match {
    case list: List[Int] => // explicit type specifier
    case _:
  }

  // 7 - name binding
  val nameBindingMatch = aList match {
    case notEmptyList @ Cons(_, _) => // name binding => use the name later
    case Cons(1, rest @ Cons(2, _)) => // name binding inside nested patterns
  }

  // 8 - multi-patterns
  val multipattern = aList match {
    case Empty | Cons(0, _) => // compound pattern
  }

  // 9 - if guards
  val secondElementSpecial = aList match {
    case Cons(_, Cons(specialElement, _)) if specialElement % 2 == 0 => 
  }
}
