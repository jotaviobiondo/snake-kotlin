package io.github.jotaviobiondo

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals

internal class SnakeTest {

    @Test
    fun `snake's initial length`() {
        val snakeWithDefaultLength = Snake()
        val snakeWithLength5 = Snake(initialSnakeLength = 5)

        assertEquals(1, snakeWithDefaultLength.length)
        assertEquals(5, snakeWithLength5.length)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, -1])
    fun `snake's initial length must be greater than 0`(invalidInitialSnakeLength: Int) {
        assertThrows<IllegalStateException> {
            Snake(initialSnakeLength = invalidInitialSnakeLength)
        }
    }

    @Test
    fun `snake 'move' behavior`() {
        val board = createBoard()
        val snake = Snake(initialSnakeLength = 2)

        assertEquals(Position(x = 0, y = 0), snake.head)

        snake.move(board, Snake.Direction.RIGHT)
        assertEquals(Position(x = 1, y = 0), snake.head)
        assertEquals(Snake.Direction.RIGHT, snake.currentDirection)

        snake.move(board, Snake.Direction.RIGHT)
        assertEquals(Position(x = 2, y = 0), snake.head)
        assertEquals(Snake.Direction.RIGHT, snake.currentDirection)

        snake.move(board, Snake.Direction.DOWN)
        assertEquals(Position(x = 2, y = 1), snake.head)
        assertEquals(Snake.Direction.DOWN, snake.currentDirection)

        snake.move(board, Snake.Direction.LEFT)
        assertEquals(Position(x = 1, y = 1), snake.head)
        assertEquals(Snake.Direction.LEFT, snake.currentDirection)

        snake.move(board, Snake.Direction.LEFT)
        assertEquals(Position(x = 0, y = 1), snake.head)
        assertEquals(Snake.Direction.LEFT, snake.currentDirection)

        snake.move(board, Snake.Direction.UP)
        assertEquals(Position(x = 0, y = 0), snake.head)
        assertEquals(Snake.Direction.UP, snake.currentDirection)

        val expectedHeads = listOf(Position(x = 0, y = 0), Position(x = 0, y = 1))
        assertEquals(expectedHeads, snake.body.subList(0, 2))
    }

    @Test
    fun `snake dies if collides with wall`() {
        val board = createBoard()
        val snake = Snake()

        snake.move(board, Snake.Direction.UP)

        assertEquals(Position(x = 0, y = -1), snake.head)
        assertEquals(true, snake.dead)
    }

    @Test
    fun `snake dies if collides itself`() {
        val board = createBoard()
        val snake = Snake(initialSnakeLength = 5)

        snake.move(board, Snake.Direction.RIGHT)
        snake.move(board, Snake.Direction.DOWN)
        snake.move(board, Snake.Direction.LEFT)
        snake.move(board, Snake.Direction.UP)

        assertEquals(snake.tail, snake.head)
        assertEquals(true, snake.dead)
    }

    @Test
    fun `test snake grow when eat food`() {
        val board = createBoard()
        val snake = Snake()

        val initialLength = snake.length
        val foodPosition = board.food.position
        var whenEatFoodExecuted = false

        moveSnakeToFood(
            snake = snake,
            board = board,
            whenEatFood = {
                whenEatFoodExecuted = true
            }
        )

        assertEquals(foodPosition, snake.head)
        assertEquals(initialLength + 1, snake.length)
        assertEquals(true, whenEatFoodExecuted)
    }

    //
    // Helpers
    //

    private fun createBoard() = Board(5, 5)

    private fun moveSnakeToFood(snake: Snake, board: Board, whenEatFood: () -> Unit) {
        val food = board.food

        if (food.position == Position(x = 0, y = 0)) {
            snake.move(board, Snake.Direction.RIGHT)
            snake.move(board, Snake.Direction.DOWN)
            snake.move(board, Snake.Direction.LEFT)
            snake.move(board, Snake.Direction.UP, whenEatFood)

            return
        }

        repeat(food.x) {
            snake.move(board, Snake.Direction.RIGHT, whenEatFood)
        }

        repeat(food.y) {
            snake.move(board, Snake.Direction.DOWN, whenEatFood)
        }
    }

}