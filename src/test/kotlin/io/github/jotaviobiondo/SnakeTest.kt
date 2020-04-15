package io.github.jotaviobiondo

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldStartWith
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SnakeTest {

    @Test
    fun `test snake's initial length`() {
        val initialSnakeLength = 5
        val snake = Snake(initialSnakeLength)

        snake.length shouldBeExactly initialSnakeLength
    }

    @ParameterizedTest
    @ValueSource(ints = [0, -1])
    fun `test snake's initial length must be greater than 0`(invalidInitialSnakeLength: Int) {
        shouldThrow<IllegalStateException> {
            Snake(invalidInitialSnakeLength)
        }
    }

    @Test
    fun `test snake move`() {
        val board = createBoard()

        with(Snake(initialSnakeLength = 2)) {
            head shouldBe Position(0, 0)

            move(board, Snake.Direction.RIGHT)
            head shouldBe Position(1, 0)
            currentDirection shouldBe Snake.Direction.RIGHT

            move(board, Snake.Direction.RIGHT)
            head shouldBe Position(2, 0)
            currentDirection shouldBe Snake.Direction.RIGHT

            move(board, Snake.Direction.DOWN)
            head shouldBe Position(2, 1)
            currentDirection shouldBe Snake.Direction.DOWN

            move(board, Snake.Direction.LEFT)
            head shouldBe Position(1, 1)
            currentDirection shouldBe Snake.Direction.LEFT

            move(board, Snake.Direction.LEFT)
            head shouldBe Position(0, 1)
            currentDirection shouldBe Snake.Direction.LEFT

            move(board, Snake.Direction.UP)
            head shouldBe Position(0, 0)
            currentDirection shouldBe Snake.Direction.UP

            body shouldStartWith listOf(Position(0, 0), Position(0, 1))
        }
    }

    @Test
    fun `test snake dies if collides wall`() {
        val board = createBoard()

        with(Snake()) {
            move(board, Snake.Direction.UP)

            head shouldBe Position(0, -1)
            dead.shouldBeTrue()
        }
    }

    @Test
    fun `test snake dies if collides itself`() {
        val board = createBoard()

        with(Snake(initialSnakeLength = 5)) {
            move(board, Snake.Direction.RIGHT)
            move(board, Snake.Direction.DOWN)
            move(board, Snake.Direction.LEFT)
            move(board, Snake.Direction.UP)

            head shouldBe tail
            dead.shouldBeTrue()
        }
    }

    @Test
    fun `test snake grow when eat food`() {
        val board = createBoard()

        with(Snake()) {
            val initialLength = length
            val foodPosition = board.food.position
            var whenEatFoodExecuted = false

            moveSnakeToFood(
                snake = this,
                board = board,
                whenEatFood = {
                    whenEatFoodExecuted = true
                }
            )

            println(foodPosition)
            head shouldBe foodPosition
            length shouldBeExactly initialLength + 1
            whenEatFoodExecuted.shouldBeTrue()
        }
    }

    private fun createBoard() = Board(5, 5)

    private fun moveSnakeToFood(snake: Snake, board: Board, whenEatFood: () -> Unit) {
        val food = board.food

        if (food.position == Position(0, 0)) {
            with(snake) {
                move(board, Snake.Direction.RIGHT)
                move(board, Snake.Direction.DOWN)
                move(board, Snake.Direction.LEFT)
                move(board, Snake.Direction.UP, whenEatFood)
            }

            return
        }

        with(snake) {
            repeat(food.x) {
                move(board, Snake.Direction.RIGHT, whenEatFood)
            }

            repeat(food.y) {
                move(board, Snake.Direction.DOWN, whenEatFood)
            }
        }
    }

}