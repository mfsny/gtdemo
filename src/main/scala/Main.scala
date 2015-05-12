package tutorial
//package com.allianz.gda.gtdemo

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import com.typesafe.config.ConfigFactory
import spray.can.Http


object Main {
  def main(args: Array[String]) {
    // we need an ActorSystem to host our service
    implicit val system = ActorSystem()

    /* from java -D
    val queryInterface = Option(System.getProperty("query.interface")).getOrElse("localhost")
    val queryPort = Try(System.getProperty("query.port").toInt).toOption.getOrElse(8080)

    val staticPath = Option(System.getProperty("geotrellis.server.static-path")).getOrElse("static")
    */

    /* aus application.conf: */
    val config = ConfigFactory.load()
    val queryInterface = config.getString("geotrellis.hostname")
    val queryPort = config.getInt("geotrellis.port")
    val staticPath = config.getString("geotrellis.server.static-path")
    println(queryInterface)
    println(queryPort)
    println(staticPath)

    //create our service actor
    val service = system.actorOf(Props(classOf[gtdemoActor], staticPath), "gtdemo-service")

    //bind our actor to an HTTP port
    IO(Http) ! Http.Bind(service, interface = queryInterface, port = queryPort)
  }
}