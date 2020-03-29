package io.github.jotaviobiondo

import kotlin.random.Random

class Food(x: Int, y: Int) {

    val position: Position = Position(x, y)

    val x get() = position.x
    val y get() = position.y

    companion object {
        fun random(maxX: Int, maxY: Int): Food {
            val randomX = Random.nextInt(0, maxX)
            val randomY = Random.nextInt(0, maxY)

            return Food(randomX, randomY)
        }
    }

}