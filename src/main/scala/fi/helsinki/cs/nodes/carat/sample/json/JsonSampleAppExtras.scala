package fi.helsinki.cs.nodes.carat.sample.json

import scala.util.Failure
import scala.util.Success

import fi.helsinki.cs.nodes.carat.util.AnalysisTools

/**
 * Case class version of Carat sample for reading the JSON version of the Carat dataset.
 * Variant with Array extras for new samples with lots of extras, but with backward compatibility.
 * @author Eemil Lagerspetz, University of Helsinki
 */
@SerialVersionUID(1L)
case class JsonSampleAppExtras(uuid: String, time: Long, batteryLevel: Long,
                      triggeredBy: String, batteryState: String, apps: Array[JsonApplicationArrayExtras],
                      memory: MemoryInfo, networkStatus: String, distanceTraveled: Double,
                      androidFields: AndroidFieldsJson = null) {
  import JsonSampleAppExtras._
  
  override def toString() = {
    TypeUtils.arrayAwareToString(this)
  }
}

object JsonSampleAppExtras{
    def charger(s: JsonSampleAppExtras) = {
    if (s.androidFields == null)
      Failure(new Error("androidFields==null"))
    else if (s.androidFields.battery == null)
      Failure(new Error("battery == null"))
    else if (s.androidFields.battery.charger == null)
      Failure(new Error("charger == null"))
    else
      Success(s.androidFields.battery.charger.toLowerCase)
  }

  def batteryTemperature(s: JsonSampleAppExtras) = {
    if (s.androidFields == null)
      Failure(new Error("androidFields==null"))
    else if (s.androidFields.battery == null)
      Failure(new Error("battery == null"))
    else
      Success(s.androidFields.battery.temperature + "")
  }

  def batteryVoltage(s: JsonSampleAppExtras) = {
    if (s.androidFields == null)
      Failure(new Error("androidFields==null"))
    else if (s.androidFields.battery == null)
      Failure(new Error("battery == null"))
    else
      Success(s.androidFields.battery.voltage + "")
  }

  def screenBrightness(s: JsonSampleAppExtras) = {
    if (s.androidFields == null)
      Failure(new Error("androidFields==null"))
    else
      Success(s.androidFields.screenBrightness + "")
  }
  
  def isScreenOn(s: JsonSampleAppExtras) = {
    if (s.androidFields == null)
      Failure(new Error("androidFields==null"))
    else
      Success(s.androidFields.screenOn + "")
  }
  
  def safeForeground(a: fi.helsinki.cs.nodes.carat.sample.json.JsonApplicationArrayExtras) = {
    a != null && a.priority != null && AnalysisTools.foregroundPriority(a.priority)
  }

  def foregroundAppCount(s: JsonSampleAppExtras) = {
    if (s.apps == null)
      Failure(new Error("apps == null"))
    else
      Success(s.apps.count(safeForeground) + "")
  }
  
  def cpuLevels(s: JsonSampleAppExtras) = {
    if (s.androidFields == null)
      Failure(new Error("androidFields==null"))
    else
      Success(s.androidFields.cpuUsage + "")
  }
  
  def uptime(s: JsonSampleAppExtras) = {
    if (s.androidFields == null)
      Failure(new Error("androidFields==null"))
    else
      Success(s.androidFields.uptime + "")
  }
}

/**
 *
 * Represent an application, with all fields named for compactness.
 *
 * @author Eemil Lagerspetz
 */

@SerialVersionUID(2L)
case class JsonApplicationArrayExtras(appSignatures: Array[String] = null, extras: Array[StringPair] = null,
                           installationPkg: String = null, pid: Long = -1,
                           priority: String = null, processName: String,
                           systemApp: Boolean = false,
                           translatedName: String = null,
                           versionCode: Long = -1,
                           versionName: String = null) extends Serializable {

  override def toString() = {
    TypeUtils.arrayAwareToString(this)
  }
}