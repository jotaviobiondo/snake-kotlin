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

    val length get() = _body.size

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

    fun move(board: Board, direction: Direction, whenEatFood: () -> Unit) {
        changeDirection(direction)
        doMove()
        dieIfCollidesItself()
        dieIfCollidesBoardWalls(board)
        growIfFoodWasEaten(board.food, whenEatFood)
    }

    private fun changeDirection(direction: Direction) {
        if (currentDirection isNotOppositeOf direction) {
            currentDirection = direction
        }
    }

    private fun doMove() {
        val (xOffset, yOffset) = when (currentDirection) {
            Direction.UP -> Pair(0, -1)
            Direction.DOWN -> Pair(0, 1)
            Direction.RIGHT -> Pair(1, 0)
            Direction.LEFT -> Pair(-1, 0)
        }

        val newX = head.x + xOffset
        val newY = head.y + yOffset

        _body.removeAt(_body.lastIndex)
        _body.add(0, Position(newX, newY))
    }

    private fun dieIfCollidesItself() {
        val collided = _body.drop(1).contains(head)

        if (collided) {
            die()
        }
    }

    private fun dieIfCollidesBoardWalls(board: Board) {
        if (head.isOutsideBoard(board)) {
            die()
        }
    }

    private fun growIfFoodWasEaten(food: Food, actionWhenEat: () -> Unit = {}) {
        if (head == food.position) {
            grow()
            actionWhenEat()
        }
    }

    private fun grow() {
        _body.add(Position(tail.x, tail.y))
    }

    private fun die() {
        dead = true
    }

}