package routing

data class Edge(private val id: String,
                val start: Vertex,
                val end: Vertex,
                val weight: Int) {

    init {
        start.neighbor(end, weight)
        end.neighbor(start, weight)
    }

    override fun hashCode(): Int = id.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val o = other as Edge?
        return id == o!!.id
    }

    override fun toString(): String = "$start o---o $end"
}