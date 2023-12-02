package year2023

import AoCApp

object Day02: AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = parseGames(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(games: List<Game>): String {
        return games.filter { isPossible(it) }.sumOf { it.id }.toString()
    }

    private fun part2(games: List<Game>): String {
        return games.sumOf { getWithCubeCount(it) }.toString()
    }

    private fun isPossible(game: Game): Boolean {
        return !game.subsets.any { it.red > 12 || it.green > 13 || it.blue > 14 }
    }

    private fun getWithCubeCount(game: Game): Int {
        val maxRed = game.subsets.maxOf { it.red }
        val maxGreen = game.subsets.maxOf { it.green }
        val maxBlue = game.subsets.maxOf { it.blue }
        return maxRed * maxGreen * maxBlue
    }

    private fun parseGames(input: List<String>): List<Game> {
        return input.map { parseGame(it) }
    }

    private fun parseGame(line: String): Game {
        val subsetStart = line.indexOf(':')
        val id = line.substring(4, subsetStart).trim().toInt()
        val subsets = mutableListOf<Subset>()
        for (subset in line.substring(subsetStart + 1).split(";")) {
            val cubes = subset.split(",")
            var red = 0
            var green = 0
            var blue = 0
            for (cube in cubes) {
                val colors = cube.split(" ")
                val count = colors[1].trim().toInt()
                when (colors[2]) {
                    "red" -> red = count
                    "green" -> green = count
                    "blue" -> blue = count
                }
            }

            subsets.add(Subset(red, green, blue))
        }

        return Game(id, subsets)
    }

    data class Game(val id: Int, val subsets: List<Subset>)
    data class Subset(val red: Int, val green: Int, val blue: Int)
}