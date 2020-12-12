package year2020

import AoCApp

object Day09 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(lines: List<Long>): String {
        return determineFirstNumberNotToCalculate(25, lines).toString()
    }

    private fun part2(lines: List<Long>): String {
        val number = determineFirstNumberNotToCalculate(25, lines)
        val determinedContiguousSet = determineContiguousSet(lines, number)
        if (determinedContiguousSet.isEmpty()) {
            return ""
        }

        val min = determinedContiguousSet.minOrNull()!!
        val max = determinedContiguousSet.maxOrNull()!!
        return (min + max).toString()
    }

    private fun processInput(lines: List<String>): List<Long> {
        return lines.map { line -> line.toLong() }
    }

    private fun determineFirstNumberNotToCalculate(windowSize: Int, values: List<Long>): Long {
        return values.windowed(windowSize + 1, 1).first { canCalculateLastNumber(it) != null }.last()
    }

    private fun canCalculateLastNumber(values: List<Long>): Long? {
        val neededValue = values.last()
        if (values.any {
                if (it * 2 == neededValue) {
                    false
                } else {
                    values.contains(neededValue - it)
                }
            }) {
            return null
        }

        return neededValue
    }

    private fun determineContiguousSet(lines: List<Long>, number: Long): List<Long> {
        val contiguousSet = mutableListOf<Long>()
        for (line in lines) {
            contiguousSet.add(line)

            var sum = contiguousSet.sum()

            while (sum > number) {
                contiguousSet.removeAt(0)
                sum = contiguousSet.sum()
            }

            if (sum == number && contiguousSet.count() > 1) {
                return contiguousSet
            }
        }

        contiguousSet.clear()
        return  contiguousSet
    }
}