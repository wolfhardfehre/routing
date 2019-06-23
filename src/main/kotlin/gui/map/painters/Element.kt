package gui.map.painters

import gui.map.styles.Style
import org.jxmapviewer.viewer.GeoPosition

data class Element(
        val positions: MutableList<GeoPosition>,
        val style: Style
)
