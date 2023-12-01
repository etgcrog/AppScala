package com.example.utils

//Spark Modules
import scala.io._
import org.apache.spark.SparkConf
// Java Modules
import java.util.Properties
import java.io.{FileNotFoundException, IOException}

object ReadFiles {
    def readSparkConfig: SparkConf = {
        val sparkConfigObject = new SparkConf()
        val properties = new Properties()
        try {
            properties.load(Source.fromFile("spark.conf").bufferedReader())
            properties.forEach { case (key, value) => 
                sparkConfigObject.set(key.toString, value.toString)
            }
        } catch {
            case e: FileNotFoundException => ExceptionHandler.handleFileNotFoundException(e)
            case e: IOException => ExceptionHandler.handleIOException(e)
        }
        sparkConfigObject
    }

    // def main(args: Array[String]): Unit = {
    //     val config = readSparkConfig
    //     println(config.toDebugString)
    // }
}
