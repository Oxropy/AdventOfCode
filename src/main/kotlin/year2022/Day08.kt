package year2022

import AoCApp

object Day08 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<List<Int>>): String {
        return input.withIndex().sumOf { indexedRowValue ->
            indexedRowValue.value.withIndex()
                .count { indexedColumnValue -> isVisible(input, indexedRowValue.index, indexedColumnValue.index) }
        }.toString()
    }

    private fun isVisible(input: List<List<Int>>, row: Int, column: Int): Boolean {
        val current = input[row][column]
        val top = if (row >= 1) input.take(row).maxOf { it[column] } else -1
        val left = if (column >= 1) input[row].take(column).maxOf { it } else -1
        val right = if (column <= input[row].size - 2) input[row].drop(column + 1).maxOf { it } else -1
        val bottom = if (row <= input.size - 2) input.drop(row + 1).maxOf { it[column] } else -1
        return current > top || current > left || current > right || current > bottom
    }

    private fun part2(input: List<List<Int>>): String {
        TODO("Not yet implemented")
    }

    private fun processInput(inputLines: List<String>): List<List<Int>> {
        return inputLines.map { line -> line.toCharArray().map { it.digitToInt() } }
    }
}