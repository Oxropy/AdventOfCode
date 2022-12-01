package year2022

import AoCApp

object Day01 : AoCApp() {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput()
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(groups: List<List<String>>): String {
        return groups.map { group -> group.sumOf { it.toInt() } }.maxOf { it }.toString()
    }

    private fun part2(groups: List<List<String>>): String {
        return groups.map { group -> group.sumOf { it.toInt() } }.sortedDescending().subList(0, 3).sum().toString()
    }

    private fun processInput(): List<List<String>> {
        return inputGroupedLines
    }
}