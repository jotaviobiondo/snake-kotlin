package io.github.jotaviobiondo

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SnakeTest {

    private val board = Board(5, 5)

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
        val foodPositionAwayFromSnake = Position(this.board.width, this.board.height)
        val board = mockFoodPositionOnBoard(foodPositionAwayFromSnake)

        with(Snake(initialSnakeLength = 3)) {
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

            move(board, Snake.Direction.UP)
            head shouldBe Position(1, 0)
            currentDirection shouldBe Snake.Direction.UP

            body shouldBe listOf(Position(1, 0), Position(1, 1), Position(2, 1))
        }
    }

    @Test
    fun `test snake die if collides wall`() {
        with(Snake()) {
            move(board, Snake.Direction.UP)

            dead.shouldBeTrue()
        }
    }

    @Test
    fun `test snake die if collides itself`() {
        with(Snake(initialSnakeLength = 5)) {
            move(board, Snake.Direction.RIGHT)
            move(board, Snake.Direction.DOWN)
            move(board, Snake.Direction.LEFT)
            move(board, Snake.Direction.UP)

            dead.shouldBeTrue()
        }
    }

    @Test
    fun `test snake grow when eat food`() {
        val snake = Snake()

        val initialLength = snake.length

        val foodPosition = Position(1, 1)

        val board = mockFoodPositionOnBoard(foodPosition)

        with(snake) {
            var whenEatFoodExecuted = false

            move(board, Snake.Direction.RIGHT)
            move(
                board,
                Snake.Direction.DOWN,
                whenEatFood = {
                    whenEatFoodExecuted = true
                }
            )

            head shouldBe foodPosition
            length shouldBeExactly initialLength + 1
            whenEatFoodExecuted.shouldBeTrue()
        }
    }

    private fun mockFoodPositionOnBoard(foodPosition: Position): Board {
        return spyk(this.board) {
            every { food } returns mockk {
                every { position } returns foodPosition
            }
        }
    }

}