package year2021

import AoCApp

object Day02 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<DirectionWithValue>): String {
        return input.fold(Point(0, 0)) { acc, value -> acc + value.getDirectionValue() }
            .let { (horizontal, depth) -> horizontal * depth }.toString()
    }

    private fun part2(input: List<DirectionWithValue>): String {
        return input.fold(PositionValues(Point(0, 0), 0)) { acc, value ->
            PositionValues(
                calculatePosition(value, acc),
                calculateAim(value, acc.aim)
            )
        }
            .let { (position, _) -> position.x * position.y }.toString()
    }

    private fun processInput(inputLines: List<String>): List<DirectionWithValue> {
        return inputLines.map { l ->
            l.split(' ')
                .let { (dir, units) -> DirectionWithValue(Direction.valueOf(dir.uppercase()), units.toInt()) }
        }
    }

    private enum class Direction(val vector: Point) {
        UP(Point(0, -1)),
        FORWARD(Point(1, 0)),
        DOWN(Point(0, 1))
    }

    private data class DirectionWithValue(val direction: Direction, val units: Int) {
        fun getDirectionValue(): Point {
            return direction.vector * units
        }
    }

    private data class PositionValues(val position: Point, val aim: Int)

    private fun calculateAim(directionWithValue: DirectionWithValue, aim: Int): Int {
        val (direction, units) = directionWithValue
        return when (direction) {
            Direction.UP -> {
                aim - units
            }
            Direction.DOWN -> {
                aim + units
            }
            else -> {
                aim
            }
        }
    }

    private fun calculatePosition(directionWithValue: DirectionWithValue, positionValues: PositionValues): Point {
        val (direction, units) = directionWithValue
        val (position, aim) = positionValues
        return when (direction) {
            Direction.FORWARD -> {
                position + Point(
                    0,
                    units * aim
                ) + direction.vector * units
            }
            else -> {
                position
            }
        }
    }
}