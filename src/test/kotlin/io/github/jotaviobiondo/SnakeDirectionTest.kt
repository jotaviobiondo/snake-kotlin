package io.github.jotaviobiondo

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SnakeDirectionTest {

    @ParameterizedTest
    @MethodSource("oppositeDirections")
    fun `test isOppositeOf and isNotOppositeOf`(direction: Snake.Direction, oppositeDirection: Snake.Direction) {
        val directionsThatIsNotOpposite = Snake.Direction.values().filter { it != oppositeDirection }

        direction.isOppositeOf(oppositeDirection).shouldBeTrue()
        direction.isNotOppositeOf(oppositeDirection).shouldBeFalse()

        directionsThatIsNotOpposite.forEach { directionNotOpposite ->
            direction.isOppositeOf(directionNotOpposite).shouldBeFalse()
            direction.isNotOppositeOf(directionNotOpposite).shouldBeTrue()
        }
    }

    fun oppositeDirections(): List<Arguments> {
        return listOf(
            Arguments.of(Snake.Direction.UP, Snake.Direction.DOWN),
            Arguments.of(Snake.Direction.DOWN, Snake.Direction.UP),
            Arguments.of(Snake.Direction.RIGHT, Snake.Direction.LEFT),
            Arguments.of(Snake.Direction.LEFT, Snake.Direction.RIGHT)
        )
    }

}