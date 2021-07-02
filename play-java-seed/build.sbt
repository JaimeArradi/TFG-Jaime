name := """play-java-seed"""
organization := "com.rest"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.2"

libraryDependencies += guice

libraryDependencies ++= Seq(
   javaJdbc
)

libraryDependencies ++= Seq(
  "org.freemarker" % "freemarker" % "2.3.31"
)

libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "8.0.23"
)
