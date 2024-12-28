val scala3Version = "3.6.2"
val http4sVersion = "1.0.0-M44"

lazy val root = project
  .in(file("."))
  .settings(
    name := "tensor_be",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "org.typelevel" %% "cats-effect" % "3.5.7",
      "org.http4s" %% "http4s-ember-server" % http4sVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.typelevel" %% "log4cats-slf4j" % "2.7.0",
      "com.github.pureconfig" %% "pureconfig-core" % "0.17.8",
    ),
  )
