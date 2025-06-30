package io.github.jotaviobiondo

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SnakeDirectionTest {

    @ParameterizedTest
    @MethodSource("oppositeDirections")
    fun `isOppositeOf and isNotOppositeOf `(direction: Snake.Direction, oppositeDirection: Snake.Direction) {
        val directionsThatIsNotOpposite = Snake.Direction.entries.filter { it != oppositeDirection }

        assertEquals(true, direction.isOppositeOf(oppositeDirection))
        assertEquals(false, direction.isNotOppositeOf(oppositeDirection))

        for (directionNotOpposite in directionsThatIsNotOpposite) {
            assertEquals(false, direction.isOppositeOf(directionNotOpposite))
            assertEquals(true, direction.isNotOppositeOf(directionNotOpposite))
        }
    }

    private fun oppositeDirections(): List<Arguments> {
        return listOf(
            Arguments.of(Snake.Direction.UP, Snake.Direction.DOWN),
            Arguments.of(Snake.Direction.DOWN, Snake.Direction.UP),
            Arguments.of(Snake.Direction.RIGHT, Snake.Direction.LEFT),
            Arguments.of(Snake.Direction.LEFT, Snake.Direction.RIGHT)
        )
    }

}