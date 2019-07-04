package map.map

import map.map.painters.DiminishingPoints
import map.map.painters.Element
import map.map.painters.Line
import map.map.painters.Points
import map.map.styles.MapStyle
import org.jxmapviewer.JXMapViewer
import org.jxmapviewer.input.CenterMapListener
import org.jxmapviewer.input.PanKeyListener
import org.jxmapviewer.input.PanMouseInputListener
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor
import org.jxmapviewer.painter.CompoundPainter
import org.jxmapviewer.painter.Painter
import org.jxmapviewer.viewer.DefaultTileFactory
import org.jxmapviewer.viewer.GeoPosition
import java.awt.geom.Rectangle2D
import java.util.concurrent.LinkedBlockingQueue

class MapView(token: String, mapStyle: MapStyle) : JXMapViewer() {
    private val collection: MutableMap<String, Painter<JXMapViewer>> = mutableMapOf()
    private lateinit var current: GeoPosition

    init {
        val pan = PanMouseInputListener(this)
        val provider = Mapbox(token, mapStyle.tileStyle)
        addMouseListener(pan)
        addMouseMotionListener(pan)
        addMouseListener(CenterMapListener(this))
        addMouseWheelListener(ZoomMouseWheelListenerCursor(this))
        addKeyListener(PanKeyListener(this))
        tileFactory = DefaultTileFactory(provider)
        this.zoom = mapStyle.zoom
    }

    fun focus(position: GeoPosition) {
        current = position
        addressLocation = current
    }

    fun points(name: String, element: Element) {
        val data = LinkedBlockingQueue<GeoPosition>(element.positions)
        collection[name] = Points(name, positions = data, style = element.style)
    }

    fun diminish(name: String, element: Element) {
        val data = LinkedBlockingQueue<GeoPosition>(element.positions)
        collection[name] = DiminishingPoints(name, positions = data, style = element.style)
    }

    fun line(name: String, element: Element) {
        val data = LinkedBlockingQueue<GeoPosition>(element.positions)
        collection[name] = Line(name, positions = data, style = element.style)
    }

    fun remove(name: String) {
        listOf("POINT", "DIMINISH", "LINE").forEach {
            collection.remove("${name}_$it")
        }
    }

    fun draw() {
        overlayPainter = CompoundPainter(collection.values.toList())
    }
}
