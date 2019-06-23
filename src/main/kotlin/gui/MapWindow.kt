package gui

import gui.map.MapHandler
import gui.map.MapView
import gui.map.styles.MapStyle
import java.awt.BorderLayout
import java.awt.GridLayout
import javax.swing.JFrame
import javax.swing.JPanel

class MapWindow(token: String, mapStyle: MapStyle) : JFrame("Map") {
    private val mainLayout = JPanel(GridLayout(1, 1))
    private var mapView = MapView(token, mapStyle)
    val mapHandler = MapHandler(mapView)

    init {
        setDefaultLookAndFeelDecorated(false)
        defaultCloseOperation = EXIT_ON_CLOSE
        contentPane.add(mainLayout(), BorderLayout.CENTER)
        setSize(1800, 1000)
        isVisible = true
    }

    fun reset() {
        mapHandler.reset()
    }

    private fun mainLayout(): JPanel {
        mainLayout.add(mapView)
        return mainLayout
    }
}
