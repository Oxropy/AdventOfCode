package year2024

import AoCApp
import kotlin.math.abs

object Day02 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = parseReports(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<List<Int>>): String {
        return input.sumOf { levels ->
            var lastValue = levels[0]
            val asc = levels[0] < levels[1]
            for (i in 1 until levels.size) {
                val diff = lastValue - levels[i]
                if ((diff < 0 && !asc) || (diff > 0 && asc)) {
                    return@sumOf 0
                }
                val abs = abs(diff)
                if (abs == 0 || abs > 3) {
                    return@sumOf 0
                }

                lastValue = levels[i]
            }

            1L
        }.toString()
    }

    private fun part2(input: List<List<Int>>): String {
        TODO("Not yet implemented")
    }

    private fun parseReports(inputLines: List<String>): List<List<Int>> {
        return inputLines.map { line -> line.split(' ').map { it.toInt() } }
    }
}