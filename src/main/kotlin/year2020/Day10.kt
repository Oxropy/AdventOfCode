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
        val sortedLines = listOf(0) + lines.sorted()
        val maxDifference = 3
        return findAllCombinations(
            sortedLines,
            0,
            0,
            maxDifference,
            sortedLines.last() + maxDifference,
            sortedLines.count(),
            mutableMapOf()
        ).toString()
    }

    private fun processInput(lines: List<String>): List<Int> {
        return lines.map { line -> line.toInt() }
    }

    private fun countByDiff(pairList: List<Pair<Int, Int>>, n: Int): Int {
        return pairList.count { it.second - it.first == n }
    }

    private fun findAllCombinations(
        lines: List<Int>,
        index: Int,
        lastValue: Int,
        maxDifference: Int,
        endValue: Int,
        linesCount: Int,
        cache: MutableMap<Int, Long>
    ): Long {
        if (index >= linesCount) {
            if (index == linesCount && endValue - lastValue in 1..maxDifference) {
                return 1
            }

            return 0
        }

        val value = lines[index]
        if (value - lastValue > maxDifference) {
            return 0
        }

        val cachedValue = cache[value]
        if (cachedValue != null) {
            return cachedValue
        }

        var count = 0L
        for (i in 1..maxDifference) {
            val nextIndex = index + i
            count += findAllCombinations(lines, nextIndex, value, maxDifference, endValue, linesCount, cache)
        }

        cache[value] = count
        println("$value: $count")

        return count
    }
}