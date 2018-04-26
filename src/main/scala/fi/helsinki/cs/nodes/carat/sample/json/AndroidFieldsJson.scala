package fi.helsinki.cs.nodes.carat.sample.json

/**
 * Android-specific fields in a Carat sample.
 * @author Eemil Lagerspetz, University of Helsinki
 */
@SerialVersionUID(1L)
case class AndroidFieldsJson(screenBrightness: Long, cpuUsage: String, uptime: Long, battery: BatteryInfo, networkInfo: NetworkInfoJson,
                             developerMode: Boolean, unknownSources: Boolean, screenOn: Boolean, timeZone: String, extras: Array[StringPair] = null) {
  override def toString() = {
    TypeUtils.arrayAwareToString(this)
  }
}