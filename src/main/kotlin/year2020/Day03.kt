package year2020

import AoCApp

object Day03 : AoCApp() {
    private const val tree = '#'

    @JvmStatic
    fun main(args: Array<String>) {
        println(part1(inputLines))
        println(part2(inputLines))
    }

    private fun part1(lines: List<String>): String {
        val result = checkSlops(lines, listOf(Pair(3, 1)))
        return result.toString()
    }

    private fun part2(lines: List<String>): String {
        return checkSlops(lines, listOf(
            Pair(1, 1),
            Pair(3, 1),
            Pair(5, 1),
            Pair(7, 1),
            Pair(1, 2)
        )).toString()
    }

    private fun hitTreesWithSlopes(lines: List<String>, right: Int, down: Int): Long {
        val width = lines[0].length

        var goneDown = down

        var currentColumn = 0
        var result = 0L
        for (line in lines) {
            if (goneDown < down) {
                println(line)
                goneDown++
                continue
            }

            goneDown = 0

            if (line[currentColumn] == tree) {
                printLine(line, currentColumn, true)
                result++
            } else {
                printLine(line, currentColumn, false)
            }
            currentColumn += right
            if (currentColumn >= width) {
                currentColumn -= width
            }

            goneDown++
        }
        return result
    }

    private fun checkSlops(grid: List<String>, slopes: List<Pair<Int, Int>>): Long {
        val width = grid.first().length

        return slopes
            .map { (dx, dy) -> checkTrees(grid, width, 0, 0, dx, dy, 0) }
            .fold(1) { a, b -> a * b }
    }

    private tailrec fun checkTrees(grid: List<String>, width: Int, x: Int, y: Int, dx: Int, dy: Int, trees: Long): Long {
        if (y >= grid.size) {
            return trees
        }
        val change = if (grid[y][x] == tree) 1 else 0

        return checkTrees(grid, width, (x + dx).let { if (x >= width) it - width else it }, y + dy, dx, dy, trees + change)
    }

    private fun hitTrees(lines: List<String>, right: Int, down: Int): Long {
        val width = lines[0].length
        var currentColumn = 0
        var result = 0L
        for (index in lines.indices step down) {
            if (lines[index][currentColumn%width] == tree) {
                result++
            }

            currentColumn += right
        }

        return result
    }

    private fun printLine(line: String, position: Int, isTree: Boolean) {
        val newline = line.toCharArray()
        newline[position] = if (isTree) 'X' else 'o'
        println(newline.concatToString())
    }

}