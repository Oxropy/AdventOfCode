package year2022

import AoCApp

object Day11 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputGroupedLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(monkeys: List<Monkey>): String {
        for (round in 0 until 20) {
            monkeys.forEach { monkey ->
                monkey.items.clear()
                monkey.items.addAll(monkey.newItems)
                monkey.newItems.clear()

                monkey.items.forEach { item ->
                    monkey.inspectedItems++
                    val worryLevel = monkey.operation(item) / 3
                    if (worryLevel % monkey.divisible == 0) {
                        monkeys[monkey.trueThrowTo].newItems.add(worryLevel)
                    } else {
                        monkeys[monkey.falseThrowTo].newItems.add(worryLevel)
                    }
                }
            }
        }

        return monkeys.map { it.inspectedItems }.sortedDescending().subList(0, 2).let { it[0] * it[1] }.toString()
    }

    private fun part2(monkeys: List<Monkey>): String {
        TODO("Not yet implemented $monkeys")
    }

    private fun processInput(inputGroups: List<List<String>>): List<Monkey> {
        return inputGroups.map { group -> initMonkey(group) }
    }

    private fun initMonkey(monkeyInfos: List<String>): Monkey {
        val items =
            monkeyInfos[1].drop(monkeyInfos[1].indexOf(":") + 1).split(",").map { it.trim().toInt() }.toMutableList()
        val operation = monkeyInfos[2].drop(monkeyInfos[2].indexOf(":") + 1).split(" ").let {
            val isMultiply = it[4] == "*"
            val isConst = it[5] != "old"

            if (isMultiply) {
                if (isConst) {
                    { value: Int -> value * it[5].toInt() }
                } else {
                    { value: Int -> value * value }
                }
            } else {
                if (isConst) {
                    { value: Int -> value + it[5].toInt() }
                } else {
                    { value: Int -> value + value }
                }
            }
        }

        val divisible = monkeyInfos[3].split(" ").last().toInt()
        val trueThrowTo = monkeyInfos[4].last().digitToInt()
        val falseThrowTo = monkeyInfos[5].last().digitToInt()
        return Monkey(mutableListOf(), items, operation, divisible, trueThrowTo, falseThrowTo, 0)
    }

    data class Monkey(
        val items: MutableList<Int>,
        val newItems: MutableList<Int>,
        val operation: (Int) -> Int,
        val divisible: Int,
        val trueThrowTo: Int,
        val falseThrowTo: Int,
        var inspectedItems: Int
    )
}