package io.github.jotaviobiondo

import io.github.jotaviobiondo.GameCanvas.Align
import java.awt.Color
import java.awt.Font
import java.awt.Point
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


    private val boardBounds: Rectangle by lazy {
        val boardWidthInPixels = (game.board.width * PIXELS_PER_BOARD_POINT) + PIXELS_PER_BOARD_POINT
        val boardHeightInPixels = (game.board.height * PIXELS_PER_BOARD_POINT) + PIXELS_PER_BOARD_POINT

        Rectangle(BORDER_SIZE, BORDER_SIZE, boardWidthInPixels, boardHeightInPixels)
    }


    init {
        val width = (BORDER_SIZE * 2) + boardBounds.width
        val height = (BORDER_SIZE * 2) + boardBounds.height

        canvas = GameCanvas(width, height)
    }

    fun render() {
        canvas.render { renderGame() }
    }

    private fun renderGame() {
        renderBackground()
        renderBoard()
        game.board.food.render()
        game.board.snake.render()

        if (game.gameOver) {
            renderGameOver()
        }
    }

    private fun renderGameOver() {
        canvas.draw(textColor) {
            string(
                "GAME OVER",
                xAlign = Align.CENTER,
                yAlign = Align.CENTER,
                fontStyle = Font.BOLD,
                fontSize = 36
            )

            string(
                "PRESS <ENTER> TO RESTART",
                yOffset = 50,
                xAlign = Align.CENTER,
                yAlign = Align.CENTER,
                fontStyle = Font.BOLD,
                fontSize = 18
            )
        }
    }

    private fun renderBackground() {
        canvas.draw(backgroundColor) {
            rect(0, 0, canvas.width, canvas.height, filled = true)
        }
    }

    private fun renderBoard() {
        canvas.draw(boardGridColor) {
            for (x in boardBounds.x until boardBounds.maxX.toInt() step PIXELS_PER_BOARD_POINT) {
                line(x, boardBounds.y, x, boardBounds.maxY.toInt())
            }

            for (y in boardBounds.y until boardBounds.maxY.toInt() step PIXELS_PER_BOARD_POINT) {
                line(boardBounds.x, y, boardBounds.maxX.toInt(), y)
            }
        }

        canvas.draw(boardBorderColor) {
            shape(boardBounds)
        }
    }

    private fun Snake.render() {
        canvas.draw(snakeColor) {
            body
                .map { it.toPixelPoint() }
                .forEach { square(it, PIXELS_PER_BOARD_POINT, filled = true) }
        }
    }

    private fun Food.render() {
        canvas.draw(foodColor) {
            circle(position.toPixelPoint(), PIXELS_PER_BOARD_POINT, filled = true)
        }
    }

    private fun Position.toPixelPoint(): Point {
        return Point(boardPointToPixelPoint(x), boardPointToPixelPoint(y))
    }

    private fun boardPointToPixelPoint(p: Int): Int {
        return (p * PIXELS_PER_BOARD_POINT) + BORDER_SIZE
    }

}