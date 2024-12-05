package year2024

import AoCApp

object Day05 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = parseInput(inputGroupedLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: Pair<Set<Pair<Int, Int>>, List<List<Int>>>): String {
        return input.second.sumOf { line ->
            line.windowed(2).all { (a, b) -> input.first.contains(Pair(a, b)) }.let {
                if (it) {
                    val middle = line.size / 2
                    line[middle]
                } else {
                    0
                }
            }
        }.toString()
    }

    private fun part2(input: Pair<Set<Pair<Int, Int>>, List<List<Int>>>): String {
        return input.second.sumOf { line ->
            line.windowed(2).any { (a, b) -> input.first.contains(Pair(b, a)) }.let {
                if (it) {
                    val middle = line.size / 2
                    val reorderedPages = reorderPage(input.first, line)
                    reorderedPages[middle]
                } else {
                    0
                }
            }
        }.toString()
    }

    private fun reorderPage(order: Set<Pair<Int, Int>>, pages: List<Int>): List<Int> {
        val mutableMapOf = mutableMapOf<Int, Int>()
        for (i in pages.indices) {
            val a = pages[i]
            mutableMapOf[a] = 1

            for (j in pages.indices) {
                if (i == j) {
                    continue
                }

                val b = pages[j]
                val abPair = Pair(a, b)

                if (order.contains(abPair) && mutableMapOf.containsKey(a)) {
                    mutableMapOf[a] = mutableMapOf[a]!!.plus(1)
                }
            }
        }

        return mutableMapOf.toList().sortedBy { (_, v) -> v }.toMap().keys.toList()
    }

    private fun parseInput(input: List<List<String>>): Pair<Set<Pair<Int, Int>>, List<List<Int>>> {
        val pageOrder = input.first().map { line ->
            line.split('|').let {
                val first = it[0].toInt()
                val second = it[1].toInt()
                Pair(first, second)
            }
        }.toHashSet()

        val pages = input[1].map { line -> line.split(',').map { it.toInt() } }
        return Pair(pageOrder, pages)
    }
}