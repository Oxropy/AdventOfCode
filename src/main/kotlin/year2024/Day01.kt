package year2024

import AoCApp
import kotlin.math.abs

object Day01: AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = parseGames(inputLines)
        printPart(1, part1(input.first, input.second))
        printPart(2, part2(input.first, input.second))
    }

    private fun part1(list1: List<Int>, list2: List<Int>): String {
        val sorted1 = list1.sorted()
        val sorted2 = list2.sorted()
        return sorted1.zip(sorted2).sumOf { (a, b) -> abs(a - b) }.toString()
    }

    private fun part2(list1: List<Int>, list2: List<Int>): String {
        return list1.sumOf { first -> list2.count { second -> first == second } * first }.toString()
    }

    private fun parseGames(input: List<String>): Pair<List<Int>, List<Int>> {
        val pairs = input.map { row -> row.split("   ").let { Pair(it[0].trim().toInt(), it[1].trim().toInt()) } }
        val first = pairs.map { it.first }
        val second = pairs.map { it.second }
        return Pair(first, second)
    }
}