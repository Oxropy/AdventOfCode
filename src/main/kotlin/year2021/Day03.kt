package year2021

import AoCApp

object Day03 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        printPart(1, part1(inputLines))
        printPart(2, part2(inputLines))
    }

    private fun part1(input: List<String>): String {
        val boolValues = (0 until input[0].length).map { i -> (input.count { value -> value[i] == '1' } > input.size / 2) }
        val gamma = Integer.parseInt(boolValues.map { it.toIntChar() }.joinToString(""), 2)
        val epsilon = Integer.parseInt(boolValues.map { (!it).toIntChar() }.joinToString(""), 2)


        return (gamma * epsilon).toString()
    }

    private fun part2(input: List<String>): String {
        val oxygenGeneratorRating = findRating(input, 0, "") { countedOnes, halfLength -> (countedOnes >= halfLength).toIntChar() }
        val co2ScrubberRating = findRating(input, 0, "") { countedOnes, halfLength -> (countedOnes < halfLength).toIntChar() }

        return (oxygenGeneratorRating * co2ScrubberRating).toString()
    }

    private fun findRating(input: List<String>, column: Int, binaryFilter: String, rating: (Int, Float) -> Char): Int {
        if (input.size == 1) {
            return Integer.parseInt(input[0], 2)
        }

        val filter = binaryFilter + rating(input.count { value -> value[column] == '1' }, input.size / 2f)

        return findRating(input.filter { it.startsWith(filter) }, column + 1, filter, rating)
    }

    private fun Boolean.toIntChar() = if (this) '1' else '0'
}