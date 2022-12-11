package year2022

import AoCApp
import java.math.BigInteger

object Day11 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputGroupedLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(monkeys: List<Monkey>): String {
        return processMonkeyRounds(monkeys.map { it.clone() }, length=20, divideWorryLevel=3.toBigInteger())
    }

    private fun part2(monkeys: List<Monkey>): String {
        return processMonkeyRounds(monkeys.map { it.clone() }, length=10000, divideWorryLevel=1.toBigInteger())
    }

    private fun processMonkeyRounds(
        monkeys: List<Monkey>,
        length: Int,
        divideWorryLevel: BigInteger
    ): String {
        for (round in 0 until length) {
            monkeys.forEach { monkey ->
                monkey.items.clear()
                monkey.items.addAll(monkey.newItems)
                monkey.newItems.clear()

                monkey.items.forEach { item ->
                    monkey.inspectedItems++
                    var worryLevel = monkey.operation(item)
                    if (divideWorryLevel > BigInteger.ONE){
                        worryLevel /= divideWorryLevel
                    }
                    if (worryLevel % monkey.divisible == BigInteger.ZERO) {
                        monkeys[monkey.trueThrowTo].newItems.add(worryLevel)
                    } else {
                        monkeys[monkey.falseThrowTo].newItems.add(worryLevel)
                    }
                }
            }
        }

        return monkeys.map { it.inspectedItems }.sortedDescending().subList(0, 2).let { it[0] * it[1].toLong() }.toString()
    }

    private fun processInput(inputGroups: List<List<String>>): List<Monkey> {
        return inputGroups.map { group -> initMonkey(group) }
    }

    private fun initMonkey(monkeyInfos: List<String>): Monkey {
        val items =
            monkeyInfos[1].drop(monkeyInfos[1].indexOf(":") + 1).split(",").map { it.trim().toBigInteger() }.toMutableList()
        val operation = monkeyInfos[2].drop(monkeyInfos[2].indexOf(":") + 1).split(" ").let {
            val isMultiply = it[4] == "*"
            val isConst = it[5] != "old"

            if (isMultiply) {
                if (isConst) {
                    { value: BigInteger -> value * it[5].toBigInteger() }
                } else {
                    { value: BigInteger -> value * value }
                }
            } else {
                if (isConst) {
                    { value: BigInteger -> value + it[5].toBigInteger() }
                } else {
                    { value: BigInteger -> value + value }
                }
            }
        }

        val divisible = monkeyInfos[3].split(" ").last().toBigInteger()
        val trueThrowTo = monkeyInfos[4].last().digitToInt()
        val falseThrowTo = monkeyInfos[5].last().digitToInt()
        return Monkey(mutableListOf(), items, operation, divisible, trueThrowTo, falseThrowTo, 0)
    }

    data class Monkey(
        val items: MutableList<BigInteger>,
        val newItems: MutableList<BigInteger>,
        val operation: (BigInteger) -> BigInteger,
        val divisible: BigInteger,
        val trueThrowTo: Int,
        val falseThrowTo: Int,
        var inspectedItems: Int
    ){
        fun clone(): Monkey{
            return Monkey(items.toMutableList(), newItems.toMutableList(), operation, divisible, trueThrowTo, falseThrowTo, inspectedItems)
        }
    }
}