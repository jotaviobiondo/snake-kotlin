package io.github.jotaviobiondo

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import kotlin.test.assertEquals
import kotlin.test.assertNotSame
import kotlin.test.assertTrue

internal class BoardTest {

    @Test
    fun `snake should start at (0,0)`() {
        val board = createBoard()
        assertEquals(Position(x = 0, y = 0), board.snake.head)
    }

    @ParameterizedTest
    @EnumSource(value = Snake.Direction::class)
    fun `changeSnakeDirection`(direction: Snake.Direction) {
        val board = createBoard()
        board.changeSnakeDirection(direction)

        assertEquals(direction, board.nextDirection)
    }

    @Test
    fun `game over when snake dies`() {
        val board = createBoard()
        board.changeSnakeDirection(Snake.Direction.UP)

        board.tick()

        assertEquals(Position(x = 0, y = -1), board.snake.head)
        assertEquals(true, board.snake.dead)
        assertEquals(true, board.gameOver)
    }

    @Test
    fun `snake should move on tick`() {
        val board = createBoard()
        board.changeSnakeDirection(Snake.Direction.RIGHT)

        board.tick()

        assertEquals(Position(x = 1, y = 0), board.snake.head)
    }

    @Test
    fun `spawn new random food when snake eat`() {
        val board = createBoard()
        val lastFood = board.food

        moveSnakeToFood(board)

        assertNotSame(lastFood, board.food)
    }

    @Test
    fun `increment score when snake eat food`() {
        val board = createBoard()
        val scoreBefore = board.score
        val foodPosition = board.food.position

        moveSnakeToFood(board)

        assertEquals(foodPosition, board.snake.head)
        assertTrue(board.score > scoreBefore)
    }

    private fun createBoard(width: Int = 5, height: Int = 5) = Board(width, height)

    private fun moveSnakeToFood(board: Board) {
        val foodPosition = board.food.position

        if (foodPosition == Position(0, 0)) {
            with(board) {
                changeSnakeDirection(Snake.Direction.RIGHT)
                tick()
                changeSnakeDirection(Snake.Direction.DOWN)
                tick()
                changeSnakeDirection(Snake.Direction.LEFT)
                tick()
                changeSnakeDirection(Snake.Direction.UP)
                tick()
            }

            return
        }

        with(board) {
            val (x, y) = foodPosition

            changeSnakeDirection(Snake.Direction.RIGHT)
            repeat(x) { tick() }

            changeSnakeDirection(Snake.Direction.DOWN)
            repeat(y) { tick() }
        }
    }

}