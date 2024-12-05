package year2024

import AoCApp

object Day05 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = inputGroupedLines
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<List<String>>): String {
        val pageOrder = input.first().map { line ->
            line.split('|').let {
                val first = it[0].toInt()
                val second = it[1].toInt()
                Pair(first, second)
            }
        }.toHashSet()

        return input[1].sumOf { line ->
            val pages = line.split(',').map { it.toInt() }
            pages.windowed(2).all { (a, b) -> pageOrder.contains(Pair(a, b)) }.let {
                if (it) {
                    val middle = pages.size / 2
                    pages[middle]
                } else {
                    0
                }
            }
        }.toString()
    }

    private fun part2(input: List<List<String>>): String {
        TODO("Not yet implemented")
    }
}