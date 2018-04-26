package fi.helsinki.cs.nodes.util

import org.apache.spark.sql.SparkSession
import scala.reflect.ClassTag
import org.apache.spark.rdd.RDD
import org.apache.hadoop.io.compress.GzipCodec
import scala.reflect.runtime.universe._

/**
 * Class to simplify Spark 2.0+ usage and working with JSON data with complex schemas.
 * @author Eemil Lagerspetz, University of Helsinki
 */
trait Spark2Main extends OptMain {
  /**
   * Whether to compress Spark outputs. Required.
   */
  val sparkOutputCompression: Boolean

  /**
   * Main entry point. Configures Spark and parses args for options specified in `shortOptSpec` and `longOptSpec` (see getopt-scala docs).
   */
  def sparkMain(spark: SparkSession)

  /**
   * Main entry point. Configures Spark and parses args, then passes control to [[fi.helsinki.cs.nodes.carat.util.SparkMain#sparkMain]] .
   */
  def optMain() {
    val sb = SparkSession
      .builder()
      .appName(getClass.getName.replaceAll("$", ""))

    val spark = {
      if (sparkOutputCompression)
        enableCompression(sb).getOrCreate()
      else
        sb.getOrCreate()
    }

    sparkMain(spark)
  }

  private def enableCompression(sb: SparkSession.Builder) = {
    sb.config("spark.hadoop.mapred.output.compress", true)
      .config("spark.hadoop.mapred.output.compression.codec", true)
      .config("spark.hadoop.mapred.output.compression.codec", "org.apache.hadoop.io.compress.GzipCodec")
      .config("spark.hadoop.mapred.output.compression.type", "BLOCK")
  }
  
  /**
   * Save an RDD of any CaseClass as a text json gz RDD. 
   */
  def saveAsJsonGz[T](rdd: RDD[T], path: String) = {
    val jsRdd = rdd.map(fi.helsinki.cs.nodes.carat.util.JsonUtil.toJsonString)
    jsRdd.saveAsTextFile(path, classOf[GzipCodec])
  }

  /**
   * Read a text json gz RDD to inferred CaseClass type RDD.
   */
  def readJsonGz[T <: Product:TypeTag](spark: SparkSession, path: String): RDD[T] = {
    import org.apache.spark.sql.Encoders
    import spark.implicits._
    val mySchema = Encoders.product[T].schema
    val rdd = spark.read.schema(mySchema).json(path).as[T].rdd
    rdd
  }
}
