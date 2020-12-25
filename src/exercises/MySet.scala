package exercises

trait MySet[A] extends (A => Boolean) {

  def contains(elem: A): Boolean
  def +(elem: A): MySet[A]
  def ++(anotherSet: MySet[A]): MySet[A]

  def map[B](f: A => B): MySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B]
  def filter(predicate: A => Boolean): MySet[A]
  def foreach(f: A => Unit): Unit
  def apply(elem: A): Boolean = contains(elem) // DONE

  def -(elem: A): MySet[A]
  def --(anotherSet: MySet[A]): MySet[A]
  def &(anotherSet: MySet[A]): MySet[A]

  def unary_! : MySet[A]

  // 1.  removing an element
  // intersection with another set
  // -- with another set
}

class EmptySet[A] extends MySet[A] {
  def contains(elem: A): Boolean = false // DONE
  def +(elem: A): MySet[A] = NonEmptySet(elem, this) // DONE
  def ++(anotherSet: MySet[A]): MySet[A] = anotherSet // DONE

  def map[B](f: A => B): MySet[B] = new EmptySet[B] // DONE
  def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B] // DONE
  def filter(predicate: A => Boolean): MySet[A] = this // DONE
  def foreach(f: A => Unit): Unit = () // DONE

  def -(elem: A): MySet[A] = this
  def &(anotherSet: MySet[A]): MySet[A] = this
  def --(anotherSet: MySet[A]): MySet[A] = this

  def unary_! : MySet[A] = new PropertyBasedSet[A](_ => true)
}

class AllInclusiveSet[A] extends MySet[A] {
  def contains(elem: A): Boolean = true
  def +(elem: A): MySet[A] = this
  def ++(anotherSet: MySet[A]): MySet[A] = this

  def map[B](f: A => B): MySet[B] = ???
  def flatMap[B](f: A => MySet[B]): MySet[B] = ???
  def filter(predicate: A => Boolean): MySet[A] = ??? // property-based set
  def foreach(f: A => Unit): Unit = ???

  def -(elem: A): MySet[A] = ???
  def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)
  def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)

  def unary_! : MySet[A] = new EmptySet[A]
}

// all elements of type A which satisfy a property
class PropertyBasedSet[A](property: A => Boolean) extends MySet[A] {
  def contains(elem: A): Boolean = property(elem)

  def +(elem: A): MySet[A] = {
    if (property(elem)) this
    else new PropertyBasedSet[A]({ x =>
      property(x) || elem == x
    })
  }

  def ++(anotherSet: MySet[A]): MySet[A] = new PropertyBasedSet[A]({ x =>
    property(x) || anotherSet(x)
  })

  def map[B](f: A => B): MySet[B] = politelyFail

  def flatMap[B](f: A => MySet[B]): MySet[B] = politelyFail

  def filter(predicate: A => Boolean): MySet[A] = new PropertyBasedSet[A]({x =>
    predicate(x) && property(x)
  })

  def foreach(f: A => Unit): Unit = politelyFail

  def -(elem: A): MySet[A] = filter(x => x != elem)

  def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)

  def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)

  def unary_! : MySet[A] = new PropertyBasedSet[A](x => !property(x))

  def politelyFail = throw new IllegalArgumentException("Really deep rabbit hole!")
}

case class NonEmptySet[A](head: A, tail: MySet[A]) extends MySet[A] {
  def contains(elem: A): Boolean = head == elem || tail.contains(elem) // DONE

  def +(elem: A): MySet[A] = { // DONE
    if (this contains elem ) this
    else new NonEmptySet[A](elem, this)
  }
  def ++(anotherSet: MySet[A]): MySet[A] = { // DONE
    tail ++ anotherSet + head
  }

  def map[B](f: A => B): MySet[B] = tail.map(f) + f(head) // DONE

  def flatMap[B](f: A => MySet[B]): MySet[B] = tail.flatMap(f) ++ f(head) // DONE

  def filter(predicate: A => Boolean): MySet[A] = { // DONE
    val filteredTail = tail filter predicate
    if (predicate(head)) filteredTail + head
    else filteredTail
  }
  def foreach(f: A => Unit): Unit = { // DONE
    f(head)
    tail foreach f
  }

  def -(elem: A): MySet[A] = { // DONE
    if (head == elem) tail
    else tail - elem + head
  }

  def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)

  def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)

  def unary_! : MySet[A] = new PropertyBasedSet[A](!this(_))
}

object MySet {
  def apply[A](values: A*): MySet[A] = {
    def buildSet(valSeq: Seq[A], acc: MySet[A]): MySet[A] = {
      if (valSeq.isEmpty) acc
      else buildSet(valSeq.tail, acc + valSeq.head)
    }
    buildSet(values.toSeq, new EmptySet[A])
  }
}

object MySetPlayground extends App {
  val s = MySet(1, 2, 3, 4)

  s ++ MySet(-1, -2) + 3 flatMap (MySet(_)) foreach println

  val negative = !s
  println(negative(2))
  println(negative(5))

  val negativeEven = negative filter (_ % 2 == 0)

  val negativeEven5 = negative + 5

  println(negativeEven5(5))

}







