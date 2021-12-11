package year2021

import AoCApp
import java.util.*

object Day10 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        printPart(1, part1(inputLines))
        printPart(2, part2(inputLines))
    }

    private fun part1(inputLines: List<String>): String {
        return inputLines.sumOf { processCorruptedLine(it) }.toString()
    }

    private fun processCorruptedLine(line: String): Int {
        return when (getCorruptedCharacter(line)) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0
        }
    }

    private fun getCorruptedCharacter(line: String): Char? {
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
        return c
    }

    private fun part2(inputLines: List<String>): String {
        val incomplete = inputLines.filter { getCorruptedCharacter(it) == null }
        val middle = incomplete.size / 2
        return incomplete.map { processIncompleteLine(it) }.sortedBy { it }[middle].toString()
    }

    private fun processIncompleteLine(line: String): Long {
        var reversed = line.reversed()
        val bracketsCloseToOpen = mapOf(')' to '(', '}' to '{', ']' to '[', '>' to '<')
        val bracketsOpenToClose = mapOf('(' to ')', '{' to '}', '[' to ']', '<' to '>')

        var result = 0L

        while (reversed.any()) {
            val current = reversed[0]
            val opening = bracketsCloseToOpen[current]
            if (opening == null) {
                result = result * 5 + when (bracketsOpenToClose[current]) {
                    ')' -> 1
                    ']' -> 2
                    '}' -> 3
                    '>' -> 4
                    else -> 0
                }

                reversed = reversed.substring(1)
                continue
            }

            var indexOfOpenBracket = reversed.indexOf(opening)
            var indexOfClosedBracket = reversed.indexOf(current, 1)
            while (indexOfClosedBracket != -1 && indexOfOpenBracket > indexOfClosedBracket) {
                indexOfClosedBracket = reversed.indexOf(current, indexOfClosedBracket + 1)
                indexOfOpenBracket = reversed.indexOf(opening, indexOfOpenBracket + 1)
            }

            reversed = reversed.substring(indexOfOpenBracket + 1)
        }

        return result
    }


}