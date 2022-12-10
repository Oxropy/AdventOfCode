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
        return getUniquePositionOfTailEnd(input, 1)
    }

    private fun part2(input: List<DirectionInfo>): String {
        return getUniquePositionOfTailEnd(input, 9)
    }

    private fun getUniquePositionOfTailEnd(input: List<DirectionInfo>, tailLength: Int): String {
        val tailPositions = (0..tailLength).associateWith { mutableListOf(Point(0, 0)) }
        input.forEach { processDirectionInfo(it, tailPositions) }

//        printPositions(tailPositions.getValue(tailLength))

        return tailPositions.getValue(tailLength).distinct().count().toString()
    }

    private fun processDirectionInfo(info: DirectionInfo, positions: Map<Int, MutableList<Point>>) {
//        println("${info.direction.name} ${info.length}")
        for (i in 0 until info.length) {
            var leadingPoint = positions.getValue(0).last()
            var lastDirection = info.direction.vector
            for ((key, value) in positions) {
                val points = when (key) {
                    0 -> processHead(lastDirection, leadingPoint, value)
                    1 -> processFirstTail(leadingPoint, lastDirection, value)
                    else -> processTailKnot(
                        leadingPoint,
                        positions.getValue(key - 1)[positions.getValue(key - 1).size - 2],
                        lastDirection,
                        value
                    )
                }

                leadingPoint = points.first
                lastDirection = points.second

                if (lastDirection.x == 0 && lastDirection.y == 0)
                    break
            }

        }
//        printPositions(positions)
    }

    private fun processHead(direction: Point, oldHeadPoint: Point, positions: MutableList<Point>): Pair<Point, Point> {
        val newHeadPoint = oldHeadPoint + direction
        positions.add(newHeadPoint)
        return Pair(newHeadPoint, oldHeadPoint)
    }

    private fun processFirstTail(
        leadingPoint: Point,
        previousLeadingPoint: Point,
        positions: MutableList<Point>
    ): Pair<Point, Point> {
        val knotPosition = positions.last()
        if (!isAwayOne(leadingPoint, knotPosition)) {
            positions.add(previousLeadingPoint)
            return Pair(previousLeadingPoint, previousLeadingPoint - knotPosition)
        }

        return Pair(knotPosition, Point(0, 0))
    }

    private fun processTailKnot(
        leadingPoint: Point,
        previousLeadingPoint: Point,
        leadingDirection: Point,
        positions: MutableList<Point>
    ): Pair<Point, Point> {
        val knotPosition = positions.last()
        if (!isAwayOne(leadingPoint, knotPosition)) {
            if (isInLine(leadingPoint, knotPosition)) {
                val newDirection = getInLineDirection(leadingPoint, knotPosition)
                val newPosition = knotPosition + newDirection
                positions.add(newPosition)
                return Pair(newPosition, newDirection)
            }

            if (isDiagonal(leadingDirection)) {
                val newPosition = knotPosition + leadingDirection
                positions.add(newPosition)
                return Pair(newPosition, leadingDirection)
            }

            positions.add(previousLeadingPoint)
            return Pair(previousLeadingPoint, previousLeadingPoint - knotPosition)
        }

        return Pair(knotPosition, Point(0, 0))
    }

    private fun isAwayOne(first: Point, second: Point): Boolean {
        val dif = first - second
        return dif.x.absoluteValue <= 1 && dif.y.absoluteValue <= 1
    }

    private fun isInLine(first: Point, second: Point): Boolean {
        val dif = first - second
        return dif.x == 0 || dif.y == 0
    }

    private fun isDiagonal(direction: Point): Boolean {
        return direction.x.absoluteValue == 1 && direction.y.absoluteValue == 1
    }

    private fun getInLineDirection(first: Point, second: Point): Point {
        val dif = first - second
        if (dif.x == 0) {
            if (dif.y > 0) {
                return Point(0, 1)
            }

            return Point(0, -1)
        }

        if (dif.x > 0) {
            return Point(1, 0)
        }

        return Point(-1, 0)
    }

    private fun printPositions(positions: MutableList<Point>) {
        val maxOfX = positions.maxOf { it.x }
        val minOfX = positions.minOf { it.x }
        val maxOfY = positions.maxOf { it.y }
        val minOfY = positions.minOf { it.y }

        for (row in (minOfY..maxOfY).reversed()) {
            for (column in minOfX..maxOfX) {
                if (column == 0 && row == 0) {
                    print("s")
                    continue
                }

                if (positions.any { it.x == column && it.y == row }) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }

        println()
    }

    private fun printPositions(positions: Map<Int, MutableList<Point>>) {
        val maxOfX = positions.values.maxOf { list -> list.maxOf { it.x } }
        val minOfX = positions.values.minOf { list -> list.minOf { it.x } }
        val maxOfY = positions.values.maxOf { list -> list.maxOf { it.y } }
        val minOfY = positions.values.minOf { list -> list.minOf { it.y } }

        for (row in (minOfY..maxOfY).reversed()) {
            for (column in minOfX..maxOfX) {
                if (column == 0 && row == 0) {
                    print("s")
                    continue
                }

                var hasKnot = false
                for (position in positions) {
                    if (position.value.last().let { it.x == column && it.y == row }) {
                        print(position.key)
                        hasKnot = true
                        break
                    }
                }
                if (!hasKnot) {
                    print(".")
                }
            }
            println()
        }

        println()
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