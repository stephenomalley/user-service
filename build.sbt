name := "user-service"

version := "1.0.1"

scalaVersion := "2.11.8"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean).settings {
  libraryDependencies ++= Seq(
    evolutions,
    jdbc,
    "org.mockito" % "mockito-core" % "1.10.19" % "test",
    "mysql" % "mysql-connector-java" % "5.1.39",
    "org.json" % "json" % "20160810" % "test",
    "cobertura" % "cobertura" % "1.8" % "test"
  )
}

javaOptions in Test += "-Dconfig.file=conf/application.test.conf"
