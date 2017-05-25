package tictactoe.spark

import org.apache.spark.{SparkContext, SparkConf}

object SparkMain extends App {


  def sc = {
    val conf= new SparkConf
    conf.setMaster("local")
    conf.setAppName("example")
    new SparkContext(conf)
  }

	println("Hello World Spark's version of TicTacToe")
}