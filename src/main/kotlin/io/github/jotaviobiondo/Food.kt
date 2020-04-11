package io.github.jotaviobiondo

import kotlin.random.Random

class Food private constructor(x: Int, y: Int) {

    val position = Position(x, y)

    val x get() = position.x
    val y get() = position.y

    companion object {
        fun random(board: Board): Food {
            val randomX = Random.nextInt(board.width + 1)
            val randomY = Random.nextInt(board.height + 1)

            return Food(randomX, randomY)
        }
    }

}