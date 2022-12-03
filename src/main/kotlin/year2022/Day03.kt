package year2022

import AoCApp

object Day03 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<String>): String {
        return input.sumOf { line ->
            val firstCompartment = line.substring(0, line.length / 2).toCharArray()
            val secondCompartment = line.substring(line.length / 2).toCharArray()
            val inBoth = firstCompartment.intersect(secondCompartment.asIterable().toSet()).first()
            getPriority(inBoth)
        }.toString()
    }

    private fun part2(input: List<String>): String {
        return input.withIndex().groupBy { it.index / 3 }.map {
            val inALl = it.value[0].value.toCharArray().intersect(
                it.value[1].value.toCharArray().asIterable().toSet()
                    .intersect(it.value[2].value.toCharArray().asIterable().toSet())
            ).first()
            getPriority(inALl)
        }.sum().toString()
    }

    private fun processInput(inputLines: List<String>): List<String> {
        return inputLines
    }

    private fun getPriority(value: Char): Int {
        return if (value.isUpperCase())
            (value.code - 'A'.code) + 27
        else
            (value.code - 'a'.code) + 1
    }
}