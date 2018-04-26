package fi.helsinki.cs.nodes.carat.util

import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.Writer
import java.net.URL

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.databind.DeserializationFeature

/**
 * JSON - case class conversion utilities.
 * @author Eemil Lagerspetz, University of Helsinki
 */
object JsonUtil {
  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  def fromJson[T](json: URL)(implicit m: Manifest[T]): T = {
    mapper.readValue[T](json)
  }

  def fromJson[T](json: String)(implicit m: Manifest[T]): T = {
    mapper.readValue[T](json)
  }

  def fromJson[T](json: InputStream)(implicit m: Manifest[T]): T = {
    mapper.readValue[T](json)
  }

  def fromJson[T](json: InputStreamReader)(implicit m: Manifest[T]): T = {
    mapper.readValue[T](json)
  }

  def writeJson(out: OutputStream, value: Any) = mapper.writeValue(out, value)

  def writeJson(out: Writer, value: Any) = mapper.writeValue(out, value)

  def toJsonString(value: Any) = mapper.writeValueAsString(value)
}
