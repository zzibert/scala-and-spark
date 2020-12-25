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
}

class EmptySet[A] extends MySet[A] {
  def contains(elem: A): Boolean = false // DONE
  def +(elem: A): MySet[A] = NonEmptySet(elem, this) // DONE
  def ++(anotherSet: MySet[A]): MySet[A] = anotherSet // DONE

  def map[B](f: A => B): MySet[B] = new EmptySet[B] // DONE
  def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B] // DONE
  def filter(predicate: A => Boolean): MySet[A] = this // DONE
  def foreach(f: A => Unit): Unit = () // DONE
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

}







