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
        TODO("Not yet implemented")
    }

    private fun processInput(input: String): List<Lanternfish> {
        return input.split(',').map { Lanternfish(it.toInt()) }
    }

    private fun simulate(currentDay: Int, days: Int, fishes: List<Lanternfish>): Int {
//        println("After $currentDay days: $fishes")

        if (currentDay >= days) {
            return fishes.size
        }

        return simulate(currentDay + 1, days, fishes.flatMap { it.dayPast() })
    }

    private class Lanternfish(val daysLeft: Int)
    {
        fun dayPast(): List<Lanternfish> {
            val isDayLeft = daysLeft == 0
            val updated = reduceDaysLeft(isDayLeft)

            if (isDayLeft) {
                return listOf(updated, Lanternfish(8))
            }

            return listOf(updated)
        }

        private fun reduceDaysLeft(isDayLeft: Boolean): Lanternfish {
            return if (isDayLeft) {
                Lanternfish(6)
            } else {
                Lanternfish(daysLeft - 1)
            }
        }

        override fun toString(): String {
            return daysLeft.toString()
        }
    }
}