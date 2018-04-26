package fi.helsinki.cs.nodes.carat.sample.json

/**
 * Network info of a Carat sample.
 * @author Eemil Lagerspetz, University of Helsinki
 */
@SerialVersionUID(1L)
case class NetworkInfoJson(networkType: String, mobileNetworkType: String, mobileDataStatus: String, mobileDataActivity: String,
                           roamingEnabled: Boolean, wifiStatus: String, wifiSignalStrength: Long, wifiLinkSpeed: Long)

