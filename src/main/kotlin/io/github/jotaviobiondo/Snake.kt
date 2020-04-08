package io.github.jotaviobiondo


class Snake(initialSnakeLength: Int = 1) {

    enum class Direction {
        UP, DOWN, RIGHT, LEFT;

        infix fun isOppositeOf(direction: Direction): Boolean {
            return when (direction) {
                UP -> this == DOWN
                DOWN -> this == UP
                RIGHT -> this == LEFT
                LEFT -> this == RIGHT
            }
        }

        infix fun isNotOppositeOf(direction: Direction): Boolean = !this.isOppositeOf(direction)
    }

    private val _body = mutableListOf(Position(0, 0))

    val body get() = _body.toList()

    val head get() = _body.first()

    val tail get() = _body.last()

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
        if (currentDirection isNotOppositeOf direction) {
            currentDirection = direction
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

    private fun grow() {
        _body.add(Position(tail.x, tail.y))
    }

    fun growIfFoodWasEaten(food: Food, actionWhenEat: () -> Unit = {}) {
        if (head == food.position) {
            grow()
            actionWhenEat()
        }
    }

    fun dieIfCollidesItself() {
        val collided = _body.drop(1).contains(head)

        if (collided) {
            die()
        }
    }

    fun die() {
        dead = true
    }

}