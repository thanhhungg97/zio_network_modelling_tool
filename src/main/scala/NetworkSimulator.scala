import network.NetworkGraph
import traffic.NetworkTraffic

class NetworkSimulator {

  def simulate(networkGraph: NetworkGraph, networkTraffic: List[NetworkTraffic]): Unit = {
    networkTraffic match
      case ::(head, next) =>
        networkGraph.accept(head) match
          case Left(value) => println(value)
          case Right(value) => simulate(value, next)
      case Nil => println("Simulation complete")

  }
}
