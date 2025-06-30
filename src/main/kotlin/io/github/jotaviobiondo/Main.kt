package io.github.jotaviobiondo

import java.awt.EventQueue

fun main() {
    EventQueue.invokeLater {
        val game = Game()
        game.start()
    }
}
