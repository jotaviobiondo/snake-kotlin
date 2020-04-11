package io.github.jotaviobiondo

import io.kotest.matchers.booleans.shouldBeFalse
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class FoodTest {

    private val board = Board(5, 5)

    @RepeatedTest(40)
    fun `test generate random food for board`() {
        val food = Food.random(board)

        food.position.isOutsideBoard(board).shouldBeFalse()
    }
}