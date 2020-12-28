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
    private var queue = ArrayBuffer[Int](1)

    def isEmpty: Boolean = queue.size == 0
    def set(newValue: Int): Unit = queue += newValue
    def get: Int = {
      val result = queue.remove(0)
      set(result + 1)
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
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]
    val capacity = 500

    class Consumer(id: Int, buffer: mutable.Queue[Int]) extends Thread {
      override def run(): Unit = {
        val random = new Random()
        while(true) {
          buffer.synchronized {
            while (buffer.isEmpty) {
              println(s"[consumer $id] buffer empty, waiting...")
              buffer.wait()
            }
            val value = buffer.dequeue
            println(s"[consumer $id] im consuming " + value)
            buffer.notify()

          }
          Thread.sleep(random.nextInt(500))
        }
      }
    }

    def consumer(index: Int, buffer: mutable.Queue[Int]): Thread = {
      new Thread(() => {
        val random = new Random()
        while(true) {
          buffer.synchronized {
            if (buffer.isEmpty) {
              println(s"[consumer $index] buffer empty, waiting...")
              buffer.wait()
            } else {
              val value = buffer.dequeue
              println(s"[consumer $index] im consuming " + value)
              buffer.notify()
            }
          }
          Thread.sleep(random.nextInt(2000))
        }
      })
    }

  @volatile var generator = 1

  class Producer(id: Int, buffer: mutable.Queue[Int], capacity: Int) extends Thread {
    override def run(): Unit = {
      val random = new Random()
      while(true) {
        buffer.synchronized {
          while (buffer.size == capacity) {
            println(s"[producer $id] buffer is currently full, waiting...")
            buffer.wait()
          }
          generator += 1
          val value = generator
          println(s"[producer $id] producing value: " + value)
          buffer.addOne(value)
          buffer.notify()

        }
        Thread.sleep(random.nextInt(250))
      }
    }
  }

    def producer(index: Int, buffer: mutable.Queue[Int]): Thread = {
      new Thread(() => {
        val random = new Random()
        while(true) {
          buffer.synchronized {
            if (buffer.size == capacity) {
              println(s"[producer $index] buffer is currently full, waiting...")
              buffer.wait()
            } else {
              generator += 1
              val value = generator
              println(s"[producer $index] producing value: " + value)
              buffer.addOne(value)
              buffer.notify()
            }
          }
          Thread.sleep(random.nextInt(250))
        }
      })
    }

  val consumers = (1 to 5).map(new Consumer(_, buffer)).toList

  val producers = (1 to 5).map(new Producer(_, buffer, capacity)).toList

  producers.foreach(_.start())
  consumers.foreach(_.start())

//  prodConsLargeBuffer()
  /*
  * producer -> [? ? ? ] -> consumer
  * */

  /*
  * producer1 -> [? ? ?] -> consumer1
  * producer2 -> same buffer -> consumer2
  * */


}
