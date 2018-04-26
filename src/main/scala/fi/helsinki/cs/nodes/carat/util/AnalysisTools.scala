package fi.helsinki.cs.nodes.carat.util

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import scala.collection.immutable.HashSet

object AnalysisTools {

  // Daemons file on the Carat website
  val DAEMON_URL = "http://carat.cs.helsinki.fi/daemons.txt"

  // Daemons list, read via HTTP
  lazy val DAEMONS_LIST = {
    var a = readHttpFile(DAEMON_URL)
    while (a == null){
      println(s"Retrying read of daemons from $DAEMON_URL because of failure")
      Thread.sleep(500)
      a = readHttpFile(DAEMON_URL) 
    }
    a
  }

  /**
   * Read a text file via a HTTP url into a set of lines.
   */
  def readHttpFile(url: String) = {
    var r: Set[String] = new HashSet[String]
    try {
      val u = new URL(url)
      val c = u.openConnection()
      val is = c.getInputStream()
      if (is != null) {
        val rd = new BufferedReader(new InputStreamReader(is))
        var s = rd.readLine()
        while (s != null) {
          r += s
          s = rd.readLine()
        }
        rd.close()
        //println("%s downloaded: %s".format(url, r))
        r
      } else
        null
    } catch {
      case th: Throwable => null
    }
  }

  /**
   * Remove from `allApps` any occurrences of daemons and their glob-expanded forms, e.g. android*
   */
  def daemons_globbed(allApps: Set[String]) = {
    val globs = DAEMONS_LIST.filter(_.endsWith("*")).map(x => { x.substring(0, x.length - 1).toLowerCase })

    var matched = allApps.filter(x => {
      val globPrefix = globs.filter(x.toLowerCase.startsWith(_))
      !globPrefix.isEmpty
    })

    //println("Matched daemons with globs: " + matched)
    val ret = DAEMONS_LIST.map(_.toLowerCase) ++ matched
    ret
  }

  /**
   * Return true if the app is a daemon, either matching a something* pattern, or the entire app name.
   */
  def isDaemonGlobbed(app: String) = {
    val globs = DAEMONS_LIST.filter(_.endsWith("*")).map(x => { x.substring(0, x.length - 1).toLowerCase })
    val globPrefix = globs.filter(app.toLowerCase.startsWith(_))
    !globPrefix.isEmpty || DAEMONS_LIST.filter(x => !x.endsWith("*")).contains(app)
  }

  /**
   * Return true if the given user id is an iOS device.
   * @author Eemil Lagerspetz, University of Helsinki
   */
  def iosCond(s: String) = s.length > 16 && s.filter(c => c.isDigit || c == '-' || c.isUpper).length == s.length
  
    /**
   * List of Carat app priorities.
   * From
   * https://github.com/carat-project/carat/blob/master/app/android/src/edu/berkeley/cs/amplab/carat/android/Constants.java
   * line 67 ->
   *
   */

  val caratPriorities = Set("Background process",
    "Service",
    "Visible task",
    "Foreground app",
    "Perceptible task",
    "foreground service",
    /*"Suggestion", // This one is Carat UI only, not sent over the network */
    "Not running",
    "uninstalled",
    "disabled",
    "installed",
    "replaced").map(_.toLowerCase)

  /**
   * Priorities that we don't allow at the moment.
   */
  val notRunningPriorities = Set("Not running",
    "uninstalled",
    "disabled",
    "installed",
    "replaced").map(_.toLowerCase)
  
  def foregroundPriority(appPriority:String) = {
    val p = appPriority.toLowerCase()
    p == "foreground" || p == "visible"
  }
}
