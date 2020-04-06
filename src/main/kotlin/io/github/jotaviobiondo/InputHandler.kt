package io.github.jotaviobiondo

import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class InputHandler(private vararg val keysOfInterest: Int) : KeyListener {

    private var keyEvents = mutableListOf<Int>()


    override fun keyTyped(e: KeyEvent) {
    }

    override fun keyPressed(e: KeyEvent) {
    }

    override fun keyReleased(e: KeyEvent) {
        val keyCode = e.keyCode
        if (keyCode in keysOfInterest) {
            keyEvents.add(keyCode)
        }
    }

    fun popFirstKey(action: (Int) -> Unit) {
        if (keyEvents.isNotEmpty()) {
            val firstKey = keyEvents.removeAt(0)
            action(firstKey)
        }
    }

}