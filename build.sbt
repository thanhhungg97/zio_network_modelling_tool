import sbt.Keys.libraryDependencies

val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "network_tool",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "org.scalactic" %% "scalactic" % "3.2.17" % Test,
      "org.scalatest" %% "scalatest" % "3.2.17" % "test",
      "org.scalatest" %% "scalatest" % "latest.integration" % Test)

  )
