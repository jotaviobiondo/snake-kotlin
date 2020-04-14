package io.github.jotaviobiondo

class Board(val width: Int, val height: Int) {

    companion object {
        private const val INITIAL_SNAKE_LENGTH = 4
        private const val SCORE_INCREMENT = 100
    }

    val snake = Snake(INITIAL_SNAKE_LENGTH)

    var food: Food = randomFood()
        private set

    var gameOver = false
        private set

    var nextDirection = Snake.Direction.RIGHT
        private set

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

    private fun spawnNewFood() {
        food = randomFood()
    }

    private fun randomFood(): Food {
        return Food.random(this)
    }

    fun changeSnakeDirection(direction: Snake.Direction) {
        nextDirection = direction
    }
}