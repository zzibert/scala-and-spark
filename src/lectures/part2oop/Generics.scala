package lectures.part2oop

object Generics extends App{

  class MyList [A] {
    // use the type A
    def add[B >: A](element: B): MyList[B] = ???
    /*
      A - Cat
      B - Dog
    */

  }

  class MyMap[Key, Value]

  val listOfIntegers = new MyList[Int]
  val listOFStrings = new MyList[String]

  // generic methods
  object MyList {
    def empty[A]: MyList[A] = ???
  }
  val emptyListOfIntegers = MyList.empty[Int]

  // Variance problem
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  // List[Cat] extends List[Animal] = COVARIANCE
  class CovariantList[+A]
  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
  // animalList.add(new Dog)

  // second option: INVARIANCE
  class InvariantList[A]

  // Hell, no : Contravariance
  class Trainer[-A]
  val trainer: Trainer[Cat] = new Trainer[Animal]

  // BOUNDED TYPES
  class Cage[A <: Animal](animal: A)
  val cage = new Cage[Dog](new Dog)

  class Car
  //val newCage = new Cage[Car](new Car)



}
