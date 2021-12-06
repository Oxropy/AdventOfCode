package year2021

import AoCApp

object Day06: AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(input)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<Lanternfish>): String {
        return simulate(0,80, input).toString()
    }

    private fun part2(input: List<Lanternfish>): String {
        return simulate(0,256, input).toString()
    }

    private fun processInput(input: String): List<Lanternfish> {
        return input.split(',').map { it.toInt() }.groupingBy { it }.eachCount().map { Lanternfish(it.key, it.value.toLong()) }
    }

    private fun simulate(currentDay: Int, days: Int, fishes: List<Lanternfish>): Long {
        if (currentDay >= days) {
            return fishes.sumOf { it.occurrence }
        }

        val newFishes = fishes.mapNotNull { it.dayPast() }
        val newSchool = (fishes + newFishes).groupBy { it.daysLeft }.map { fishAge -> Lanternfish(fishAge.key, fishAge.value.sumOf { it.occurrence }) }

        return simulate(currentDay + 1, days, newSchool)
    }

    private class Lanternfish(var daysLeft: Int, var occurrence: Long)
    {
        fun dayPast(): Lanternfish? {
            return if (daysLeft == 0) {
                daysLeft = 6
                Lanternfish(8, occurrence)
            } else {
                daysLeft -= 1
                null
            }
        }

        override fun toString(): String {
            return "$daysLeft occurred $occurrence"
        }
    }
}