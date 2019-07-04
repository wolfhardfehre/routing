package map.map.styles

import map.map.painters.ALL
import map.map.painters.BLUE
import java.awt.Color


sealed class Type {
    object Point : Type()
    object Line : Type()
    object Diminish : Type()
}


data class Style(val fillColor: Color = BLUE,
                 val strokeWidth: Float = 2F,
                 val diameter: Double = 10.0,
                 val type: Type = Type.Point,
                 var limit: Int = ALL)
