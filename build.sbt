name := "user-service"

version := "0.0.1"

scalaVersion := "2.11.8"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean).settings {
  libraryDependencies ++= Seq(
    jdbc,
    "org.mockito" % "mockito-core" % "1.9.5" % "test"
  )
}
