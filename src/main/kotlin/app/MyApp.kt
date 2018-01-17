package app

import javafx.stage.Stage
import tornadofx.*

class MyApp : App(MyView::class, Styles::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 800.0
        stage.height = 600.0
    }
}