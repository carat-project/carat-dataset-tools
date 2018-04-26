package fi.helsinki.cs.nodes.carat.sample.json

/**
 * Memory info of a Carat sample.
 * @author Eemil Lagerspetz, University of Helsinki
 */

@SerialVersionUID(1L)
case class MemoryInfo(memoryWired: Long, memoryActive: Long, memoryInactive: Long, memoryFree: Long, memoryUser: Long)
