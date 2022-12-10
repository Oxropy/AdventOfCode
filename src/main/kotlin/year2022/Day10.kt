package year2022

import AoCApp

object Day10 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private var x = 1
    private var cycle = 0
    private var cyclesUntilReport = 20
    private const val reportCycle = 40
    private var signalStrengthSum = 0

    private fun part1(input: List<Instruction>): String {
        input.forEach {
            when (it) {
                is Noop -> cycle()
                is AddX -> {
                    cycle()
                    cycle()
                    x += it.value
                }
            }
        }

        return signalStrengthSum.toString()
    }

    private fun cycle() {
        cycle++
        cyclesUntilReport--
        if (cyclesUntilReport == 0) {
            val signalStrength = cycle * x
            signalStrengthSum += signalStrength

            println("cycle: $cycle, x: $x, strength: $signalStrength, sum: $signalStrengthSum")

            cyclesUntilReport = reportCycle
        }
    }

    private fun part2(input: List<Instruction>): String {
        TODO("Not yet implemented")
    }

    private fun processInput(inputLines: List<String>): List<Instruction> {
        return inputLines.map {
            val split = it.split(" ", limit = 2)
            when (split[0]) {
                "noop" -> Noop()
                "addx" -> AddX(split[1].toInt())
                else -> unreachable()
            }
        }
    }

    interface Instruction

    class Noop : Instruction

    data class AddX(val value: Int) : Instruction
}