package year2023

import AoCApp

object Day03: AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = inputLines
        printPart(1, part1(input))
//        printPart(2, part2(input))
    }

    private fun part1(input: List<String>): String {
        val partNumbers = mutableListOf<Int>()
        for (y in input.indices) {
            var currentNumber = ""
            var hasAdjacentSymbol = false
            for (x in input[y].indices) {
                val currentChar = input[y][x]
                if (currentChar.isDigit()) {
                    currentNumber += currentChar
                    if (isAdjacentASymbol(input, Point(x, y))) hasAdjacentSymbol = true
                }
                else {
                    if (hasAdjacentSymbol && currentNumber.isNotEmpty())
                        partNumbers.add(currentNumber.toInt())
                    currentNumber = ""
                    hasAdjacentSymbol = false
                }
            }
            if (hasAdjacentSymbol && currentNumber.isNotEmpty())
                partNumbers.add(currentNumber.toInt())
        }

        return partNumbers.sum().toString()
    }

    private fun part2(input: List<String>): String {
        TODO("Not yet implemented")
    }

    private fun isAdjacentASymbol(input: List<String>, point: Point): Boolean {
        return enumValues<Direction>().any {
            val directionPoint = point + it.vector
            if (!directionPoint.isNegative() && directionPoint.y < input.size && directionPoint.x < input[directionPoint.y].length) {
                val directionChar = input[directionPoint.y][directionPoint.x]
                if (directionChar != '.' && !directionChar.isDigit()) {
                    return@any true
                }
            }

            return@any false
        }
    }
}