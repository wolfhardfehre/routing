package loaders

import nominatim.RestApi
import nominatim.Place
import routing.Edge
import routing.Graph
import routing.Vertex

class CityLoader : Loader {

    private val vertices = ArrayList<Vertex>()
    private val edges = ArrayList<Edge>()
    private val restApi = RestApi()
    private val cities = listOf("Frankfurt", "München", "Dresden", "Berlin", "Hamburg",
            "Rostock", "Münster", "Duisburg", "Erfurt", "Leipzig", "Hannover")

    override fun load(): Graph {
        loadCities()
        loadLanes()
        return Graph(vertices, edges)
    }

    private fun loadCities() {
        (0..10).mapTo(vertices) {
            val city = cities[it]
            val call = restApi.getPlace(city)
            val response = call.execute()
            if (response.isSuccessful) {
                val places: List<Place>? = response.body()
                val place: Place? = places?.get(0)
                Vertex(it.toString(), city, place?.lon!!.toDouble(), 90 - place.lat.toDouble())
            } else {
                Vertex(it.toString(), city, 0.0, 0.0)
            }
        }
    }

    private fun loadLanes() {
        addLane("Edge_0", 0, 1, 85)
        addLane("Edge_1", 0, 2, 217)
        addLane("Edge_2", 0, 4, 173)
        addLane("Edge_3", 2, 6, 186)
        addLane("Edge_4", 2, 7, 103)
        addLane("Edge_5", 3, 7, 183)
        addLane("Edge_6", 5, 8, 250)
        addLane("Edge_7", 8, 9, 84)
        addLane("Edge_8", 7, 9, 167)
        addLane("Edge_9", 4, 9, 502)
        addLane("Edge_10", 9, 10, 40)
        addLane("Edge_11", 1, 10, 600)
    }

    private fun addLane(laneId: String, startNumber: Int, endNumber: Int, weight: Int) {
        val lane = Edge(laneId, vertices[startNumber], vertices[endNumber], weight)
        edges.add(lane)
    }
}