package network

trait ShortestPathAlgorithm {
  def shortestPath(source: Vertex, destination: Vertex): List[Vertex]
}

trait SimpleShortestPath(graph: NetworkGraph) extends ShortestPathAlgorithm {
}
