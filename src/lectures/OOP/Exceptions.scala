package lectures.OOP

import java.nio.BufferOverflowException

object Exceptions extends App {

  val x: String = null
//  println(x.length)
  // this will crash with a nullPointerException
  // throwing and catching exceptions

//  val aWeirdValue: String = throw new NullPointerException

  // throwable classes extend the Throwable class.
  // Exceptions and Error are the major throwable subtypes

  // 2. how to catch exceptions
  def getInt(withExceptions: Boolean): Int =
    if (withExceptions) throw new RuntimeException("no int for you")
    else 42

  // use finally only for side effects

  // 3. how to define your own exceptions
  class MyException extends Exception

  val exception = new MyException


  // 1. crash your program with an outofmemoryError
  // 2. crash with a stackoverflow
  // 3. pocket calculator
  // add(x, y)
  // subtract(x, y)
  // multiply(x, y)
  // divide (x, y)
  // throw - overflowException if add exceeds int.Max_VALUE
  // UnderflowException if sbutract exceeds int.MIN_VALUE
  // MathCalculationException for division by zero

  class OverFlowException extends Exception

  class UnderFlowException extends Exception

  class MathCalculationException extends Exception

  class PocketCalculator {
    def add(x: Int, y: Int) = {
      if (x > 0 && y > 0 && x + y < 0) throw new OverFlowException
      else x + y
    }

    def subtract(x: Int, y: Int) = {
      if (x < y && (x - y) > 0) throw new UnderFlowException
      else x - y
    }

    def multiply(x: Int, y: Int) = {
      if (x * y < 0) throw new OverFlowException
      else x * y
    }

    def divide(x: Int, y: Int) = {
      if (y == 0) throw new MathCalculationException
      else x / y
    }

  }
}
