package routing

class Router(private val graph: Graph) {

    fun route(from: Vertex, to: Vertex): List<Vertex> {
        val dijkstra = Dijkstra(graph)
        dijkstra.execute(from)
        return dijkstra.path(to)
    }
}