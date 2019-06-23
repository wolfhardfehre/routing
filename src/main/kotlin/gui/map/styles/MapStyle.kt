package gui.map.styles

import gui.map.Mapbox

data class MapStyle(val tileStyle: Mapbox.Base = Mapbox.Base.LIGHT,
                    val zoom: Int = 7)
