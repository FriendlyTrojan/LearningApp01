name := """uiplay"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "9.3-1102-jdbc41",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.6.Final",
  "org.webjars" %% "webjars-play" % "2.3.0",
  "org.webjars" % "bootstrap" % "3.2.0",
  "org.testng" % "testng" % "6.8.8" % "test",
  "org.springframework" % "spring-context" % "4.0.6.RELEASE",
  "org.springframework" % "spring-test" % "4.0.6.RELEASE",
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  javaJpa
)
