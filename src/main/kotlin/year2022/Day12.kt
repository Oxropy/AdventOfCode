package year2022

import AoCApp

object Day12 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: Input): String {
        val (heightMap, start, goal) = input
        val steps = listOf(goal)
        return findWay(heightMap, goal, start, steps).toString()
    }

    private fun findWay(heightMap: Map<Point, Int>, current: Point, goal: Point, steps: List<Point>): Int {
        if (current == goal) {
            return steps.size - 1
        }

        val surroundingPoints = getSurroundingPoints(current)
        val getPossiblePoints = getValuesWithOneLowerOrEqualHeight(heightMap, current, steps, surroundingPoints)

        if (getPossiblePoints.isEmpty()) {
            return Int.MAX_VALUE
        }

        return getPossiblePoints.minOf { findWay(heightMap, it, goal, steps + it) }
    }

    private fun getSurroundingPoints(currentPoint: Point): List<Point> {
        val up = currentPoint + Direction.UP.vector
        val right = currentPoint + Direction.RIGHT.vector
        val down = currentPoint + Direction.DOWN.vector
        val left = currentPoint + Direction.LEFT.vector
        return listOf(up, right, down, left)
    }

    private fun getValuesWithOneLowerOrEqualHeight(
        heightMap: Map<Point, Int>,
        point: Point,
        notIn: List<Point>,
        searchIn: List<Point>
    ): List<Point> {
        val height = heightMap.getValue(point)
        return searchIn.filter { !notIn.contains(it) }.map { Pair(it, heightMap[it]) }
            .filter { it.second != null && (it.second!! == height || it.second!! == height - 1) }.map { it.first }
    }

    private fun part2(input: Input): String {
        TODO("Not yet implemented $input")
    }

    private fun processInput(inputLines: List<String>): Input {
        val values = inputLines.withIndex().map { line ->
            line.value.toCharArray().withIndex().map { Pair(Point(it.index, line.index), it.value.code - 'a'.code) }
        }.flatten()

        val start = values.first { it.second == -14 }.first
        val goal = values.first { it.second == -28 }.first

        val heightMap = values.associate {
            when (it.second) {
                -14 -> Pair(it.first, 0)
                -28 -> Pair(it.first, 25)
                else -> it
            }
        }
        return Input(heightMap, start, goal)
    }

    data class Input(val heightMap: Map<Point, Int>, val start: Point, val goal: Point)
}