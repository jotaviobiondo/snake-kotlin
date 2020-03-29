package io.github.jotaviobiondo

import kotlin.random.Random

class Board(val width: Int, val height: Int) {

    companion object {
        const val INITIAL_SNAKE_LENGTH = 4
    }

    val snake = Snake(INITIAL_SNAKE_LENGTH)

    var food: Food

    var gameOver = false

    var nextDirection: Snake.Direction = Snake.Direction.RIGHT

    init {
        food = randomFood()
    }

    private fun randomFood(): Food {
        return Food.random(width, height)
    }

    fun spawnNewFood() {
        food = randomFood()
    }

    /**
     * Do one move on the board
     */
    fun tick() {
        if (gameOver) {
            return
        }

        snake.changeDirection(nextDirection)

        snake.move()

        checkWallsCollision()

        if (snake.dead) {
            gameOver = true
        }
    }

    fun turnSnakeUp() {
//        snake.turnUp()
        nextDirection = Snake.Direction.UP
    }

    fun turnSnakeDown() {
//        snake.turnDown()
        nextDirection = Snake.Direction.DOWN
    }

    fun turnSnakeLeft() {
//        snake.turnLeft()
        nextDirection = Snake.Direction.LEFT
    }

    fun turnSnakeRight() {
//        snake.turnRight()

        nextDirection = Snake.Direction.RIGHT
    }

    private fun checkWallsCollision() {
        val headX = snake.head.x
        val headY = snake.head.y

        if (headX < 0 || headX > this.width || headY < 0 || headY > this.height) {
            snake.die()
        }
    }
}