package year2021

import AoCApp

object Day12: AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(caveMap:Map<Cave, List<Cave>>): String {
        val start = caveMap.keys.first { it.name == "start" }
        return findPaths(caveMap, listOf(), listOf(start), caveMap[start]!!).size.toString()
    }

    private fun part2(caveMap: Map<Cave, List<Cave>>): String {
        TODO("Not yet implemented")
    }

    private fun processInput(input: List<String>): Map<Cave, List<Cave>> {
        val pairs = input.map { it.split('-').let { (from, to) -> Pair(createCave(from), createCave(to)) } }
        val reversePair = pairs.map { Pair(it.second, it.first) }
        return (pairs + reversePair).groupBy( { it.first }, {it.second})
    }

    private fun findPaths(caveMap: Map<Cave, List<Cave>>, paths: List<List<Cave>>, path: List<Cave>, caves: List<Cave>): List<List<Cave>> {
        return caves.flatMap { findPaths(caveMap, paths, path, it) }
    }

    private fun findPaths(caveMap: Map<Cave, List<Cave>>, paths: List<List<Cave>>, path: List<Cave>, cave: Cave): List<List<Cave>> {
        if (cave.name == "end") {
            val newPath = path + cave
            return paths + listOf(newPath)
        }

        if (!cave.isBig && path.contains(cave)) {
            return emptyList()
        }

        return caveMap[cave]!!.flatMap { findPaths(caveMap, paths, path + cave, it) }
    }

    private fun createCave(name: String): Cave {
        return Cave(name, name[0].isUpperCase())
    }

    private data class Cave(val name: String, val isBig: Boolean)
}