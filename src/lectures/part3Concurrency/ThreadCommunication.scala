package lectures.part3Concurrency

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object ThreadCommunication extends App {
  /*
  * the producer-consumer problem
  * producer -> [ x ] -> consumer
  * */

  class SimpleContainer {
    private var queue = ArrayBuffer[Int]()

    def isEmpty: Boolean = queue.size == 0
    def set(newValue: Int): Unit = queue += newValue
    def get: Int = {
      val result = queue.remove(0)
      result
    }
  }

  def naiveProdCons(): Unit = {
    val container = new SimpleContainer

    val consumer = new Thread(() => {
      println("[consumer] waiting ...")
      while(container.isEmpty) {
        println("[consumer] actively waiting ...")
      }
      println("[consumer] I have consumed " + container.get)
    })

    val producer = new Thread(() => {
      println("[producer] computing...")
      Thread.sleep(500)
      val value = 42
      println("[producer] I have produced, after long work, the value " + value)
      container.set(value)
    })

    consumer.start()
    producer.start()
  }

//  naiveProdCons()

  // wait and notify
  def prodConsLargeBuffer(): Unit = {
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]
    val capacity = 3

    val consumer = new Thread(() => {
      val random = new Random()

      while(true) {
        buffer.synchronized {
          if (buffer.isEmpty) {
            println("[consumer] buffer empty, waiting...")
            buffer.wait()
          } else {
            val value = buffer.dequeue()
            println("[consumer] im consuming " + value)
            buffer.notify()
          }
        }
        Thread.sleep(random.nextInt(5000))

      }

    })
    val producer = new Thread(() => {
      var value = 1
      val random = new Random(

      )
      while(true) {
        buffer.synchronized {
          if (buffer.size == capacity) {
            println("[producer] buffer is currently full, waiting...")
            buffer.wait()
          } else {
            value += 1
            println("[producer] producing value: " + value)
            buffer.addOne(value)
            buffer.notify()
          }
        }
        Thread.sleep(random.nextInt(500))
      }

    })

    producer.start()
    consumer.start()
  }

  prodConsLargeBuffer()
  /*
  * producer -> [? ? ? ] -> consumer
  * */
}
