package year2022

import AoCApp

object Day05 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(input)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: Pair<Map<Int, MutableList<String>>, List<Procedure>>): String {
        val procedures = input.second
        val cratesLocation = input.first.map { Pair(it.key, it.value.toMutableList()) }.toMap()
        procedures.forEach { procedure ->
            for (i in 1..procedure.amount) {
                val last = cratesLocation[procedure.stackFrom]?.last()
                cratesLocation[procedure.stackFrom]?.removeLast()
                cratesLocation[procedure.stackTo]?.add(last!!)
            }
        }

        return cratesLocation.values.map { it.last() }.joinToString("") { it }
    }

    private fun part2(input: Pair<Map<Int, MutableList<String>>, List<Procedure>>): String {
        val procedures = input.second
        val cratesLocation = input.first.map { Pair(it.key, it.value.toMutableList()) }.toMap()
        procedures.forEach { procedure ->
            for (i in 0 until procedure.amount) {
                val last = cratesLocation[procedure.stackFrom]?.last()
                cratesLocation[procedure.stackFrom]?.removeLast()
                val size = cratesLocation[procedure.stackTo]?.size!!
                cratesLocation[procedure.stackTo]?.add(size - i, last!!)
            }
        }

        return cratesLocation.values.map { it.last() }.joinToString("") { it }
    }

    private fun processInput(input: String): Pair<Map<Int, MutableList<String>>, List<Procedure>> {
        val inputLines = input.split("\r\n")
        val indexOfBlank = inputLines.indexOf("")
        val cratesLocation = inputLines.take(indexOfBlank)
        val procedureList = inputLines.drop(indexOfBlank + 1)
        return Pair(getCratesLocation(cratesLocation), getProcedures(procedureList))
    }

    private fun getCratesLocation(input: List<String>): Map<Int, MutableList<String>> {
        val cratesLocation = input.last().split("   ").associate { Pair(it.trim().toInt(), mutableListOf<String>()) }
        input.dropLast(0).reversed().map { line ->
            """\s(\s)\s{1,2}|\[(\w)]\s?""".toRegex().findAll(line).withIndex()
                .forEach { match ->
                    if (match.value.groups.size == 3) match.value.groups[2]?.let {
                        cratesLocation[match.index + 1]?.add(it.value)
                    }
                }
        }
        return cratesLocation
    }

    private fun getProcedures(input: List<String>): List<Procedure> {
        return input.map { line ->
            """move (\d+) from (\d+) to (\d+)""".toRegex().matchEntire(line)!!.groupValues.drop(1).map { it.toInt() }
                .let { Procedure(it[1], it[2], it[0]) }
        }
    }

    data class Procedure(val stackFrom: Int, val stackTo: Int, val amount: Int)
}