import Dependencies._

ThisBuild / scalaVersion := "2.12.10"
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
    libraryDependencies ++= cassandraDependencies
  )

// Project euler solutions
lazy val euler = project.settings(name := "ProjectEulerScala").dependsOn(common)

// Google guava cache
lazy val cache = project.settings(
  name := "Google Guava Cache",
  libraryDependencies += "com.google.guava" % "guava" % "28.2-jre"
)

// A common package to share code between projects
lazy val common = project.settings(name := "Common")
// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
