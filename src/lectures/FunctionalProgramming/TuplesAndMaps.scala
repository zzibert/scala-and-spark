package lectures.FunctionalProgramming

object TuplesAndMaps extends App {

  // tuples = finite ordered lists
  val aTuple = new Tuple2(2, "hello, Scala") // (Int, String)

  println(aTuple._1)
  println(aTuple.copy(_2 = "goodbye Java"))
  println(aTuple.swap)

  // Maps - keys -> values
  val aMap: Map[String, Int] = Map()

  val phoneBook = Map(("Jim", 555), "Daniel" -> 789).withDefaultValue(-1)
  // a -> b === (a, b)
  println(phoneBook)

  println(phoneBook.contains("Jim"))
  println(phoneBook("Jim"))
  println(phoneBook("Mary"))


  // add a pairing
  val newPairing = "Mary" -> 678
  val newPhonebook = phoneBook + newPairing
  println(newPhonebook)

  // finctionals on maps
  println(phoneBook.map(pair => pair._1.toLowerCase -> pair._2))

  println(phoneBook.toList)

  println(List(("Jim",555), ("Daniel",789)).toMap)

  val names = List("Bob", "James", "Angela", "Mary", "Daniel", "Jim")
  println(names.groupBy(name => name.charAt(0)))
}
