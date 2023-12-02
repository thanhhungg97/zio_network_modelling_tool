package network

import scala.io.BufferedSource

trait NetworkTrafficReader {
  def read(filePath: String): List[NetworkTraffic]
}

object NetworkTrafficReader {
  def apply(): NetworkTrafficReader = TrafficReaderCsv()
}

private[network] case class TrafficReaderCsv() extends NetworkTrafficReader {
  override def read(filePath: String): List[NetworkTraffic] = {
    val bufferedSource = io.Source.fromFile(filePath)
    val trafficList = process(bufferedSource)
    bufferedSource.close()
    trafficList
  }

  private def process(bufferedSource: BufferedSource): List[NetworkTraffic] = {
    bufferedSource.getLines().drop(1).map { line => {
      val cols = line.split(",")
      val from = cols(0)
      val to = cols(1)
      val demand = cols(2).toInt
      NetworkTraffic(Vertex(from), Vertex(to), demand)
    }
    }.toList
  }
}
