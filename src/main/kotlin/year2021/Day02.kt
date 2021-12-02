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
        return input.fold(Point(0, 0)) { acc, value -> acc.plus(value.getDirectionValue()) }.let { (horizontal, depth) -> horizontal * depth }.toString()
    }

    private fun part2(input: List<DirectionWithValue>): String {
        return calculateWithAim(Point(0, 0), 0, input, 0).let { (horizontal, depth) -> horizontal * depth }.toString()
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
        fun getDirectionValue() : Point {
            return direction.vector.times(units)
        }
    }

    private fun calculateWithAim(position: Point, aim: Int, directions: List<DirectionWithValue>, index: Int) : Point {
        if (index >= directions.size) {
            return position
        }

        val (direction, units) = directions[index]

        var newAim = aim
        var newPosition = position
        when (direction) {
            Direction.UP -> { newAim = aim - units }
            Direction.FORWARD -> { newPosition = position + Point(0, units * aim) + direction.vector * units }
            Direction.DOWN -> { newAim = aim + units }
        }

        return calculateWithAim(newPosition, newAim, directions, index + 1)
    }
}