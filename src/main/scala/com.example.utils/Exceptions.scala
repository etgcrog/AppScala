package com.example.utils

import java.io.FileNotFoundException
import java.io.IOException

object ExceptionHandler {

  def handleFileNotFoundException(e: FileNotFoundException): Unit = {
    println("Arquivo de configuração não encontrado: " + e.getMessage)
  }

  def handleIOException(e: IOException): Unit = {
    println("Erro ao ler o arquivo de configuração: " + e.getMessage)
  }

  // Você pode adicionar métodos adicionais para outras exceções conforme necessário.
}