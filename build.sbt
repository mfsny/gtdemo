name := "gtdemo"

version := "1.0"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
  "com.azavea.geotrellis" %% "geotrellis" % "0.9.0",
  "com.azavea.geotrellis" %% "geotrellis-services" % "0.9.0", // due to geotrellis.services.ColorRampMap
  "io.spray" % "spray-routing" % "1.2.0",
  "io.spray" % "spray-can" % "1.2.0"
/*
  "com.azavea.geotrellis" %% "geotrellis" % "0.10.0-SNAPSHOT",
  "com.azavea.geotrellis" %% "geotrellis-services" % "0.10.0-SNAPSHOT", // due to geotrellis.services.ColorRampMap
  "com.azavea.geotrellis" %% "geotrellis-vector" % "0.10.0-SNAPSHOT", // due to import geotrellis.vector.reproject
  "com.azavea.geotrellis" %% "geotrellis-proj4" % "0.10.0-SNAPSHOT", // due to import import geotrellis.proj4.CRS
  "io.spray" %% "spray-routing" % "1.3.2",
  "io.spray" %% "spray-can" % "1.3.2"
*/
)

//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.6"

//libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % scalaVersion

//ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }