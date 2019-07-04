package map.map

import java.io.File

class Tokens {
    private val homeDir = System.getProperty("user.home")
    private val path = "$homeDir/.gradle/gradle.properties"
    val tokens = File(path).readLines()
            .map { line -> line.split("=") }
            .map { tuple -> tuple[0] to tuple[1] }
            .toMap()
}

fun main() {
    val tokens = Tokens()
    tokens.tokens.forEach { k, v -> println("$k=$v") }
}
