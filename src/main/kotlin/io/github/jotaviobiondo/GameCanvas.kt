package io.github.jotaviobiondo

import java.awt.*
import java.awt.image.BufferStrategy

class GameCanvas(val width: Int, val height: Int) {

    enum class Align {
        START, END, CENTER
    }

    val canvas = Canvas()

    private val bufferStrategy: BufferStrategy by lazy {
        canvas.createBufferStrategy(2)
        canvas.bufferStrategy
    }

    private lateinit var g2d: Graphics2D

    init {
        canvas.preferredSize = Dimension(width, height)
    }

    fun render(renderGame: () -> Unit) {
        this.g2d = bufferStrategy.drawGraphics as Graphics2D

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2d.color = Color.WHITE
        g2d.fillRect(0, 0, canvas.width, canvas.height)

        renderGame()

        g2d.dispose()
        bufferStrategy.show()
    }

    fun withColor(color: Color, doPainting: Graphics2D.() -> Unit) {
        val oldColor = g2d.color
        g2d.color = color

        g2d.doPainting()

        g2d.color = oldColor
    }

    fun drawString(
        str: String,
        xOffset: Int = 0,
        yOffset: Int = 0,
        xAlign: Align = Align.START,
        yAlign: Align = Align.START
    ) {
        val stringBounds = g2d.fontMetrics.getStringBounds(str, g2d)
        val stringWidth = stringBounds.width.toInt()
        val stringHeight = stringBounds.height.toInt()

        val x = when (xAlign) {
            Align.START -> 0
            Align.END -> width - stringWidth
            Align.CENTER -> (width / 2) - (stringWidth / 2)
        }

        val y = when (yAlign) {
            Align.START -> stringHeight
            Align.END -> height
            Align.CENTER -> (height / 2) - (stringHeight / 2)
        }

        g2d.drawString(str, x + xOffset, y + yOffset)
    }

}