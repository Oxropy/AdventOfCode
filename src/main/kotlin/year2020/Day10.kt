package year2020

import AoCApp

object Day10 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(lines: List<Int>): String {
        val sorted = listOf(0) + lines.sorted()
        val zip = sorted.zip(sorted.drop(1))
        return (countByDiff(zip, 1) * (countByDiff(zip, 3) + 1)).toString()
    }

    private fun part2(lines: List<Int>): String {
        TODO("Not yet implemented")
    }

    private fun processInput(lines: List<String>): List<Int> {
        return lines.map { line -> line.toInt() }
    }

    private fun countByDiff(pairList: List<Pair<Int, Int>>, n: Int): Int {
        return pairList.count { it.second - it.first == n }
    }
}