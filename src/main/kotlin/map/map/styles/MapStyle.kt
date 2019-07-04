package map.map.styles

import map.map.Mapbox

data class MapStyle(val tileStyle: Mapbox.Base = Mapbox.Base.LIGHT,
                    val zoom: Int = 2)
