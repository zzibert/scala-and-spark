package lectures.part3Concurrency

import java.util.concurrent.Executors

object Intro extends App {
  /*
  * interface Runnable {
  *   public void run()
  * }
  * */
  // JVM threads

  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("running in parallel")
  })

  aThread.start() // gives the signal to the JVM to start a JVM thread
  // create a JVM thread => OS thread
  aThread.join() // blocks until aThread finished running

  val threadHello = new Thread(() => (1 to 5).foreach(_ => println("hello")))
  val threadGoodbye = new Thread(() => (1 to 5).foreach(_ => println("Goodbye")))
//  threadHello.start()
//  threadGoodbye.start()

  // different runs produce different results

  // executors
  val pool = Executors.newFixedThreadPool(10)
  pool.execute(() => println("something in the thread pool"))

  pool.execute(() => {
    Thread.sleep(1000)
    println("done after 1 second")
  })

  pool.execute(() => {
    Thread.sleep(1000)
    println("almost done")
    Thread.sleep(1000)
    println("done after two seconds")
  })

  pool.shutdown()

  println(pool.isShutdown)
//  pool.execute(() => println("should not appear")) // throws an exception in the calling thread

//  pool.shutdownNow()
}
