package io.github.jotaviobiondo


class Snake(initialSnakeLength: Int = 1) {

    enum class Direction {
        UP, DOWN, RIGHT, LEFT
    }

    // TODO: make the getter immutable
    val body: MutableList<Position> = mutableListOf(Position(0, 0))

    var currentDirection: Direction = Direction.RIGHT
        private set

    val head: Position get() = body[0]

    private val tail: Position
        get() {
            val index = if (length == 0) 0 else (length - 1)
            return body[index]
        }

    val length get() = body.size

    var dead = false
        private set

    init {
        check(initialSnakeLength > 0) { "Initial snake length must be greater than 0. Got $initialSnakeLength." }

        repeat(initialSnakeLength - 1) {
            grow()
        }
    }

    fun turnUp() {
        if (currentDirection == Direction.LEFT || currentDirection == Direction.RIGHT) {
            currentDirection = Direction.UP
        }
    }

    fun turnDown() {
        if (currentDirection == Direction.LEFT || currentDirection == Direction.RIGHT) {
            currentDirection = Direction.DOWN
        }
    }

    fun turnRight() {
        if (currentDirection == Direction.UP || currentDirection == Direction.DOWN) {
            currentDirection = Direction.RIGHT
        }
    }

    fun turnLeft() {
        if (currentDirection == Direction.UP || currentDirection == Direction.DOWN) {
            currentDirection = Direction.LEFT
        }
    }

    fun changeDirection(direction: Direction) {
        when (direction) {
            Direction.UP -> turnUp()
            Direction.DOWN -> turnDown()
            Direction.RIGHT -> turnRight()
            Direction.LEFT -> turnLeft()
        }
    }

    fun move() {
        when (currentDirection) {
            Direction.UP -> doMove(y = -1)
            Direction.DOWN -> doMove(y = 1)
            Direction.RIGHT -> doMove(x = 1)
            Direction.LEFT -> doMove(x = -1)
        }
    }

    private fun doMove(x: Int = 0, y: Int = 0) {
        val newX = head.x + x
        val newY = head.y + y

        body.removeAt(body.lastIndex)
        body.add(0, Position(newX, newY))
    }

    fun grow() {
        body.add(Position(tail.x, tail.y))
    }

    fun growIfFoodWasEaten(food: Food) {

    }

    fun die() {
        dead = true
    }

}