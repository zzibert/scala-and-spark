package lectures.part3Concurrency

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object FuturesPromises extends App {

  def calculateMeaningOfLife: Int = {
    Thread.sleep(2000)
    42
  }

  val aFuture = Future {
    calculateMeaningOfLife
  }

  aFuture onComplete { t =>
    t match {
      case Success(value) => println(value)
      case Failure(exception) => println(exception)
    }
  }

  Thread.sleep(3000)

}
