package routing

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class Dijkstra(graph: Graph) : Algorithm {

    private var vertices: MutableSet<Vertex> = HashSet(graph.vertices)
    private var predecessors: MutableMap<Vertex, Vertex?> = HashMap()
    private var distances: MutableMap<Vertex, Int> = HashMap()

    override fun path(end: Vertex): List<Vertex> {
        val path = ArrayList<Vertex>()
        var tmp = end
        while (predecessors[tmp] != null) {
            tmp = predecessors[tmp]!!
            path.add(tmp)
        }
        Collections.reverse(path)
        return path
    }

    override fun execute(start: Vertex) {
        initialize(start, vertices)
        while (!vertices.isEmpty()) {
            val vertex = minimum()
            vertices.remove(vertex)
            vertex.neighbors.forEach { if (it.key in vertices) updateDistance(it.value, it.key, vertex) }
            distances.remove(vertex)
        }
    }

    private fun initialize(start: Vertex, vertices: Set<Vertex>) {
        vertices.forEach {
            distances[it] = Int.MAX_VALUE
            predecessors[it] = null
        }
        distances[start] = 0
    }

    private fun minimum(): Vertex {
        var min = vertices.first()
        distances.forEach { if (distances[min]!! > it.value) min = it.key }
        return min
    }

    private fun updateDistance(distance: Int, neighbor: Vertex, vertex: Vertex) {
        val tmp = distances[vertex]!! + distance
        if (tmp < distances[neighbor]!!) {
            distances[neighbor] = tmp
            predecessors[neighbor] = vertex
        }
    }
}
