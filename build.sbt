import Dependencies._

ThisBuild / scalaVersion     := "2.12.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

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

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
