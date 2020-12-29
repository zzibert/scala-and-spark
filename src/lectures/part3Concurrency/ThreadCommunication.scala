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

//  producers.foreach(_.start())
//  consumers.foreach(_.start())

//  prodConsLargeBuffer()
  /*
  * producer -> [? ? ? ] -> consumer
  * */

  /*
  * producer1 -> [? ? ?] -> consumer1
  * producer2 -> same buffer -> consumer2
  * */

  /*
  * Exercise notifyAll
  * create a deadlock
  * create a livelock
  * */
  def testNotifyAll(): Unit = {
    val bell = new Object

    (1 to 10).foreach(i => new Thread(() => {
      bell.synchronized {
        println(s"[Thread $i] waiting ...")
        bell.wait()
        println(s"[Thread $i]  hooray!")
      }
    }).start())

    new Thread(() => {
      Thread.sleep(2000)
      println("[announcer] Rockn n Roll")
      bell.synchronized {
        bell.notify()
      }
    }).start()

  }

//  testNotifyAll()

  // 2 - DEADLOCK
  case class Friend(name: String) {
    def bow(other: Friend) = {
      this.synchronized {
        println(s"$this: I am bowing to my friend $other")
        other.rise(this)
        println(s"$this: my friend $other has risen")
      }
    }
    def rise(other: Friend): Unit = {
      this.synchronized {
        println(s"$this: I am rising to my friend $other")
        other.rise(this)
        println(s"$this: I am rising to my friend $other")
      }
    }
    var side = "right"

    def switchSide(): Unit = {
      if (side == "right") side = "left"
      else side = "right"
    }

    def pass(other: Friend): Unit = {
      while (this.side == other.side) {
        println(s"$this: oh, but please, $other, feel free to pass ...")
        switchSide()
        Thread.sleep(1000)
      }
    }
  }

  val sam = Friend("Sam")
  val pierre = Friend("Pierre")

//  new Thread(() => sam.bow(pierre)).start()
//  new Thread(() => pierre.bow(sam)).start()

  // livelock

  new Thread(() => sam.pass(pierre)).start()
  new Thread(() => pierre.pass(sam)).start()



}
