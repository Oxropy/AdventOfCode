@file:Suppress("UNUSED_PARAMETER")

package year2023

import AoCApp
import java.lang.StringBuilder

object Day01 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = inputLines
        printPart(1, part1(input))
//        printPart(2, part2(input))
    }

    private fun part1(input: List<String>): String {
        return input.sumOf { line ->
            val first = line.first {
                it.isDigit()
            }
            val last = line.last {
                it.isDigit()
            }

            val sb = StringBuilder()
            sb.append(first)
            sb.append(last)
            return@sumOf sb.toString().toInt()
        }.toString()
    }

    private fun part2(input: List<String>): String {
        TODO("Not yet implemented")
    }

}