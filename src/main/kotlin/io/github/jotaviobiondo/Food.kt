package io.github.jotaviobiondo

import kotlin.random.Random

class Food(val x: Int, val y: Int) {

    companion object {
        fun random(maxX: Int, maxY: Int): Food {
            val randomX = Random.nextInt(0, maxX)
            val randomY = Random.nextInt(0, maxY)

            return Food(randomX, randomY)
        }
    }

}