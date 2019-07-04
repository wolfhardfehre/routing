package map.map

import map.map.painters.Element
import map.map.styles.Style
import map.map.styles.Type
import org.jxmapviewer.viewer.GeoPosition

class MapHandler(private val mapView: MapView) {
    private val elements = mutableMapOf<String, Element>()

    init {
        mapView.focus(GeoPosition(52.517788, 13.403991))
    }

    @Synchronized fun update(id: String, position: GeoPosition, style: Style) {
        updates(id, mutableListOf(position), style)
    }

    @Synchronized fun updates(id: String, positions: MutableList<GeoPosition>, style: Style) {
        updateElements(id, positions, style)
        elements.forEach { entry -> drawElement(entry) }
    }

    @Synchronized fun remove(id: String) {
        elements.remove(id)
        mapView.remove(id)
    }

    @Synchronized fun draw() {
        mapView.draw()
    }

    @Synchronized fun reset() {
        elements.clear()
    }

    private fun updateElements(id: String, positions: MutableList<GeoPosition>, style: Style) {
        if (elements.containsKey(id)) elements[id]!!.positions.addAll(positions)
        else elements[id] = Element(positions, style)
    }

    private fun drawElement(entry: Map.Entry<String, Element>) {
        when (entry.value.style.type) {
            is Type.Point -> mapView.points("${entry.key}_POINT", entry.value)
            is Type.Diminish -> mapView.diminish("${entry.key}_DIMINISH", entry.value)
            is Type.Line -> mapView.line("${entry.key}_LINE", entry.value)
        }
    }
}
