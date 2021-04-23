import play.sbt.PlayImport.jdbc

name := """PlayREST"""
organization := "com.aj"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.2"


libraryDependencies ++= Seq(
  guice,
  javaJdbc,
  "mysql" % "mysql-connector-java" % "8.0.23"
)
