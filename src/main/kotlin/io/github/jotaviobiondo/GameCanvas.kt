package io.github.jotaviobiondo

import java.awt.Canvas
import java.awt.Color
import java.awt.Component
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.Shape
import java.awt.image.BufferStrategy

class GameCanvas(val width: Int, val height: Int) {

    enum class Align {
        START, END, CENTER
    }

    private val canvas = Canvas()

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
        g2d.color = Color.BLACK
        g2d.fillRect(0, 0, canvas.width, canvas.height)

        renderGame()

        g2d.dispose()
        bufferStrategy.show()
    }

    fun withColor(color: Color, doPainting: GameCanvas.() -> Unit) {
        val oldColor = g2d.color
        g2d.color = color

        this.doPainting()

        g2d.color = oldColor
    }

    fun withFont(font: Font, doPainting: GameCanvas.() -> Unit) {
        val oldFont = g2d.font
        g2d.font = font

        this.doPainting()

        g2d.font = oldFont
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

    fun draw(shape: Shape) = g2d.draw(shape)

    fun drawFilled(shape: Shape) = g2d.fill(shape)

    fun drawLine(x1: Int, y1: Int, x2: Int, y2: Int) = g2d.drawLine(x1, y1, x2, y2)

    fun drawRect(x: Int, y: Int, width: Int, height: Int) = g2d.drawRect(x, y, width, height)

    fun drawFilledRect(x: Int, y: Int, width: Int, height: Int) = g2d.fillRect(x, y, width, height)

    fun drawOval(x: Int, y: Int, width: Int, height: Int) = g2d.drawOval(x, y, width, height)

    fun drawFilledOval(x: Int, y: Int, width: Int, height: Int) = g2d.fillOval(x, y, width, height)

    fun toAwtComponent(): Component = canvas

}