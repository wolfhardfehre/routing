package loaders

import routing.Graph

interface Loader {
    fun load(): Graph
}