import network.{NetworkGraphReader, NetworkSimulator, NetworkTrafficReader, Vertex}

@main def main: Unit =
  val graph = NetworkGraphReader().read("src/main/resources/network_graph.csv")
  val networkTraffic = NetworkTrafficReader().read("src/main/resources/network_traffic.csv")
  graph.print()
  graph.shortedPath(Vertex("A"), Vertex("D")).foreach(println)
  NetworkSimulator().simulate(graph, networkTraffic)
