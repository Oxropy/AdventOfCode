package year2021

import AoCApp
import java.util.*

object Day10 : AoCApp() {

    private val points = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)

    @JvmStatic
    fun main(args: Array<String>) {
        printPart(1, part1(inputLines))
        printPart(2, part2(inputLines))
    }

    private fun part1(inputLines: List<String>): String {
        return inputLines.sumOf { processLine(it) }.toString()
    }

    private fun processLine(line: String): Int {
        val stack = Stack<Char>()
        val c = line.firstOrNull {
            if (it == '(' || it == '{' || it == '[' || it == '<') {
                stack.add(it)
                false
            } else {
                val peek = stack.peek()
                if (!(peek == '(' && it != ')' || peek == '{' && it != '}' || peek == '[' && it != ']' || peek == '<' && it != '>')) {
                    stack.pop()
                    false
                } else {
                    true
                }
            }
        }

        return if (c == null) {
            0
        } else {
            points[c]!!
        }
    }

    private fun part2(inputLines: List<String>): String {
        TODO("Not yet implemented")
    }
}