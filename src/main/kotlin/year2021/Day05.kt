package year2021

import AoCApp

object Day05 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputLines)
        printPart(1, part1(input))
//        printPart(2, part2(input))
    }

    private fun part1(input: List<Line>): String {
        return input.flatMap { getLineFillerPointsHorizontalAndVertical(it).second }.groupBy { it }.count { it.value.size > 1 }.toString()
    }

    private fun processInput(inputLines: List<String>): List<Line> {
        return inputLines.map { line ->
            line.split(" -> ").map { point -> point.split(',').map { it.toInt() }.let { Point(it[0], it[1]) } }
        }.map { Line(it[0], it[1]) }
    }

    private data class Line(val start: Point, val end: Point)

    private fun getLineFillerPointsHorizontalAndVertical(line: Line): Pair<Line, List<Point>> {
        val isXEqual = line.start.x == line.end.x
        val isYEqual = line.start.y == line.end.y

        if (!isXEqual && !isYEqual) {
            return Pair(line, listOf())
        }

        if (isXEqual) {
            return Pair(line, if (line.start.y > line.end.y) {
                getFillerPointsY(
                    line.end.y,
                    line.start.y,
                    Point(line.end.x, line.end.y)
                )
            } else {
                getFillerPointsY(
                    line.start.y,
                    line.end.y,
                    Point(line.start.x, line.start.y)
                )
            })
        }

        return Pair(line, if (line.start.x > line.end.x) {
            getFillerPointsX(
                line.end.x,
                line.start.x,
                Point(line.end.x, line.end.y)
            )
        } else {
            getFillerPointsX(
                line.start.x,
                line.end.x,
                Point(line.start.x, line.start.y)
            )
        })
    }

    private fun getFillerPointsX(
        start: Int,
        end: Int,
        startPoint: Point
    ): List<Point> {
        return (start..end).map { startPoint.setX(it) }
    }

    private fun getFillerPointsY(
        start: Int,
        end: Int,
        startPoint: Point
    ): List<Point> {
        return (start..end).map { startPoint.setY(it) }
    }
}