package network

import traffic.*

trait NetworkGraph {
  def addEdge(edge: Edge): NetworkGraph

  def shortedPath(from: Vertex, to: Vertex): Option[List[Edge]]


  def print(): Unit

  def accept(networkTraffic: NetworkTraffic): Either[Throwable, NetworkGraph]
}

private[network] case class AdjacencyNetworkGraph(graphInternal: Map[Vertex, Set[Edge]]) extends NetworkGraph {
  def addEdge(edge: Edge): NetworkGraph = {
    val current: Set[Edge] = graphInternal.getOrElse(edge.from, Set())
    val newEdges = graphInternal + (edge.from -> (current + edge))
    AdjacencyNetworkGraph(newEdges)
  }

  override def print(): Unit = {
    val builder = new StringBuilder()
    graphInternal.foreach { case (vertex, edges) =>
      builder.append(s"$vertex -> ${edges.map(_.to).mkString(", ")}\n")
    }
    println(builder.toString())
  }

  def shortedPath(from: Vertex, to: Vertex): Option[List[Edge]] = {
    def loop(from: Vertex, to: Vertex, visited: Set[Vertex], path: List[Edge]): Option[List[Edge]] = {
      if (from == to) Some(path.reverse)
      else {
        val edges = this.graphInternal.getOrElse(from, List())
        val newVisited = visited + from
        val newEdges = edges.filterNot(edge => newVisited.contains(edge.to))
        val newPaths = newEdges.map(edge => loop(edge.to, to, newVisited, edge :: path))
        newPaths.filter(_.isDefined).map(_.get).toList.sortBy(_.length).headOption
      }
    }

    loop(from, to, Set(), List())
  }

  override def accept(networkTraffic: NetworkTraffic): Either[Throwable, NetworkGraph] = {
    val path: Option[List[Edge]] = shortedPath(networkTraffic.from, networkTraffic.to)
    path.toRight(Exception("No path found")).flatMap { path =>
      path.foldLeft[Either[Throwable, NetworkGraph]](Right(this)) { (graph, edge) =>
        graph.flatMap(g => edge.use(networkTraffic.demand).map(g.addEdge))
      }
    }
  }
}

object NetworkGraph {
  def apply(): NetworkGraph = AdjacencyNetworkGraph(Map())
}
