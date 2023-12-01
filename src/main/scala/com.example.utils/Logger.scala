package com.example.utils

import org.apache.log4j.{Level, Logger, BasicConfigurator}

object LogManager {
  def setupLogging(): Unit = {
    BasicConfigurator.configure()
    Logger.getRootLogger.setLevel(Level.ERROR)
    Logger.getLogger("org").setLevel(Level.ERROR)
    Logger.getLogger("akka").setLevel(Level.ERROR)
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
  }
}