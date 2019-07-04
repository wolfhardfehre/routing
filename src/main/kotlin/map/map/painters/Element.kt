package map.map.painters

import map.map.styles.Style
import org.jxmapviewer.viewer.GeoPosition

data class Element(
        val positions: MutableList<GeoPosition>,
        val style: Style
)
