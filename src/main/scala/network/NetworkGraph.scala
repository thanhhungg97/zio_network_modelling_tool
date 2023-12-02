package network

trait NetworkGraph {
  def addEdge(edge: Edge): NetworkGraph

  def shortedPath(from: Vertex, to: Vertex): Option[List[Edge]]

  def print(): Unit

}

private[network] case class AdjacencyNetworkGraph(edges: Map[Vertex, List[Edge]]) extends NetworkGraph {
  def addEdge(edge: Edge): NetworkGraph = {
    val newEdges = edges + (edge.from -> (edge :: edges.getOrElse(edge.from, List())))
    AdjacencyNetworkGraph(newEdges)
  }

  override def print(): Unit = {
    val builder = new StringBuilder()
    edges.foreach { case (vertex, edges) =>
      builder.append(s"$vertex -> ${edges.map(_.to).mkString(", ")}\n")
    }
    println(builder.toString())
  }

  def shortedPath(from: Vertex, to: Vertex): Option[List[Edge]] ={
    def loop(from: Vertex, to: Vertex, visited: Set[Vertex], path: List[Edge]): Option[List[Edge]] = {
      if (from == to) Some(path.reverse)
      else {
        val edges = this.edges.getOrElse(from, List())
        val newVisited = visited + from
        val newEdges = edges.filterNot(edge => newVisited.contains(edge.to))
        val newPaths = newEdges.map(edge => loop(edge.to, to, newVisited, edge :: path))
        newPaths.filter(_.isDefined).map(_.get).sortBy(_.length).headOption
      }
    }
    loop(from, to, Set(), List())
  }

}

object NetworkGraph {
  def apply(): NetworkGraph = AdjacencyNetworkGraph(Map())
}
