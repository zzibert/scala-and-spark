package ATourOfScala

object GenericClasses extends App {

  class Stack[A] {
    private var elements: List[A] = Nil
    def push(x: A) { elements = x :: elements }
    def peek: A = elements.head
    def pop(): A = {
      val currentTop = peek
      elements = elements.tail
      currentTop
    }
  }

  val stack = new Stack[Int]
  stack.push(1)
  stack.push(2)
  println(stack.pop)  // prints 2
  println(stack.pop)  // prints 1

  class Fruit
  class Apple extends Fruit
  class Banana extends Fruit

  val stack1 = new Stack[Fruit]
  val apple = new Apple
  val banana = new Banana

  stack1.push(apple)
  stack1.push(banana)
}
