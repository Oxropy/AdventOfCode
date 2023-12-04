package year2023

import AoCApp
import kotlin.math.pow

object Day04: AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = inputLines
        printPart(1, part1(input))
//        printPart(2, part2(input))
    }

    private fun part1(input: List<String>): String {
        return input.sumOf {
            val indexOfColon = it.indexOf(':')
            val values = it.substring(indexOfColon + 2).split('|')

            val winningNumbers = getNumbers(values[0])
            val ownNumbers = getNumbers(values[1])

            val intersect = winningNumbers.intersect(ownNumbers)
            if (intersect.isEmpty())
                return@sumOf 0

            2.0.pow(intersect.size - 1).toInt()
        }.toString()
    }


    private fun getNumbers(numbers: String): Set<Int> {
        return numbers.split(' ').filter { value -> value.isNotEmpty() }.map { value -> value.trim().toInt() }.toSet()
    }
    private fun part2(input: List<String>): String {
        TODO("Not yet implemented")
    }
}