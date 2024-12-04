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
        TODO("Not yet implemented")
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
}