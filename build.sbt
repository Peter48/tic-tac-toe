name := "TicTacToe-root"

version := "0.0.1"

scalaVersion := "2.11.8"

lazy val api = project.
  settings(Common.settings: _*).
  settings(libraryDependencies ++= Dependencies.apiDependencies)

lazy val console = project.
  dependsOn(api, domain).
  settings(Common.settings: _*).
  settings(libraryDependencies ++= Dependencies.consoleDependencies)

lazy val domain = project.
  dependsOn(api).
  settings(Common.settings: _*).
  settings(libraryDependencies ++= Dependencies.domainDependencies)

lazy val web = project.
  dependsOn(api, domain % "test->test;compile->compile").
  settings(Common.settings: _*).
  settings(libraryDependencies ++= Dependencies.webDependencies).
  enablePlugins(PlayScala)

lazy val spark = project.
  dependsOn(api, domain).
  settings(Common.settings: _*).
  settings(libraryDependencies ++= Dependencies.sparkDependencies)

lazy val akka = project.
  dependsOn(api, domain).
  settings(Common.settings: _*).
  settings(libraryDependencies ++= Dependencies.akkaDependencies)

lazy val root = (project in file(".")).
  aggregate(api, console, domain, web, spark, akka)