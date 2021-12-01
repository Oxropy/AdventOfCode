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

        return addWhenCurrentValueGreaterThenOld(lines, 1, lines[0], 0).toString()
    }

    private fun part2(lines: List<Int>): String {
        val summed = lines.windowed(3, 1).map { it.sum() }
        return addWhenCurrentValueGreaterThenOld(summed, 1, summed[0], 0).toString()
    }

    private fun processInput(inputLines: List<String>): List<Int> {
        return inputLines.map { l -> l.toInt() }
    }

    private fun addWhenCurrentValueGreaterThenOld(values: List<Int>, index: Int, oldValue: Int, increases: Int): Int {
        if (index >= values.size) {
            return increases
        }

        val currentValue = values[index]
        return addWhenCurrentValueGreaterThenOld(values, index + 1, currentValue,
            increases + (currentValue > oldValue).toInt())
    }

    private fun Boolean.toInt() = if (this) 1 else 0
}