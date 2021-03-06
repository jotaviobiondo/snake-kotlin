package io.github.jotaviobiondo

import java.awt.Canvas
import java.awt.Color
import java.awt.Component
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics2D
import java.awt.Point
import java.awt.RenderingHints
import java.awt.Shape
import java.awt.image.BufferStrategy

class GameCanvas(val width: Int, val height: Int) {

    enum class Align {
        START, END, CENTER
    }

    companion object {
        val DEFAULT_COLOR: Color = Color.BLACK
        val DEFAULT_FONT = Font(Font.MONOSPACED, Font.PLAIN, 18)
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
        clear(Color.BLACK)

        renderGame()

        g2d.dispose()
        bufferStrategy.show()
    }

    fun draw(
        color: Color = DEFAULT_COLOR,
        doDrawing: GameCanvas.() -> Unit
    ) {
        withColor(color) {
            this.doDrawing()
        }
    }

    fun shape(shape: Shape, fill: Boolean = false) =
        if (fill) g2d.fill(shape) else g2d.draw(shape)

    fun line(x1: Int, y1: Int, x2: Int, y2: Int) =
        g2d.drawLine(x1, y1, x2, y2)

    fun rect(x: Int, y: Int, width: Int, height: Int, fill: Boolean = false) =
        if (fill) g2d.fillRect(x, y, width, height) else g2d.drawRect(x, y, width, height)

    fun rect(startPoint: Point, width: Int, height: Int, fill: Boolean = false) =
        rect(startPoint.x, startPoint.y, width, height, fill)

    fun square(x: Int, y: Int, length: Int, fill: Boolean = false) =
        rect(x, y, length, length, fill)

    fun square(startPoint: Point, length: Int, fill: Boolean = false) =
        square(startPoint.x, startPoint.y, length, fill)

    fun oval(x: Int, y: Int, width: Int, height: Int, fill: Boolean = false) =
        if (fill) g2d.fillOval(x, y, width, height) else g2d.drawOval(x, y, width, height)

    fun oval(startPoint: Point, diameter: Int, fill: Boolean) =
        oval(startPoint.x, startPoint.y, diameter, diameter, fill)

    fun circle(x: Int, y: Int, diameter: Int, fill: Boolean = false) =
        oval(x, y, diameter, diameter, fill)

    fun circle(startPoint: Point, diameter: Int, fill: Boolean = false) =
        circle(startPoint.x, startPoint.y, diameter, fill)

    fun clear(color: Color = DEFAULT_COLOR) {
        draw(color) {
            rect(0, 0, width, height, fill = true)
        }
    }

    fun string(
        str: String,
        xOffset: Int = 0,
        yOffset: Int = 0,
        xAlign: Align = Align.START,
        yAlign: Align = Align.START,
        fontSize: Int = DEFAULT_FONT.size,
        fontStyle: Int = DEFAULT_FONT.style
    ) {
        withFont(DEFAULT_FONT.deriveFont(fontStyle, fontSize.toFloat())) {
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

    private inline fun withColor(color: Color, doPainting: () -> Unit) {
        val oldColor = g2d.color
        g2d.color = color

        doPainting()

        g2d.color = oldColor
    }

    private inline fun withFont(font: Font, doPainting: () -> Unit) {
        val oldFont = g2d.font
        g2d.font = font

        doPainting()

        g2d.font = oldFont
    }

    fun toAwtComponent(): Component = canvas

}