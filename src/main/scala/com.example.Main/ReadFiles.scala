package com.example.Main

import scala.io._
import java.util.Properties
import org.apache.spark.SparkConf

object ReadFiles {
    def readSparkConfig: SparkConf = {
        val sparkConfigObject = new SparkConf()
        val properties = new Properties()
        try {
            properties.load(Source.fromFile("spark.conf").bufferedReader())
            properties.forEach { (key, value) => 
                sparkConfigObject.set(key.toString, value.toString)
            }
        } catch {
            case e: FileNotFoundException => println("Arquivo de configuração não encontrado.")
            case e: IOException => println("Erro ao ler o arquivo de configuração.")
        }
        sparkConfigObject
    }

    def main(args: Array[String]): Unit = {
        val config = readSparkConfig
        println(config.toDebugString)
    }
}
