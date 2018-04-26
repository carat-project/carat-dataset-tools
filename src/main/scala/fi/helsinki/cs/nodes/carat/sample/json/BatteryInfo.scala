package fi.helsinki.cs.nodes.carat.sample.json

/**
 * Battery info of a Carat sample.
 * @author Eemil Lagerspetz, University of Helsinki
 */

@SerialVersionUID(1L)
case class BatteryInfo(charger: String, health: String, voltage: Double, temperature: Double, technology: String, capacity: Double)
