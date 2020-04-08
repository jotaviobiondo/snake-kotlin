package io.github.jotaviobiondo

class Board(val width: Int, val height: Int) {

    companion object {
        const val INITIAL_SNAKE_LENGTH = 4
    }

    val snake = Snake(INITIAL_SNAKE_LENGTH)

    var food: Food = randomFood()
        private set

    var gameOver = false
        private set

    private var nextDirection = Snake.Direction.RIGHT


    /**
     * Do one move on the board
     */
    fun tick() {
        if (gameOver) {
            return
        }

        snake.changeDirectionAndMove(nextDirection)

        checkAteFood()

        checkSnakeBodyCollision()

        checkWallsCollision()

        if (snake.dead) {
            gameOver = true
        }
    }

    private fun checkAteFood() {
        snake.growIfFoodWasEaten(food) {
            spawnNewFood()
        }
    }

    private fun checkSnakeBodyCollision() {
        snake.dieIfCollidesItself()
    }

    private fun checkWallsCollision() {
        if (snake.head.isOutsideBoard(this)) {
            snake.die()
        }
    }

    private fun randomFood(): Food {
        return Food.random(width, height)
    }

    private fun spawnNewFood() {
        food = randomFood()
    }

    fun turnSnakeUp() {
        nextDirection = Snake.Direction.UP
    }

    fun turnSnakeDown() {
        nextDirection = Snake.Direction.DOWN
    }

    fun turnSnakeLeft() {
        nextDirection = Snake.Direction.LEFT
    }

    fun turnSnakeRight() {
        nextDirection = Snake.Direction.RIGHT
    }
}