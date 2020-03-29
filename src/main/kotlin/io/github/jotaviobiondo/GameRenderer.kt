package io.github.jotaviobiondo

import java.awt.*
import java.awt.image.BufferStrategy

class GameRenderer(private val game: Game) {

    private enum class Align {
        START, END, CENTER
    }

    companion object {
        const val PIXELS_PER_BOARD_POINT = 15
        const val BORDER_SIZE = 50
    }

    val canvas = Canvas()

    private val backgroundColor = Color(39, 59, 69)
    private val boardBorderColor = Color(105, 136, 148)
    private val boardGridColor = Color(62, 78, 90)
    private val snakeColor = Color(43, 140, 215)
    private val foodColor = Color.RED
    private val textColor = Color.WHITE


    private val boardBounds: Rectangle
        get() {
            val boardWidthInPixels = (game.board.width * PIXELS_PER_BOARD_POINT) + PIXELS_PER_BOARD_POINT
            val boardHeightInPixels = (game.board.height * PIXELS_PER_BOARD_POINT) + PIXELS_PER_BOARD_POINT

            return Rectangle(BORDER_SIZE, BORDER_SIZE, boardWidthInPixels, boardHeightInPixels)
        }


    private val bufferStrategy: BufferStrategy
        get() {
            val bs = canvas.bufferStrategy

            if (bs == null) {
                canvas.createBufferStrategy(2)
                return canvas.bufferStrategy
            }

            return bs
        }

    private val graphics: Graphics2D
        get() {
            return bufferStrategy.drawGraphics as Graphics2D
        }

    init {
        setupCanvasDimensions()
    }


    private fun setupCanvasDimensions() {
        val width = (BORDER_SIZE * 2) + boardBounds.width
        val height = (BORDER_SIZE * 2) + boardBounds.height

        canvas.preferredSize = Dimension(width, height)
    }


    fun render() {
        val g2d = graphics

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2d.color = Color.WHITE
        g2d.fillRect(0, 0, canvas.width, canvas.height)

        renderGame(g2d)

        g2d.dispose()
        bufferStrategy.show()
    }

    private fun renderGame(g2d: Graphics2D) {
        renderBackground(g2d)
        renderBoard(g2d)
        renderFood(g2d, game.board.food)
        renderSnake(g2d, game.board.snake)

        if (game.gameOver) {
            renderGameOver(g2d)
        }
    }

    private fun renderGameOver(g2d: Graphics2D) {
        paintWithColor(g2d, textColor) {
            g2d.font = Font(null, Font.BOLD, 36)
            drawString(g2d, "GAME OVER", xAlign = Align.CENTER, yAlign = Align.CENTER)

            g2d.font = Font(null, Font.BOLD, 18)
            drawString(g2d, "PRESS <ENTER> TO RESTART", yOffset = 50, xAlign = Align.CENTER, yAlign = Align.CENTER)
        }
    }

    private fun renderBackground(g2d: Graphics2D) {
        paintWithColor(g2d, backgroundColor) {
            g2d.fill(canvas.bounds)
        }
    }

    private fun renderBoard(g2d: Graphics2D) {
        paintWithColor(g2d, boardGridColor) {
            for (i in boardBounds.x..boardBounds.maxX.toInt() step PIXELS_PER_BOARD_POINT) {
                g2d.drawLine(i, boardBounds.y, i, boardBounds.maxY.toInt())
            }

            for (i in boardBounds.y..boardBounds.maxY.toInt() step PIXELS_PER_BOARD_POINT) {
                g2d.drawLine(boardBounds.x, i, boardBounds.maxX.toInt(), i)
            }
        }

        paintWithColor(g2d, boardBorderColor) {
            // g2d.stroke = BasicStroke(2F)
            g2d.draw(boardBounds)
        }
    }

    private fun renderSnake(g2d: Graphics2D, snake: Snake) {
        paintWithColor(g2d, snakeColor) {
            for ((x, y) in snake.body) {
                val snakeBodyXInPixels = boardPointToPixelPoint(x)
                val snakeBodyYInPixels = boardPointToPixelPoint(y)

                g2d.fillRect(
                    snakeBodyXInPixels,
                    snakeBodyYInPixels,
                    PIXELS_PER_BOARD_POINT,
                    PIXELS_PER_BOARD_POINT
                )
            }
        }
    }

    private fun renderFood(g2d: Graphics2D, food: Food) {
        paintWithColor(g2d, foodColor) {
            val foodXInPixels = boardPointToPixelPoint(food.x)
            val foodYInPixels = boardPointToPixelPoint(food.y)

            g2d.fillOval(
                foodXInPixels,
                foodYInPixels,
                PIXELS_PER_BOARD_POINT - 1,
                PIXELS_PER_BOARD_POINT - 1
            )
        }
    }

    private fun paintWithColor(g2d: Graphics2D, color: Color, doSomething: () -> Unit) {
        val oldColor = g2d.color
        g2d.color = color
        doSomething()
        g2d.color = oldColor
    }

    private fun boardPointToPixelPoint(p: Int): Int {
        return (p * PIXELS_PER_BOARD_POINT) + BORDER_SIZE
    }

    private fun drawString(
        g: Graphics2D,
        str: String,
        xOffset: Int = 0,
        yOffset: Int = 0,
        xAlign: Align = Align.START,
        yAlign: Align = Align.START
    ) {
        val stringBounds = g.fontMetrics.getStringBounds(str, g)
        val stringWidth = stringBounds.width.toInt()
        val stringHeight = stringBounds.height.toInt()

        val x = when (xAlign) {
            Align.START -> 0
            Align.END -> canvas.width - stringWidth
            Align.CENTER -> (canvas.width / 2) - (stringWidth / 2)
        }

        val y = when (yAlign) {
            Align.START -> stringHeight
            Align.END -> canvas.height
            Align.CENTER -> (canvas.height / 2) - (stringHeight / 2)
        }

        g.drawString(str, x + xOffset, y + yOffset)
    }

}