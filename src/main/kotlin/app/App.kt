package app

import loaders.CityLoader
import map.MapWindow
import map.map.Mapbox
import map.map.Tokens
import map.map.painters.BLUE
import map.map.painters.GREEN
import map.map.painters.ORANGERED
import map.map.painters.VIOLETT
import map.map.styles.MapStyle
import map.map.styles.Style
import map.map.styles.Type
import org.jxmapviewer.viewer.GeoPosition
import routing.Edge
import routing.Graph
import routing.Router
import routing.Vertex


fun List<Vertex>.toPositionSet() = this
        .map{ vertex -> GeoPosition(vertex.y, vertex.x) }
        .toMutableSet()


class App {
    private val token = Tokens().tokens.getValue("MAPBOX_TOKEN")
    private val mapStyle = MapStyle(tileStyle = Mapbox.Base.LIGHT, zoom = 12)
    private val window = MapWindow(token, mapStyle)
    private val handler = window.mapHandler
    private val graph: Graph
    private val router: Router
    private val edges: List<Edge>
    private var vertices: List<Vertex>

    init {
        val loader = CityLoader()
        graph = loader.load()
        router = Router(loader.load())
        edges = graph.edges
        vertices = graph.vertices
    }

    fun show() {
        val edgeStyle = Style(fillColor = GREEN, strokeWidth = 4F, type = Type.Line)
        val vertexStyle = Style(fillColor = BLUE, diameter = 30.0, type = Type.Point)
        val routeStyle = Style(fillColor = VIOLETT, strokeWidth = 4F, type = Type.Line)
        val visitStyle = Style(fillColor = ORANGERED, diameter = 30.0, type = Type.Point)

        edges.forEach {
            val from = GeoPosition(it.start.y, it.start.x)
            val to = GeoPosition(it.end.y, it.end.x)
            val positions = mutableListOf(from, to)
            handler.updates("${it.id}-EDGE", positions, edgeStyle)
        }

        vertices.forEach {
            val coordinate = GeoPosition(it.y, it.x)
            handler.update("${it.id}-VERTEX", coordinate, vertexStyle)
        }

        var predecessor: Vertex? = null
        val path = router.route(vertices[1], vertices[8])
        path.forEach {
            val coordinate = GeoPosition(it.y, it.x)
            handler.update("${it.id}-VISIT", coordinate, visitStyle)
            if (predecessor != null) {
                val from = GeoPosition(predecessor?.y!!, predecessor?.x!!)
                val to = GeoPosition(it.y, it.x)
                val positions = mutableListOf(from, to)
                handler.updates("${it.id}-PATH", positions, routeStyle)
            }
            predecessor = it
        }

        handler.draw()
    }
}

fun main() {
    val app = App()
    app.show()
}
