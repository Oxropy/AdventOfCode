package year2023

import AoCApp

object Day08: AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = inputGroupedLines
        printPart(1, part1(input))
//        printPart(2, part2(input))
    }

    private fun part1(input: List<List<String>>): String {
        val sequence = input[0][0].toCharArray()
        val regex = Regex("""([A-Z]{3}) = \(([A-Z]{3}), ([A-Z]{3})\)""")

        val nodeMap = input[1].associate { line ->
            regex.find(line)?.let {
                val (key, left, right) = it.destructured
                Pair(key, Pair(left, right))
            }!!
        }

        var index = 0
        var steps = 0
        var currentNode = "AAA"
        while (currentNode != "ZZZ") {
            if (index >= sequence.size) {
                index = 0
            }

            val direction = sequence[index]
            currentNode = nodeMap[currentNode].let { (if (direction == 'L') it?.first else it?.second)!! }
            steps++
            index++
        }

        return steps.toString()
    }

    private fun part2(list: List<List<String>>): String {
        TODO("Not yet implemented")
    }
}