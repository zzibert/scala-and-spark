package lectures.OOP

import playgroud.ScalaPlayground.{Cinderella => Princess, PrinceCharming => Prince}

import java.util.Date

import java.sql.{Date => SqlDate}


object PackigingAndImports extends App {

  // package members are accesible by their siple name
  val Writer = new Writer("Daniel", "RockTheJVM", 2018)

  val princess = new Princess // fully qualified name

  // packages are in hierarchy
  // matching folder structure

  // package object
  sayHello
  println(SPEED_OF_LIGHT)

  // imports
  val prince = new Prince

  val date = new Date
  val sqlDate = new SqlDate(2018, 5, 4)

  // default imports
  // java.lang = String, Object, Exception, ...
  // scala = Int, Nothing, Function
  // scala.Predef = println, ???
}
