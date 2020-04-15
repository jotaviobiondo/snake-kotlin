package io.github.jotaviobiondo

import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class BoardTest {

    @Test
    fun `test snake should start at (0,0)`() {
        with(createBoard()) {
            snake.head shouldBe Position(0, 0)
        }
    }

    @ParameterizedTest
    @EnumSource(value = Snake.Direction::class)
    fun `test changeSnakeDirection`(direction: Snake.Direction) {
        with(createBoard()) {
            changeSnakeDirection(direction)

            nextDirection shouldBe direction
        }
    }

    @Test
    fun `test game over when snake dies`() {
        with(createBoard()) {
            changeSnakeDirection(Snake.Direction.UP)

            tick()

            snake.head shouldBe Position(0, -1)
            snake.dead.shouldBeTrue()
            gameOver.shouldBeTrue()
        }
    }

    @Test
    fun `test snake should move on tick`() {
        with(createBoard()) {
            changeSnakeDirection(Snake.Direction.RIGHT)

            tick()

            snake.head shouldBe Position(1, 0)
        }
    }

    @Test
    fun `test spawn new random food when snake eat`() {
        with(createBoard()) {
            val lastFood = food

            moveSnakeToFood(this)

            food shouldNotBeSameInstanceAs lastFood
        }
    }

    @Test
    fun `test increment score when snake eat food`() {
        with(createBoard()) {
            val scoreBefore = score
            val foodPosition = food.position

            moveSnakeToFood(this)

            snake.head shouldBe foodPosition
            score shouldBeGreaterThan scoreBefore
        }
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