package year2020

import AoCApp

object Day06 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        printPart(1, part1(inputGroupedLines))
        printPart(2, part2(inputGroupedLines))
    }

    private fun part1(groups: List<List<String>>): String {
        return groups.sumBy { group -> group.joinToString("").toCharArray().distinct().count() }.toString()
    }

    private fun part2(groups: List<List<String>>): String {
        return groups.sumBy { group ->
            group.joinToString("").toCharArray().toList().groupingBy { gr -> gr }.eachCount()
                .count { charCount -> charCount.value == group.count() }
        }.toString()
    }
}