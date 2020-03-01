package lectures.part1basics

object DefaultArgs extends App {
  def savePicture(format: String = "jpg", width: Int = 800, height: Int = 1200): Unit = println("picture")

  savePicture(width = 1200)
}
