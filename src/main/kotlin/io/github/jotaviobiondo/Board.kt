package io.github.jotaviobiondo

class Board(val width: Int, val height: Int) {

    companion object {
        const val INITIAL_SNAKE_LENGTH = 4
    }

    val snake = Snake(INITIAL_SNAKE_LENGTH)

    var food: Food = randomFood()

    var gameOver = false

    var nextDirection = Snake.Direction.RIGHT

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

        snake.changeDirectionAndMove(nextDirection)

        checkWallsCollision()

        if (snake.dead) {
            gameOver = true
        }
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

    private fun checkWallsCollision() {
        val headX = snake.head.x
        val headY = snake.head.y

        if (headX < 0 || headX > this.width || headY < 0 || headY > this.height) {
            snake.die()
        }
    }
}