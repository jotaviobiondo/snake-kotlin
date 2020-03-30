package io.github.jotaviobiondo


class Snake(initialSnakeLength: Int = 1) {

    enum class Direction {
        UP, DOWN, RIGHT, LEFT;

        fun changeIfPossible(direction: Direction): Direction {
            val upDown = listOf(UP, DOWN)
            val leftRight = listOf(LEFT, RIGHT)

            return when (direction) {
                UP, DOWN -> if (this in leftRight) direction else this
                RIGHT, LEFT -> if (this in upDown) direction else this
            }
        }
    }

    private val _body = mutableListOf(Position(0, 0))

    val body get() = _body.toList()

    val head get() = body[0]

    val tail get() = body[length - 1]

    val length get() = body.size

    var currentDirection = Direction.RIGHT
        private set

    var dead = false
        private set

    init {
        check(initialSnakeLength > 0) { "Initial snake length must be greater than 0. Got $initialSnakeLength." }

        repeat(initialSnakeLength - 1) {
            grow()
        }
    }

    fun changeDirection(direction: Direction) {
        currentDirection = currentDirection.changeIfPossible(direction)
    }

    fun move() {
        when (currentDirection) {
            Direction.UP -> doMove(y = -1)
            Direction.DOWN -> doMove(y = 1)
            Direction.RIGHT -> doMove(x = 1)
            Direction.LEFT -> doMove(x = -1)
        }
    }

    fun changeDirectionAndMove(direction: Direction) {
        changeDirection(direction)
        move()
    }

    private fun doMove(x: Int = 0, y: Int = 0) {
        val newX = head.x + x
        val newY = head.y + y

        _body.removeAt(_body.lastIndex)
        _body.add(0, Position(newX, newY))
    }

    fun grow() {
        _body.add(Position(tail.x, tail.y))
    }

    fun growIfFoodWasEaten(food: Food) {

    }

    fun die() {
        dead = true
    }

}