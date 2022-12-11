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
                is Instruction.Noop -> cyclePart1(registerAndSignalStrength, cycleInformation)
                is Instruction.AddX -> {
                    cyclePart1(registerAndSignalStrength, cycleInformation)
                    cyclePart1(registerAndSignalStrength, cycleInformation)
                    registerAndSignalStrength.add(it.value)
                }
            }
        }

        return registerAndSignalStrength.signalStrengthSum.toString()
    }

    private fun cyclePart1(registerAndSignalStrength: RegisterAndSignalStrength, cycleInformation: CycleInformation) {
        cycleInformation.addCycle()
        if (cycleInformation.isReport()) {
            val signalStrength = cycleInformation.cycle * registerAndSignalStrength.x
            registerAndSignalStrength.signalStrengthSum += signalStrength
            cycleInformation.resetCycleUntilReport()
        }
    }

    private fun part2(input: List<Instruction>): String {
        val registerAndSignalStrength = RegisterAndSignalStrength(1, 0)
        val cycleInformation = CycleInformation(0, 40, 40)
        input.forEach {
            when (it) {
                is Instruction.Noop -> cyclePart2(registerAndSignalStrength, cycleInformation)
                is Instruction.AddX -> {
                    cyclePart2(registerAndSignalStrength, cycleInformation)
                    cyclePart2(registerAndSignalStrength, cycleInformation)
                    registerAndSignalStrength.add(it.value)
                }
            }
        }

        return ""
    }

    private fun cyclePart2(registerAndSignalStrength: RegisterAndSignalStrength, cycleInformation: CycleInformation) {
        val rowCycle = cycleInformation.cycle % cycleInformation.reportCycle
        if (rowCycle == registerAndSignalStrength.x || rowCycle == registerAndSignalStrength.x - 1 || rowCycle == registerAndSignalStrength.x + 1) {
            print("#")
        } else {
            print(".")
        }

        cycleInformation.addCycle()
        if (cycleInformation.isReport()) {
            println()
            cycleInformation.resetCycleUntilReport()
        }
    }

    private fun processInput(inputLines: List<String>): List<Instruction> {
        return inputLines.map {
            val split = it.split(" ", limit = 2)
            when (split[0]) {
                "noop" -> Instruction.Noop
                "addx" -> Instruction.AddX(split[1].toInt())
                else -> unreachable()
            }
        }
    }

    sealed interface Instruction {
        object Noop : Instruction
        data class AddX(val value: Int) : Instruction
    }

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