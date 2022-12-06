package year2022

import AoCApp

object Day06 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: String): String {
        return getMarkerPosition(input, 4).toString()
    }

    private fun part2(input: String): String {
        return getMarkerPosition(input, 14).toString()
    }

    private fun getMarkerPosition(input: String, markerLength: Int): Int {
        return input.withIndex().windowed(markerLength, 1)
            .first { window -> window.groupingBy { it.value }.eachCount().all { it.value == 1 } }.last().index + 1
    }
}