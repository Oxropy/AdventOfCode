package year2023

import AoCApp

object Day06: AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = inputLines
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<String>): String {
        val time = input[0].drop(11).split(' ').filter { it.isNotEmpty() }.map { it.trim().toLong() }
        val distance = input[1].drop(11).split(' ').filter { it.isNotEmpty() }.map { it.trim().toLong() }
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
        val time = input[0].drop(11).filter { it.isDigit() }.toLong()
        val distance = input[1].drop(11).filter { it.isDigit() }.toLong()

        return (1 until time).count { holdTime ->
                calculateDistance(
                    time,
                    holdTime
                ) > distance
            }.toString()
    }

    private fun calculateDistance(time: Long, holdTime: Long): Long {
        return (time - holdTime) *  holdTime
    }
}