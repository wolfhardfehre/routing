package routing

data class Vertex(private val id: String,
                  val name: String,
                  val x: Double,
                  val y: Double) {

    val neighbors = HashMap<Vertex, Int>()

    fun neighbor(neighbor: Vertex, weight: Int) {
        neighbors.put(neighbor, weight)
    }

    override fun hashCode(): Int = id.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val o = other as Vertex?
        return id == o!!.id
    }

    override fun toString(): String = name
}