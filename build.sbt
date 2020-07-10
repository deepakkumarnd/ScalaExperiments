import Dependencies._

ThisBuild / scalaVersion := "2.13.1"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "io.github.basicobject"
ThisBuild / organizationName := "BasicObject"

lazy val cassandraDependencies = Seq(
  "com.datastax.oss" % "java-driver-core" % "4.5.0",
  "com.datastax.oss" % "java-driver-query-builder" % "4.5.0"
)

lazy val root = (project in file("."))
  .settings(
    name := "ScalaExperiments",
    libraryDependencies += scalaTest % Test,
    libraryDependencies ++= cassandraDependencies,
    libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.3"
  )
  .dependsOn(common)

// Project euler solutions
lazy val euler = project.settings(name := "ProjectEulerScala").dependsOn(common)

// Google guava cache
lazy val cache = project.settings(
  name := "Google Guava Cache",
  libraryDependencies += "com.google.guava" % "guava" % "28.2-jre"
)

// A common package to share code between projects
lazy val common = project.settings(name := "Common")

lazy val postgres = project.settings(
  name := "Postgres",
  scalacOptions += "-Ypartial-unification",
  libraryDependencies ++= Seq(
    "org.tpolecat" %% "doobie-core" % "0.8.8",
    "org.tpolecat" %% "doobie-postgres" % "0.8.8"
  )
)

lazy val gameoflife = project.settings(name := "Conways Game of Life")
lazy val wsclient = project.settings(
  name := "WsClient standalone mode",
  libraryDependencies ++= Seq(
    "com.typesafe.play" %% "play-ahc-ws-standalone" % "2.1.2",
    "com.typesafe.play" %% "play-ws-standalone-json" % "2.1.2"
  )
)

lazy val cassandra = project.settings(
  name := "Cassandra",
  libraryDependencies ++= Seq(
    "com.datastax.oss" % "java-driver-core" % "4.5.1",
    "org.scala-lang.modules" %% "scala-java8-compat" % "0.9.1"
  )
)

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
