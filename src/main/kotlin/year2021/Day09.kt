package year2021

import AoCApp

object Day09 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<List<Int>>): String {
        return input.mapIndexed { y, line ->
            line.mapIndexed { x, value ->
                val upValue = getValue(Point(x, y), Point(0, -1), input)
                val leftValue = getValue(Point(x, y), Point(-1, 0), input)
                val rightValue = getValue(Point(x, y), Point(1, 0), input)
                val downValue = getValue(Point(x, y), Point(0, 1), input)

                if (isValueLower(value, upValue)
                    && isValueLower(value, leftValue)
                    && isValueLower(value, rightValue)
                    && isValueLower(value, downValue)
                ) {
                    value + 1
                } else {
                    0
                }
            }.sum()
        }.sum().toString()
    }

    private fun part2(input: List<List<Int>>): String {
        TODO("Not yet implemented")
    }

    private fun processInput(inputLines: List<String>): List<List<Int>> {
        return inputLines.map { line -> line.map { Character.getNumericValue(it) } }
    }

    private fun isValueLower(value: Int, other: Int?) = other == null || value < other

    private fun getValue(current: Point, direction: Point, values: List<List<Int>>): Int? {
        val newPoint = current + direction
        if (newPoint.x < 0 || newPoint.y < 0 || newPoint.x >= values[current.y].size || newPoint.y >= values.size) {
            return null
        }

        return values[newPoint.y][newPoint.x]
    }
}