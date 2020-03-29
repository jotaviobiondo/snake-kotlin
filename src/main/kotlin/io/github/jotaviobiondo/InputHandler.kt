package io.github.jotaviobiondo

import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class InputHandler : KeyListener {

    private var keyEvents = mutableListOf<Int>()


    override fun keyTyped(e: KeyEvent) {
    }

    override fun keyPressed(e: KeyEvent) {
    }

    override fun keyReleased(e: KeyEvent) {
        keyEvents.add(e.keyCode)
    }

    fun popFirstKey(action: (Int) -> Unit) {
        if (keyEvents.isNotEmpty()) {
            val firstKey = keyEvents.removeAt(0)
            action(firstKey)
        }
    }

}