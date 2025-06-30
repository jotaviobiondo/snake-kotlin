package io.github.jotaviobiondo

import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.test.assertEquals

internal class PositionTest {

    private val board = Board(20, 20)

    @Test
    fun `'isOutsideBoard' should return expected results`() {
        // Valid positions
        assertEquals(false, Position(0, 0).isOutsideBoard(board))
        assertEquals(false, Position(board.width, board.height).isOutsideBoard(board))
        assertEquals(
            false,
            Position(Random.nextInt(board.width), Random.nextInt(board.height)).isOutsideBoard(board)
        )

        // Invalid positions
        assertEquals(false, Position(0, 0).isOutsideBoard(board))
        assertEquals(true, Position(-1, 0).isOutsideBoard(board))
        assertEquals(true, Position(0, -1).isOutsideBoard(board))
        assertEquals(true, Position(board.width + 1, board.height).isOutsideBoard(board))
        assertEquals(true, Position(board.width, board.height + 1).isOutsideBoard(board))
    }

}