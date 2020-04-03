package io.github.jotaviobiondo

import io.github.jotaviobiondo.GameCanvas.Align
import java.awt.Color
import java.awt.Font
import java.awt.Rectangle

class GameRenderer(private val game: Game) {

    companion object {
        const val PIXELS_PER_BOARD_POINT = 15
        const val BORDER_SIZE = 50
    }

    val canvas: GameCanvas

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


    init {
        val width = (BORDER_SIZE * 2) + boardBounds.width
        val height = (BORDER_SIZE * 2) + boardBounds.height

        canvas = GameCanvas(width, height)

    }

    fun render() {
        canvas.render { this.renderGame() }
    }

    private fun renderGame() {
        renderBackground()
        renderBoard()
        renderFood()
        renderSnake()

        if (game.gameOver) {
            renderGameOver()
        }
    }

    private fun renderGameOver() {
        canvas.withColor(textColor) {
            withFont(Font(null, Font.BOLD, 36)) {
                drawString("GAME OVER", xAlign = Align.CENTER, yAlign = Align.CENTER)
            }

            withFont(Font(null, Font.BOLD, 18)) {
                drawString("PRESS <ENTER> TO RESTART", yOffset = 50, xAlign = Align.CENTER, yAlign = Align.CENTER)
            }
        }
    }

    private fun renderBackground() {
        canvas.withColor(backgroundColor) {
            drawFilledRect(0, 0, canvas.width, canvas.height)
        }
    }

    private fun renderBoard() {
        canvas.withColor(boardGridColor) {
            for (i in boardBounds.x..boardBounds.maxX.toInt() step PIXELS_PER_BOARD_POINT) {
                drawLine(i, boardBounds.y, i, boardBounds.maxY.toInt())
            }

            for (i in boardBounds.y..boardBounds.maxY.toInt() step PIXELS_PER_BOARD_POINT) {
                drawLine(boardBounds.x, i, boardBounds.maxX.toInt(), i)
            }
        }

        canvas.withColor(boardBorderColor) {
            // g2d.stroke = BasicStroke(2F)
            draw(boardBounds)
        }
    }

    private fun renderSnake() {
        canvas.withColor(snakeColor) {
            val snake = game.board.snake
            for ((x, y) in snake.body) {
                val snakeBodyXInPixels = boardPointToPixelPoint(x)
                val snakeBodyYInPixels = boardPointToPixelPoint(y)

                drawFilledRect(
                    snakeBodyXInPixels,
                    snakeBodyYInPixels,
                    PIXELS_PER_BOARD_POINT,
                    PIXELS_PER_BOARD_POINT
                )
            }
        }
    }

    private fun renderFood() {
        canvas.withColor(foodColor) {
            val food = game.board.food
            val foodXInPixels = boardPointToPixelPoint(food.x)
            val foodYInPixels = boardPointToPixelPoint(food.y)

            drawFilledOval(
                foodXInPixels,
                foodYInPixels,
                PIXELS_PER_BOARD_POINT - 1,
                PIXELS_PER_BOARD_POINT - 1
            )
        }
    }

    private fun boardPointToPixelPoint(p: Int): Int {
        return (p * PIXELS_PER_BOARD_POINT) + BORDER_SIZE
    }

}