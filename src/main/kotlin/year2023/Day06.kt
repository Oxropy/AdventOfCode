package year2023

import AoCApp

object Day06: AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = inputLines
        printPart(1, part1(input))
//        printPart(2, part2(input))
    }

    private fun part1(input: List<String>): String {
        val time = input[0].drop(11).split(' ').filter { it.isNotEmpty() }.map { it.trim().toInt() }
        val distance = input[1].drop(11).split(' ').filter { it.isNotEmpty() }.map { it.trim().toInt() }
        val records = time.indices.map { Pair(time[it], distance[it]) }

        return records.map { record ->
            (1 until record.first).count { holdTime ->
                calculateDistance(
                    record.first,
                    holdTime
                ) > record.second
            }
        }.reduce { acc, i -> acc * i }.toString()
    }

    private fun part2(input: List<String>): String {
        TODO("Not yet implemented")
    }

    private fun calculateDistance(time: Int, holdTime: Int): Int {
        return (time - holdTime) *  holdTime
    }
}