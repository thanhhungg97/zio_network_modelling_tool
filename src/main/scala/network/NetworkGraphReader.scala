package network

import scala.io.BufferedSource

trait NetworkGraphReader {
  def read(filePath: String): NetworkGraph
}

object NetworkGraphReader {
  def apply(): NetworkGraphReader = NetWorkGraphReaderCsv()
}


private[network] case class NetWorkGraphReaderCsv() extends NetworkGraphReader {
  override def read(filePath: String): NetworkGraph = {
    val bufferedSource = io.Source.fromFile(filePath)
    val graph = process(bufferedSource)
    bufferedSource.close
    graph
  }

  private def process(bufferedSource: BufferedSource): NetworkGraph = {
    var graph = NetworkGraph()
    bufferedSource.getLines.drop(1)
      .foreach(line => {

        val cols = line.split(",").map(_.trim)
        val from = cols(1)
        val to = cols(2)
        val capacity = cols(3)
        val weight = cols(4)
        graph = graph.addEdge(Edge(Vertex(from), Vertex(to), capacity.toInt, weight.toInt))
      })
    graph
  }
}
