name := "user-service"

version := "1.0.0"

scalaVersion := "2.11.8"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean).settings {
  libraryDependencies ++= Seq(
    jdbc,
    "org.mockito" % "mockito-core" % "1.10.19" % "test",
    "org.scalatest" % "scalatest_2.11" % "2.2.2" % "test"
  )
}
