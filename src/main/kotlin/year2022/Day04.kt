package year2022

import AoCApp

object Day04 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<Pair<List<Int>,List<Int>>>): String {
        return input.count { isListFullyIntersect(it.first, it.second) }.toString()
    }

    private fun part2(input: List<Pair<List<Int>,List<Int>>>): String {
        TODO("Not yet implemented")
    }

    private fun processInput(inputLines: List<String>): List<Pair<List<Int>,List<Int>>> {
        return inputLines.map { line -> "(\\d+)-(\\d+),(\\d+)-(\\d+)".toRegex().matchEntire(line)!!.groupValues.drop(1).map { it.toInt() }.let { Pair((it[0]..it[1]).toList(), (it[2]..it[3]).toList()) } }
    }

    private fun isListFullyIntersect(first: List<Int>, second: List<Int>) : Boolean{
        val intersect = first.intersect(second.toSet())
        return intersect.size == first.size || intersect.size == second.size
    }
}