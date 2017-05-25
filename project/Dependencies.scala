import sbt._

object Dependencies {

  val commonDependencies: Seq[ModuleID] = Seq(
    "org.scalatest" % "scalatest_2.11" % "3.0.1" % "test",
    "org.scalacheck" % "scalacheck_2.11" % "1.13.4"
  )

  val apiDependencies: Seq[ModuleID] = commonDependencies

  val consoleDependencies: Seq[ModuleID] = commonDependencies

  val domainDependencies: Seq[ModuleID] = commonDependencies

  val sparkVersion = "2.1.0"
  val sparkDependencies: Seq[ModuleID] = commonDependencies ++ Seq(
    "org.apache.spark" %% "spark-core_2.11" % sparkVersion,
    "org.apache.spark" %% "spark-sql_2.11" % sparkVersion,
    "org.apache.spark" %% "spark-mllib_2.11" % sparkVersion,
    "org.apache.spark" %% "spark-streaming_2.11" % sparkVersion)

  val akkaVersion = "2.5.0"
  val akkaDependencies: Seq[ModuleID] = commonDependencies ++ Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion)

  val webDependencies: Seq[ModuleID] = commonDependencies ++ akkaDependencies ++ Seq(
    "com.typesafe.play" %% "play-json" % "2.4.2")
}