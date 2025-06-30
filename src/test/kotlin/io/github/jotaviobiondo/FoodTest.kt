package io.github.jotaviobiondo

import org.junit.jupiter.api.RepeatedTest
import kotlin.test.assertEquals

internal class FoodTest {

    private val board = Board(5, 5)

    @RepeatedTest(40)
    fun `generate random food must always be inside board`() {
        val food = Food.random(board)

        assertEquals(false, food.position.isOutsideBoard(board))
    }
}