package year2020

import AoCApp

object Day07 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val parseInput = parseInput(inputLines)
        printPart(1, part1(parseInput))
        printPart(2, part2(parseInput))
    }

    private const val shinyGold = "shiny gold"

    private fun part1(lines: Map<String, Map<String, Int>>): String {
        return lines.count { (bag, bagContent) ->
            if (bag == shinyGold) {
                false
            } else {
                val findShinyGold = findShinyGold(bag, bagContent, lines)
                findShinyGold
            }
        }.toString()
    }

    private fun part2(lines: Map<String, Map<String, Int>>): String {
        val shinyGoldBag = lines[shinyGold]!!
        return calcBagContent(shinyGoldBag, lines).toString()
    }

    private fun calcBagContent(
        bagContent: Map<String, Int>,
        definition: Map<String, Map<String, Int>>
    ): Int {
        return bagContent.entries.sumOf { it.value + (it.value * calcBagContent(it.key, definition)) }
    }

    private fun calcBagContent(
        bag: String,
        definition: Map<String, Map<String, Int>>
    ): Int {
        if (bag.isEmpty()) {
            return 1
        }

        val bagContent = definition[bag]!!
        return calcBagContent(bagContent, definition)
    }

    private fun findShinyGold(
        bag: String,
        bagContent: Map<String, Int>,
        bags: Map<String, Map<String, Int>>
    ): Boolean {
        if (bag == shinyGold) {
            return true
        }

        if (bagContent.isEmpty()) {
            return false
        }

        return bagContent.any {
            findShinyGold(
                it.key,
                bags[it.key]!!,
                bags
            )
        }
    }

    private fun parseInput(lines: List<String>): Map<String, Map<String, Int>> {
        val regex = Regex("""(\d+) (.*)""")

        return lines.map { line ->
            line.split(" contain ").let {
                val key = replaceBag(it[0])
                val values = getBagContent(it[1], regex)
                Pair(key, values)
            }
        }.toMap()
    }

    private fun getBagContent(bagContent: String, regex: Regex): Map<String, Int> {
        return bagContent.split(", ")
            .map { bag -> replaceBag(bag) }
            .let { bags ->
                when (bags[0]) {
                    "no other" -> mapOf()
                    else -> getMapOfBags(bags, regex)
                }
            }
    }

    private fun getMapOfBags(bags: List<String>, regex: Regex): Map<String, Int> {
        return bags.map { bag ->
            regex.find(bag)!!.let { bagCount ->
                val (count, color) = bagCount.destructured
                Pair(color, count.toInt())
            }
        }.toMap()
    }

    private fun replaceBag(bag: String): String {
        return bag.replace(Regex(""" bags?.?"""), "")
    }
}