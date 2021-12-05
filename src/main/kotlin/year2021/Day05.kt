package year2021

import AoCApp

object Day05 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<Line>): String {
        return input.flatMap { getLineFillerPoints(it).second }.groupBy { it }.count { it.value.size > 1 }.toString()
    }

    private fun part2(input: List<Line>): String {
        return input.flatMap { getLineFillerPoints(it, false).second }.groupBy { it }.count { it.value.size > 1 }
            .toString()
    }

    private fun processInput(inputLines: List<String>): List<Line> {
        return inputLines.map { line ->
            line.split(" -> ").map { point -> point.split(',').map { it.toInt() }.let { Point(it[0], it[1]) } }
        }.map { Line(it[0], it[1]) }
    }

    private data class Line(val start: Point, val end: Point)

    private fun getLineFillerPoints(line: Line, ignoreDiagonal: Boolean = true): Pair<Line, List<Point>> {
        val isXEqual = line.start.x == line.end.x
        val isYEqual = line.start.y == line.end.y

        if (!isXEqual && !isYEqual) {
            if (ignoreDiagonal) {
                return Pair(line, listOf())
            }

            return getFillerPointDiagonal(line)
        }

        if (isXEqual) {
            return Pair(
                line,
                getFillerPointsY(
                    line.start.y,
                    line.end.y,
                    line.start
                )
            )
        }

        return Pair(
            line,
            getFillerPointsX(
                line.start.x,
                line.end.x,
                line.start
            )
        )
    }

    private fun getFillerPointsX(
        start: Int,
        end: Int,
        startPoint: Point
    ): List<Point> {
        return (start toward end).map { startPoint.setX(it) }
    }

    private fun getFillerPointsY(
        start: Int,
        end: Int,
        startPoint: Point
    ): List<Point> {
        return (start toward end).map { startPoint.setY(it) }
    }

    private fun getFillerPointDiagonal(line: Line): Pair<Line, List<Point>> {
        val xOperand = getOperand(line.start.x, line.end.x)
        val yOperand = getOperand(line.start.y, line.end.y)
        val operandPoint = Point(xOperand, yOperand)

        val points = mutableListOf<Point>()
        points.add(line.start)
        var lastPoint = line.start

        while (lastPoint != line.end) {

            lastPoint += operandPoint
            points.add(lastPoint)
        }

        return Pair(line, points)
    }

    private fun getOperand(start: Int, end: Int): Int {
        return if (start > end) {
            -1
        } else {
            1
        }
    }

    private infix fun Int.toward(to: Int): IntProgression {
        val step = if (this > to) -1 else 1
        return IntProgression.fromClosedRange(this, to, step)
    }
}