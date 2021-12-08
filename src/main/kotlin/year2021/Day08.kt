package year2021

import AoCApp

object Day08: AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<Entry>): String {
        return input.sumOf { entry -> entry.output.count { it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7  } }.toString()
    }

    private fun part2(input: List<Entry>): String {
        TODO("Not yet implemented")
    }

    private fun processInput(input: List<String>): List<Entry> {
        return input.map { line -> line.split('|').map { it.split(' ') }.let { (patterns, output) -> Entry(patterns, output) } }
    }

    private data class Entry(val patterns: List<String>, val output: List<String>)
}