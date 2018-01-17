package app

import javafx.scene.paint.Color
import loaders.CityLoader
import routing.Graph
import routing.Router
import routing.Vertex
import tornadofx.*

class MyView: View("Routing Graph") {

    private val scalar = 50.0
    private var graph: Graph
    private var router: Router

    init {
        val loader = CityLoader()
        graph = loader.load()
        router = Router(loader.load())
    }

    override val root = stackpane {
        val vertices = graph.vertices
        val edges = graph.edges
        val path = router.route(vertices[0], vertices[10])
        path.forEach { println(it.name) }

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
        }
    }
}