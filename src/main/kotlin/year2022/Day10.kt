package year2022

import AoCApp

object Day10 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<Instruction>): String {
        val registerAndSignalStrength = RegisterAndSignalStrength(1, 0)
        val cycleInformation = CycleInformation(0, 40, 20)
        input.forEach {
            when (it) {
                is Noop -> cycle(registerAndSignalStrength, cycleInformation)
                is AddX -> {
                    cycle(registerAndSignalStrength, cycleInformation)
                    cycle(registerAndSignalStrength, cycleInformation)
                    registerAndSignalStrength.add(it.value)
                }
            }
        }

        return registerAndSignalStrength.signalStrengthSum.toString()
    }

    private fun cycle(registerAndSignalStrength: RegisterAndSignalStrength, cycleInformation: CycleInformation) {
        cycleInformation.addCycle()
        if (cycleInformation.isReport()) {
            val signalStrength = cycleInformation.cycle * registerAndSignalStrength.x
            registerAndSignalStrength.signalStrengthSum += signalStrength

            println("cycle: ${cycleInformation.cycle}, x: ${registerAndSignalStrength.x}, strength: $signalStrength, sum: ${registerAndSignalStrength.signalStrengthSum}")

            cycleInformation.resetCycleUntilReport()
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

    data class RegisterAndSignalStrength(var x: Int, var signalStrengthSum: Int) {
        fun add(value: Int) {
            x += value
        }
    }

    data class CycleInformation(var cycle: Int, val reportCycle: Int, var cyclesUntilReport: Int) {
        fun addCycle() {
            cycle++
            cyclesUntilReport--
        }

        fun isReport(): Boolean {
            return cyclesUntilReport == 0
        }

        fun resetCycleUntilReport() {
            cyclesUntilReport = reportCycle
        }
    }
}