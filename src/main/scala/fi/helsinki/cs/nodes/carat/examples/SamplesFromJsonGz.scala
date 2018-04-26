package fi.helsinki.cs.nodes.carat.examples

import fi.helsinki.cs.nodes.util.Spark2Main
import org.apache.spark.sql.SparkSession
import fi.helsinki.cs.nodes.carat.sample.json._
import scala.collection.Seq

/**
 * Example class on how to read Carat data and print some of it to standard out.
 * Usage: 
 * $SPARK_HOME/bin/spark-submit --class fi.helsinki.cs.nodes.carat.examples.SamplesFromJsonGz \
 *   carat-dataset-tools-with-dependencies.jar --input /path/to/caratdata --output dummy.csv
 * @author Eemil Lagerspetz, University of Helsinki
 */
object SamplesFromJsonGz extends Spark2Main {

  val shortOptions = ""

  val longOptions = Seq("input=", "output=")

  // Cleartext output for this class
  val sparkOutputCompression = false

  def sparkMain(spark: SparkSession) {
    import spark.implicits._

    val input = mandatoryOption("input")
    val output = mandatoryOption("output")
    
    val jsonRdd = readJsonGz[JsonSampleAppExtras](spark, input)
    // If you need access to DataFrame format:
    jsonRdd.toDF.show
    // RDD format
    jsonRdd.take(10).foreach(println(_))
  }
}