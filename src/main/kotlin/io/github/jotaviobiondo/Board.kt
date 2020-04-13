package io.github.jotaviobiondo

class Board(val width: Int, val height: Int) {

    companion object {
        const val INITIAL_SNAKE_LENGTH = 4
        const val SCORE_INCREMENT = 100
    }

    val snake = Snake(INITIAL_SNAKE_LENGTH)

    var food: Food = randomFood()
        private set

    var gameOver = false
        private set

    private var nextDirection = Snake.Direction.RIGHT

    var score = 0
        private set


    /**
     * Do one move on the board
     */
    fun tick() {
        if (gameOver) {
            return
        }

        snake.move(
            board = this,
            direction = nextDirection,
            whenEatFood = this::whenSnakeEatFood
        )

        if (snake.dead) {
            gameOver = true
        }
    }

    private fun whenSnakeEatFood() {
        spawnNewFood()
        incScore()
    }

    private fun incScore() {
        score += SCORE_INCREMENT
    }

    private fun randomFood(): Food {
        return Food.random(this)
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