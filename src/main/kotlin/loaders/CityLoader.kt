package loaders

import nominatim.RestApi
import nominatim.Place
import routing.Edge
import routing.Graph
import routing.Vertex


private val CITIES = listOf("Frankfurt", "München", "Dresden", "Berlin", "Hamburg",
        "Rostock", "Münster", "Duisburg", "Erfurt", "Leipzig", "Hannover")


class CityLoader : Loader {
    private val restApi = RestApi()
    private val vertices: List<Vertex> = loadCities()
    private val edges: List<Edge> = loadLanes()

    override fun load(): Graph {
        return Graph(vertices, edges)
    }

    private fun loadCities() = CITIES.mapIndexed { index, city ->
            val places = restApi.getPlace(city)
            if (places.isEmpty()) {
                return@mapIndexed Vertex(index.toString(), city, 0.0, 0.0)
            }
            val place: Place = places[0]
            return@mapIndexed Vertex(index.toString(), city, place.lon, place.lat)
        }

    private fun loadLanes() = listOf(
            edge("Edge_0", 0, 1, 85),
            edge("Edge_1", 0, 2, 217),
            edge("Edge_2", 0, 4, 173),
            edge("Edge_3", 2, 6, 186),
            edge("Edge_4", 2, 7, 103),
            edge("Edge_5", 3, 7, 183),
            edge("Edge_6", 5, 8, 250),
            edge("Edge_7", 8, 9, 84),
            edge("Edge_8", 7, 9, 167),
            edge("Edge_9", 4, 9, 502),
            edge("Edge_10", 9, 10, 40),
            edge("Edge_11", 1, 10, 600)
        )

    private fun edge(laneId: String, startNumber: Int, endNumber: Int, weight: Int) =
        Edge(laneId, vertices[startNumber], vertices[endNumber], weight)
}
