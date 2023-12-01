// Import Spark SQL and Datasets libraries
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Dataset
import org.apache.spark.SparkConf
import org.apache.spark.sql.Encoders
import org.apache.spark.sql.functions._
import org.apache.log4j.{Level, Logger, BasicConfigurator}

import com.example.Main.ReadFiles

object DatasetTransformations{

  // Define a case class that represents the structure of your data
  case class SalesRecord(transactionId: Int, customerId: Int, productId: Int, quantity: Option[Int], price: Double)

  val sparkConfig = ReadFiles.readSparkConfig
  println(s"Sessao configurada com os parametros: $sparkConfig")

  // Create sparkSession
  val spark = SparkSession.builder()
              .master("local[3]")
              .config(sparkConfig)
              .getOrCreate()

  // Import implicits for automatic conversions from RDDs to DataFrames
  import spark.implicits._

  // Load data into a Dataset
  val data = Seq(
    SalesRecord(1, 23, 101, None, 24.99),
    SalesRecord(2, 5, 103, Some(2), 99.95),
    SalesRecord(3, 17, 104, Some(1), 14.95),
    SalesRecord(4, 23, 101, Some(3), 24.99),
    SalesRecord(5, 9, 102, Some(2), 49.50)
  )

  def main(args: Array[String]): Unit ={
    // Configuração do Logger
    BasicConfigurator.configure()
    Logger.getRootLogger.setLevel(Level.ERROR)
    Logger.getLogger("org").setLevel(Level.ERROR)
    Logger.getLogger("akka").setLevel(Level.ERROR)
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    
    val salesDataset = spark.createDataset(data)

    // Show the original Dataset
    // Calcular a média da coluna 'quantity'
    val averageQuantity = salesDataset
      .filter($"quantity".isNotNull)
      .agg(avg($"quantity"))
      .first()
      .getAs[Double](0)

    // Substituir valores nulos pela média na coluna 'quantity'
    val datasetWithReplacedNulls = salesDataset
      .withColumn("quantity", when($"quantity".isNull, lit(averageQuantity)).otherwise($"quantity"))

    // Mostrar o dataset resultante
    datasetWithReplacedNulls.show()
    println("With mean values")

    // Select some columns in Dataset
    println("Selection some Columns")
    salesDataset.select(col("transactionId"), col("CustomerId")).show()

    // Transformation: Filter for a specific customer
    val filteredSales = salesDataset.filter(_.customerId == 23)

    // Show the transformed Dataset
    println("Filtered Dataset for customer ID 23:")
    filteredSales.show()

    // Transformation: Calculate the total sale amount for each transaction
    val salesWithTotal = salesDataset.map {record => 
      val totalAmount = record.quantity match{
        case Some(qty) => qty * record.price
        case None => 0.0
      }
      (record.transactionId, totalAmount)
    }.toDF("transactionId", "totalAmount")

    // Show the transformed Dataset with total sales
    println("Dataset with Total Sales Amount:")
    salesWithTotal.show()

    // Action: Count the number of transactions
    val transactionCount = salesDataset.count()

    // Output the result of the action
    println(s"Total number of transactions: $transactionCount")
  }

}
