package year2021

import AoCApp

object Day01 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(lines: List<Int>): String {
        return addWhenCurrentValueGreaterThenOld(lines).toString()
    }

    private fun part2(lines: List<Int>): String {
        return addWhenCurrentValueGreaterThenOld(lines.windowed(3, 1).map { it.sum() }).toString()
    }

    private fun addWhenCurrentValueGreaterThenOld(lines: List<Int>) =
        lines.windowed(2).count { it[1] > it[0] }

    private fun processInput(inputLines: List<String>): List<Int> {
        return inputLines.map { l -> l.toInt() }
    }
}