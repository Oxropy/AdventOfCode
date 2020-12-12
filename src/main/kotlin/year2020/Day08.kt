package year2020

import AoCApp

object Day08 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(lines: List<Pair<String, Int>>): String {
        return calculateAccumulator(lines, 0, 0, mutableListOf(), false).toString()
    }

    private fun part2(lines: List<Pair<String, Int>>): String {
        return calculateAccumulatorWithChange(lines, 0, 0, mutableListOf()).toString()
    }

    private fun processInput(lines: List<String>): List<Pair<String, Int>> {
        return lines.map { line -> line.split(" ").let { Pair(it[0], it[1].toInt()) } }
    }

    private fun calculateAccumulator(
        instructions: List<Pair<String, Int>>,
        index: Int,
        accumulator: Int,
        passedIndex: MutableList<Int>,
        isNullWhenIndexPassed: Boolean
    ): Int? {

        if (passedIndex.contains(index)) {
            return if (isNullWhenIndexPassed) {
                null
            } else {
                accumulator
            }
        }

        if (index >= instructions.count()) {
            return accumulator
        }

        return executeInstructionCalculateAccumulator(instructions, index, accumulator, passedIndex, isNullWhenIndexPassed)
    }

    private fun calculateAccumulatorWithChange(
        instructions: List<Pair<String, Int>>,
        index: Int,
        accumulator: Int,
        passedIndex: MutableList<Int>,
        isNullWhenIndexPassed: Boolean = true
    ): Int? {
        val (instruction, value) = instructions[index]

        val result = when (instruction) {
            "jmp" -> executeInstructionCalculateAccumulator("nop", value, instructions, index, accumulator, passedIndex, isNullWhenIndexPassed)
            "nop" -> executeInstructionCalculateAccumulator("jmp", value, instructions, index, accumulator, passedIndex, isNullWhenIndexPassed)
            else -> null
        }

        if (result != null) {
            return result
        }

        return executeInstructionCalculateAccumulatorWithChange(instructions, index, accumulator, passedIndex, isNullWhenIndexPassed)
    }

    private fun executeInstructionCalculateAccumulator(
        instructions: List<Pair<String, Int>>,
        index: Int,
        accumulator: Int,
        passedIndex: MutableList<Int>,
        isNullWhenIndexPassed: Boolean
    ) : Int?
    {
        return executeInstructionOnIndex(instructions, index, accumulator, passedIndex, isNullWhenIndexPassed, ::calculateAccumulator)
    }

    private fun executeInstructionCalculateAccumulator(
        instruction: String,
        value: Int,
        instructions: List<Pair<String, Int>>,
        index: Int,
        accumulator: Int,
        passedIndex: MutableList<Int>,
        isNullWhenIndexPassed: Boolean
    ) : Int?
    {
        return executeInstruction(instruction, value, instructions, index, accumulator, passedIndex, isNullWhenIndexPassed, ::calculateAccumulator)
    }

    private fun executeInstructionCalculateAccumulatorWithChange(
        instructions: List<Pair<String, Int>>,
        index: Int,
        accumulator: Int,
        passedIndex: MutableList<Int>,
        isNullWhenIndexPassed: Boolean
    ) : Int?
    {
        return executeInstructionOnIndex(instructions, index, accumulator, passedIndex, isNullWhenIndexPassed, ::calculateAccumulatorWithChange)
    }

    private fun executeInstructionOnIndex(
        instructions: List<Pair<String, Int>>,
        index: Int,
        accumulator: Int,
        passedIndex: MutableList<Int>,
        isNullWhenIndexPassed: Boolean,
        doInstruction: (List<Pair<String, Int>>, Int, Int, MutableList<Int>, Boolean) -> Int?
    ): Int? {
        return instructions[index].let { (instruction, value) ->
            executeInstruction(
                instruction,
                value,
                instructions,
                index,
                accumulator,
                passedIndex,
                isNullWhenIndexPassed,
                doInstruction
            )
        }
    }

    private fun executeInstruction(
        instruction: String,
        value: Int,
        instructions: List<Pair<String, Int>>,
        index: Int,
        accumulator: Int,
        passedIndex: MutableList<Int>,
        isNullWhenIndexPassed: Boolean,
        doInstruction: (List<Pair<String, Int>>, Int, Int, MutableList<Int>, Boolean) -> Int?
    ): Int? {
        passedIndex.add(index)

        return when (instruction) {
            "nop" -> doInstruction(instructions, index + 1, accumulator, passedIndex, isNullWhenIndexPassed)
            "jmp" -> doInstruction(
                instructions,
                index + value,
                accumulator,
                passedIndex,
                isNullWhenIndexPassed
            )
            "acc" -> doInstruction(
                instructions,
                index + 1,
                accumulator + value,
                passedIndex,
                isNullWhenIndexPassed
            )
            else -> unreachable()
        }
    }
}