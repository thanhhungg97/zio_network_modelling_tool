package network

case class Edge(from: Vertex, to: Vertex, capacity: Int, weight: Int) {
  override def toString(): String = {
    s"Edge: $from -> $to, capacity: $capacity, weight: $weight"
  }

}
