package app

import loaders.CityLoader
import gui.MapWindow
import gui.map.Mapbox
import gui.map.Tokens
import gui.map.painters.GREEN
import gui.map.styles.MapStyle
import gui.map.styles.Style
import gui.map.styles.Type
import org.jxmapviewer.viewer.GeoPosition
import routing.Edge
import routing.Graph
import routing.Router
import routing.Vertex


class App {
    private val token = Tokens().tokens.getValue("MAPBOX_TOKEN")
    private val mapStyle = MapStyle(tileStyle = Mapbox.Base.LIGHT, zoom = 7)
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
        val shapeStyle = Style(fillColor = GREEN, strokeWidth = 4F, type = Type.Line)

        edges.forEach {
            val from = GeoPosition(it.start.y, it.start.x)
            val to = GeoPosition(it.end.y, it.end.x)
            println("$from, $to")
            val positions = mutableListOf(from, to)
            handler.updates("${it.id}-EDGE", positions, shapeStyle)

        }
        handler.draw()

        /*
        group {

            edges.forEach {
                line {
                    startX = it.start.x * scalar
                    startY = it.start.y * scalar
                    endX = it.end.x * scalar
                    endY = it.end.y * scalar
                }
            }

            vertices.forEach {
                circle {
                    centerX = it.x * scalar
                    centerY = it.y * scalar
                    radius = 5.0
                }
                label {
                    layoutX = it.x * scalar
                    layoutY = it.y * scalar
                    text = it.name
                }
            }


            var predecessor: Vertex? = null
            path.forEach {
                circle {
                    fill = Color.CORAL
                    centerX = it.x * scalar
                    centerY = it.y * scalar
                    radius = 5.0
                }
                label {
                    textFill = Color.CORAL
                    layoutX = it.x * scalar
                    layoutY = it.y * scalar
                    text = it.name
                }
                if (predecessor != null) {
                    line {
                        stroke = Color.CORAL
                        startX = predecessor?.x!! * scalar
                        startY = predecessor?.y!! * scalar
                        endX = it.x * scalar
                        endY = it.y * scalar
                    }
                }
                predecessor = it
            }
        }*/
    }
}

fun main() {
    val app = App()
    app.show()
}
