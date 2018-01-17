package routing

interface Algorithm {
    fun execute(start: Vertex)
    fun path(end: Vertex): List<Vertex>
}