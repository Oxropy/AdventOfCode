package year2023

import AoCApp

object Day09 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = inputLines
        printPart(1, part1(input))
//        printPart(2, part2(input))
    }

    private fun part1(input: List<String>): String {
        return input.sumOf { line ->
            line.split(' ').map { it.toInt() }.let {
                val intermediateValues = mutableListOf<MutableList<Int>>()
                intermediateValues.add(it.toMutableList())
                intermediateSteps(it, intermediateValues)
                getAdditionalSequenceValue(intermediateValues)
            }
        }.toString()
    }

    private fun part2(input: List<String>): String {
        TODO("Not yet implemented")
    }

    private fun intermediateSteps(values: List<Int>, intermediateValues: MutableList<MutableList<Int>>) {
        if (values.all { it == 0 }) {
            return
        }

        val currentIntermediateValues = mutableListOf<Int>()
        values.reduce { acc, i ->
            currentIntermediateValues.add(i - acc)
            i
        }

        intermediateValues.add(currentIntermediateValues)
        intermediateSteps(currentIntermediateValues, intermediateValues)
    }

    private fun getAdditionalSequenceValue(intermediateValues: MutableList<MutableList<Int>>): Int {
//        println(intermediateValues.last())
        for (i in intermediateValues.size - 1 downTo 1) {
            val element = intermediateValues[i].last() + intermediateValues[i - 1].last()
//            print(intermediateValues[i - 1])
//            println(element)
            intermediateValues[i - 1].add(element)
        }

        val result = intermediateValues[0].last()
//        println(result)
        return result
    }
}