package lectures.FunctionalProgramming

object TuplesAndMaps extends App {

  // tuples = finite ordered Sets
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

  println(phoneBook.toSet)

  println(Set(("Jim",555), ("Daniel",789)).toMap)

  val names = Set("Bob", "James", "Angela", "Mary", "Daniel", "Jim")
  println(names.groupBy(name => name.charAt(0)))

  /*
  *  What would happen if I had two original entries "Jim" -> 555 and "JIM" -> 900
  * Overly simplified social network based on maps
  *  - Person = String
  *  - add a Person to the network done
  *  - remove a person done
  *  - friend (mutual)
  *  - unfriend (mutual)
  *  - number of friend of a given person
  *  - person with most friends
  *  - how many people have no friends
  *  - if there is a social connection between two people (direct or not)
  * */


  def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] =
    network + (person -> Set())

  def friend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendsA = network(a)
    val friendsB = network(b)

    network + (a -> (friendsA + b)) + (b -> (friendsB + a))
  }

  def unfriend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendsA = network(a)
    val friendsB = network(b)

    network + (a -> (friendsA - b)) + (b -> (friendsB - a))
  }

  def remove(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    def removeAux(friends: Set[String], networkAcc: Map[String, Set[String]]): Map[String, Set[String]] = {
      if (friends.isEmpty) networkAcc
      else removeAux(friends.tail, unfriend(network, friends.head, person))
    }
    val unfriended = removeAux(network(person), network)

    unfriended - person
  }

  val empty: Map[String, Set[String]] = Map()
  val network = add(add(empty, "Mary"), "Bob")
  println(network)
  println(friend(network, "Mary", "Bob"))
  println(unfriend(friend(network, "Mary", "Bob"), "Bob", "Mary"))
  println(remove(friend(network, "Mary", "Bob"), "Bob"))

  // Jim, Bob, Mary
  val people = add(add(add(empty, "Mary"), "Bob"), "Jim")
  val jimBob = friend(people, "Bob", "Jim")
  val maryBob = friend(jimBob, "Bob", "Mary")
  println(maryBob)

  def nFriends(network: Map[String, Set[String]], person: String): Int = {
    if(!network.contains(person)) {
      0
    } else {
      network(person).size
    }
  }

  println(nFriends(maryBob, "Bob"))

  def mostFriends(network: Map[String, Set[String]]): String = {
    network.maxBy(pair => pair._2.size)._1
  }

  println(mostFriends(maryBob))

  def nPeopleWithNoFriends(network: Map[String, Set[String]]): Int = {
    network.count(_._2 == 0)
  }

  println(nPeopleWithNoFriends(maryBob))

  def socialConnection(network: Map[String, Set[String]], a: String, b: String): Boolean = {
    def bfs(target: String, consideredPeople: Set[String], discoveredPeople: Set[String]): Boolean = {
      if (discoveredPeople.isEmpty) false
      else {
        val person = discoveredPeople.head
        if (person == target) true
        else if (consideredPeople.contains(person)) bfs(target, consideredPeople, discoveredPeople.tail)
        else bfs(target, consideredPeople + person, discoveredPeople.tail ++ network(person))
      }
    }
    bfs(b, Set(), network(a) + a)
  }

}
