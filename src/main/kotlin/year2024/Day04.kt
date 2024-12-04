package year2024

import AoCApp

object Day04: AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = inputLines
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<String>): String {
        var count = 0
        for (y in input.indices) {
            for (x in input[0].indices) {
                val field = input[y][x]
                if (field != 'X') {
                    continue
                }

                count += xmasCount(input, Point(x, y))
            }
        }

        return count.toString()
    }

    private fun part2(input: List<String>): String {
        var count = 0
        for (y in input.indices) {
            for (x in input[0].indices) {
                val field = input[y][x]
                if (field != 'A') {
                    continue
                }

                if (isMasCross(input, Point(x, y))) {
                    count++
                }
            }
        }

        return count.toString()
    }

    private fun xmasCount(input: List<String>, point: Point): Int {
        return Direction.entries.toTypedArray().count { isXmas(input, it, point, 1) }
    }

    private fun isXmas(input: List<String>, direction: Direction, point: Point, index: Int): Boolean {
        val xmas = "XMAS"
        if (index >= xmas.length ) {
            return true
        }

        val newPoint = point + direction.vector
        if (newPoint.x < 0 || newPoint.y < 0 || newPoint.x >= input[0].length || newPoint.y >= input.size) {
            return false
        }

        if (xmas[index] != input[newPoint.y][newPoint.x]) {
            return false
        }

        return isXmas(input, direction, newPoint, index + 1)
    }

    private fun isMasCross(input: List<String>, point: Point): Boolean {
        return isDiagonal(input, point, Direction.UPLEFT, Direction.DOWNRIGHT) &&isDiagonal(input, point, Direction.UPRIGHT, Direction.DOWNLEFT)
    }

    private fun isDiagonal(input: List<String>, point: Point, firstDirection: Direction, secondDirection: Direction): Boolean {
        val first = point + firstDirection.vector
        if (isOutOfRange(input, first)) {
            return false
        }

        val second = point + secondDirection.vector
        if (isOutOfRange(input, second)) {
            return false
        }

        val firstValue = input[first.y][first.x]
        val secondValue = input[second.y][second.x]
        if (!(firstValue == 'M' && secondValue == 'S') && !(firstValue == 'S' && secondValue == 'M')) {
            return false
        }

        return true
    }

    private fun isOutOfRange(input: List<String>, point: Point): Boolean {
        return point.x < 0 || point.y < 0 || point.x >= input[0].length || point.y >= input.size
    }
}