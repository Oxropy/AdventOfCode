package year2021

import AoCApp

object Day11 : AoCApp() {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: Map<Point, EnergyCounter>): String {
        val mutableMap = input.toMutableMap()
        return (1..100).sumOf { doStep(mutableMap) }.toString()
    }

    private fun part2(input: Map<Point, EnergyCounter>): String {
        TODO("Not yet implemented")
    }

    private fun processInput(inputLines: List<String>): Map<Point, EnergyCounter> {
        return inputLines.flatMapIndexed { y, line ->
            line.mapIndexed { x, char ->
                Pair(
                    Point(x, y),
                    EnergyCounter(Character.getNumericValue(char))
                )
            }
        }.toMap()
    }

    private val getAdjacentPoints = listOf(
        Point(-1, -1), Point(0, -1), Point(1, -1),
        Point(-1, 0), Point(1, 0),
        Point(-1, 1), Point(0, 1), Point(1, 1)
    )

    private fun doStep(dumboMap: MutableMap<Point, EnergyCounter>): Int {
        dumboMap.values.forEach { it.add() }

        var flashed = dumboMap.filter { it.value.flash() }
        while (flashed.isNotEmpty()) {
            flashed.forEach { dumboPositionEnergy ->
                getAdjacentPoints.mapNotNull { dumboMap[dumboPositionEnergy.key + it] }
                    .forEach { it.add() }
            }

            flashed = dumboMap.filter { it.value.flash() }
        }

        return dumboMap.values.count { it.reset() }
    }

    private data class EnergyCounter(var energy: Int, var hasFlashed: Boolean) {
        constructor(energy: Int): this(energy, false)

        fun reset(): Boolean {
            return if (hasFlashed) {
                energy = 0
                hasFlashed = false
                true
            } else {
                false
            }
        }

        fun add() {
            energy += 1
        }

        fun flash(): Boolean {
            return if (energy > 9 && !hasFlashed) {
                hasFlashed = true
                true
            } else {
                false
            }
        }

        fun getPrintValue(): String {
            return if (hasFlashed) {
                "X"
            } else {
                energy.toString()
            }
        }
    }

    private fun printMap(input: Map<Point, EnergyCounter>) {
        for (y in 0..9) {
            for (x in 0..9) {
                print(input[Point(x, y)]!!.getPrintValue())
            }
            println()
        }
        println()
    }
}