package year2024

import AoCApp
import kotlin.math.absoluteValue
import kotlin.math.sign

object Day02 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = parseReports(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<List<Int>>): String {
        return input.count { isSafe(it) }.toString()
    }

    private fun part2(input: List<List<Int>>): String {
        return input.count { levels ->
            isSafe(levels) or getVariations(levels).any { isSafe(it) }
        }.toString()
    }

    private fun getVariations(levels: List<Int>): List<List<Int>> {
        return levels.indices.map { levels.filterIndexed { i, _ -> i != it } }
    }

    private fun isSafe(levels: List<Int>) : Boolean {
        val differences = levels.windowed(2).map { (a, b) -> b - a }.toList()
        val sign = differences.first().sign
        return differences.all { it.sign == sign && it.absoluteValue in 1..3 }
    }

    private fun parseReports(inputLines: List<String>): List<List<Int>> {
        return inputLines.map { line -> line.split(' ').map { it.toInt() } }
    }
}