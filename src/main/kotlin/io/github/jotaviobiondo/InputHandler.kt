package io.github.jotaviobiondo

import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

class InputHandler(private vararg val keysOfInterest: Int) : KeyAdapter() {

    private val keyEvents = mutableListOf<Int>()

    override fun keyReleased(e: KeyEvent) {
        val keyCode = e.keyCode
        if (keysOfInterest.isEmpty() || keyCode in keysOfInterest) {
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