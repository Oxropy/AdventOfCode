package year2022

import AoCApp
import kotlin.math.absoluteValue

object Day09 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<DirectionInfo>): String {
        var headPoint = Point(0, 0)
        val tailPositions = mutableListOf(Point(0, 0))
        for (info in input) {
            headPoint = processDirectionInfo(info, headPoint, tailPositions)
        }

        return tailPositions.distinct().count().toString()
    }

    private fun processDirectionInfo(
        info: DirectionInfo,
        headPosition: Point,
        tailPositions: MutableList<Point>
    ): Point {
        var newHeadPoint = headPosition
        for (i in 0 until info.length) {
            val oldHeadPosition = newHeadPoint
            newHeadPoint += info.direction.vector
            val tailPosition = tailPositions.last()
            if (!isAwayOne(newHeadPoint, tailPosition)) {
                tailPositions.add(oldHeadPosition)
            }
        }

        return newHeadPoint
    }

    private fun isAwayOne(first: Point, second: Point): Boolean {
        val dif = first - second
        return dif.x.absoluteValue <= 1 && dif.y.absoluteValue <= 1
    }

    private fun part2(input: List<DirectionInfo>): String {
        TODO("Not yet implemented")
    }

    private fun processInput(input: List<String>): List<DirectionInfo> {
        return input.map { line ->
            line.split(" ").let { (direction, length) -> DirectionInfo(getDirection(direction), length.toInt()) }
        }
    }

    enum class Direction(val vector: Point) {
        UP(Point(0, 1)),
        RIGHT(Point(1, 0)),
        DOWN(Point(0, -1)),
        LEFT(Point(-1, 0))
    }

    private fun getDirection(value: String): Direction {
        return when (value) {
            "U" -> Direction.UP
            "R" -> Direction.RIGHT
            "D" -> Direction.DOWN
            "L" -> Direction.LEFT
            else -> unreachable()
        }
    }

    data class DirectionInfo(val direction: Direction, val length: Int)
}