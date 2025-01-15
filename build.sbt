ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.6.2"

lazy val root = (project in file("."))
  .settings(
    name := "rts_prak3"
  )
This / libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.19" % Test,
  "org.scalactic" %% "scalactic" % "3.2.19",
  "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core" % "2.33.0",
  // Use the "provided" scope instead when the "compile-internal" scope is not supported
  "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % "2.33.0" % "compile-internal"
)
