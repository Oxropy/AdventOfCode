package year2021

import AoCApp

object Day14: AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputGroupedLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: Pair<String, Map<String, String>>): String {
        val insertions = input.second
        val polymer = (1..10).fold(input.first) { acc, _ ->
            acc.windowed(2).joinToString("") { it[0] + insertions[it]!! } + acc.last()
        }

        val occurrence = polymer.groupBy { it }.map { Pair(it.key, it.value.size) }
        val mostCommon = occurrence.maxOf { it.second }
        val leastCommon = occurrence.minOf { it.second }
        return (mostCommon - leastCommon).toString()
    }

    private fun part2(input: Pair<String, Map<String, String>>): String {
        TODO("Not yet implemented")
    }

    private fun processInput(inputLines: List<List<String>>): Pair<String, Map<String, String>> {
        val template = inputLines[0][0]
        val insertions = inputLines[1].associate { line -> line.split(" -> ").let { (pair, insertion) -> Pair(pair, insertion) } }
        return Pair(template, insertions)
    }
}