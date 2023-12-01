package com.example.utils

import org.apache.log4j.{FileAppender, Level, Logger, PatternLayout}

object LogConfiguration {
  def configureLogging(): Unit = {
    val currentDirectory = new java.io.File(".").getCanonicalPath
    val logFilePath = s"$currentDirectory/logs/myapp.log"

    val layout = new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n")
    val fileAppender = new FileAppender(layout, logFilePath, true)

    val rootLogger = Logger.getRootLogger
    rootLogger.setLevel(Level.ERROR)
    rootLogger.addAppender(fileAppender)
  }
}