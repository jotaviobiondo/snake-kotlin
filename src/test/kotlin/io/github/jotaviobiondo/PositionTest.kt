package io.github.jotaviobiondo

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.random.Random

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PositionTest {

    private val board = Board(20, 20)

    @ParameterizedTest
    @MethodSource("positionsInsideBoard")
    fun `test isOutsideBoard for valid positions`(validPosition: Position) {
        validPosition.isOutsideBoard(board).shouldBeFalse()
    }

    fun positionsInsideBoard(): List<Position> {
        return listOf(
            Position(0, 0),
            Position(board.width, board.height),
            Position(Random.nextInt(board.width), Random.nextInt(board.height))
        )
    }

    @ParameterizedTest
    @MethodSource("positionsOutsideBoard")
    fun `test isOutsideBoard for invalid positions`(invalidPosition: Position) {
        invalidPosition.isOutsideBoard(board).shouldBeTrue()
    }

    fun positionsOutsideBoard(): List<Position> {
        return listOf(
            Position(-1, 0),
            Position(0, -1),
            Position(board.width + 1, board.height),
            Position(board.width, board.height + 1)
        )
    }

}