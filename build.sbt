name := "Ludo"
organization := "de.htwg.se.ludo"
version := "0.2.0"
scalaVersion := "2.13.3"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test"
libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"

coverageExcludedPackages := ".*gui.*;.*Ludo.*"