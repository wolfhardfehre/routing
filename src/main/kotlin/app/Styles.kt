package app

import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    init {
        label {
            fontSize = 11.px
            fontWeight = FontWeight.LIGHT
            backgroundColor += c("#ffffff")
        }
    }
}