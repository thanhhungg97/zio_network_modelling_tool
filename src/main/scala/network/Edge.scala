package network

case class Edge(from: Vertex, to: Vertex, capacity: Int, weight: Int, used: Int = 0) {
  override def toString(): String = {
    s"Edge: $from -> $to, capacity: $capacity, weight: $weight"
  }

  def use(demand: Int): Either[Throwable, Edge] = {
    if (used + demand > capacity) Left(new Exception("Edge is overused"))
    else Right(
      this.copy(used = used + demand))
  }

}
