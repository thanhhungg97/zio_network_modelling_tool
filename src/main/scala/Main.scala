import network.{NetworkGraphReader, Vertex}

@main def main: Unit =
  val graph = NetworkGraphReader().read("src/main/resources/network_graph.csv")
  graph.print()
  graph.shortedPath(Vertex("A"), Vertex("D")).foreach(println)
