name := "Spark DataFrame Example"

version := "0.1"

scalaVersion := "2.12.18"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.4.1",
  "org.apache.spark" %% "spark-sql" % "2.4.1",
)