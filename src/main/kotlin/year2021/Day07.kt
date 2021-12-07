package year2021

import AoCApp

object Day07: AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(input)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<Int>): String {
        val minValue = input.minOf { it }
        val average = input.sum() / input.size

        return (minValue .. average).minOf { calculateMovementCost(input, it) }.toString()
    }

    private fun part2(input: List<Int>): String {
        TODO("Not yet implemented")
    }

    private fun processInput(lines: String): List<Int> {
        return lines.split(',').map { it.toInt() }
    }

    private fun calculateMovementCost(values: List<Int>, to: Int): Int {
        return values.sumOf { maxOf(it, to) - minOf(it, to) }
    }
}