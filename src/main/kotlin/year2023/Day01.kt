package year2023

import AoCApp
import java.lang.StringBuilder

object Day01 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = inputLines
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<String>): String {
        return input.sumOf { concatFirstAndLastDigit(it) }.toString()
    }

    private fun part2(input: List<String>): String {
        return input.sumOf { textToNumber(it) }.toString()
    }

    private fun concatFirstAndLastDigit(line: String): Int {
        val first = line.first {
            it.isDigit()
        }
        val last = line.last {
            it.isDigit()
        }

        val sb = StringBuilder()
        sb.append(first)
        sb.append(last)
        return sb.toString().toInt()
    }

    private fun textToNumber(line: String): Int {
        return (firstTextToNumber(line).toString() + lastTextToNumber(line).toString()).toInt()
    }

    private val numberText = arrayOf(Pair("one", 1), Pair("two", 2), Pair("three", 3), Pair("four", 4), Pair("five", 5), Pair("six", 6), Pair("seven", 7), Pair("eight", 8), Pair("nine", 9), Pair("1", 1), Pair("2", 2), Pair("3", 3), Pair("4", 4), Pair("5", 5), Pair("6", 6), Pair("7", 7), Pair("8", 8), Pair("9", 9))

    private fun firstTextToNumber(line: String): Int {
        val index = IntArray(18)
        index[0] = line.indexOf(numberText[0].first)
        index[1] = line.indexOf(numberText[1].first)
        index[2] = line.indexOf(numberText[2].first)
        index[3] = line.indexOf(numberText[3].first)
        index[4] = line.indexOf(numberText[4].first)
        index[5] = line.indexOf(numberText[5].first)
        index[6] = line.indexOf(numberText[6].first)
        index[7] = line.indexOf(numberText[7].first)
        index[8] = line.indexOf(numberText[8].first)
        index[9] = line.indexOf(numberText[9].first)
        index[10] = line.indexOf(numberText[10].first)
        index[11] = line.indexOf(numberText[11].first)
        index[12] = line.indexOf(numberText[12].first)
        index[13] = line.indexOf(numberText[13].first)
        index[14] = line.indexOf(numberText[14].first)
        index[15] = line.indexOf(numberText[15].first)
        index[16] = line.indexOf(numberText[16].first)
        index[17] = line.indexOf(numberText[17].first)

        var lowestIndex = Int.MAX_VALUE
        for (i in index.indices) {
            if (index[i] >= 0 && (lowestIndex == Int.MAX_VALUE || index[i] < index[lowestIndex])) {
                lowestIndex = i
            }
        }

        return numberText[lowestIndex].second
    }

    private fun lastTextToNumber(line: String): Int {
        val index = IntArray(18)
        index[0] = line.lastIndexOf(numberText[0].first)
        index[1] = line.lastIndexOf(numberText[1].first)
        index[2] = line.lastIndexOf(numberText[2].first)
        index[3] = line.lastIndexOf(numberText[3].first)
        index[4] = line.lastIndexOf(numberText[4].first)
        index[5] = line.lastIndexOf(numberText[5].first)
        index[6] = line.lastIndexOf(numberText[6].first)
        index[7] = line.lastIndexOf(numberText[7].first)
        index[8] = line.lastIndexOf(numberText[8].first)
        index[9] = line.lastIndexOf(numberText[9].first)
        index[10] = line.lastIndexOf(numberText[10].first)
        index[11] = line.lastIndexOf(numberText[11].first)
        index[12] = line.lastIndexOf(numberText[12].first)
        index[13] = line.lastIndexOf(numberText[13].first)
        index[14] = line.lastIndexOf(numberText[14].first)
        index[15] = line.lastIndexOf(numberText[15].first)
        index[16] = line.lastIndexOf(numberText[16].first)
        index[17] = line.lastIndexOf(numberText[17].first)

        var highestIndex = 0
        for (i in index.indices) {
            if (index[i] > index[highestIndex]) {
                highestIndex = i
            }
        }

        return numberText[highestIndex].second
    }

}