package year2021

import AoCApp

object Day03 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        printPart(1, part1(inputLines))
//        printPart(2, part2(inputLines))
    }

    private fun part1(input: List<String>): String {
        val boolValues = (0 until input[0].length).map { i -> (input.count { value -> value[i] == '1' } > input.size / 2) }
        val gamma = Integer.parseInt(boolValues.map { it.toIntChar() }.joinToString(""), 2)
        val epsilon = Integer.parseInt(boolValues.map { (!it).toIntChar() }.joinToString(""), 2)


        return (gamma * epsilon).toString()
    }

    private fun Boolean.toIntChar() = if (this) '1' else '0'
}