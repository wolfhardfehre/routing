package gui.map

import gui.map.painters.DiminishingPoints
import gui.map.painters.Element
import gui.map.painters.Line
import gui.map.painters.Points
import gui.map.styles.MapStyle
import org.jxmapviewer.JXMapViewer
import org.jxmapviewer.input.CenterMapListener
import org.jxmapviewer.input.PanKeyListener
import org.jxmapviewer.input.PanMouseInputListener
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor
import org.jxmapviewer.painter.CompoundPainter
import org.jxmapviewer.painter.Painter
import org.jxmapviewer.viewer.DefaultTileFactory
import org.jxmapviewer.viewer.GeoPosition
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
        //draw()
    }

    fun draw() {
        overlayPainter = CompoundPainter(collection.values.toList())
    }
}
