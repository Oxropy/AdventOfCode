package year2020

import AoCApp
import kotlin.math.abs

object Day12 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val instructions = processInput(inputLines)
        printPart(1, part1(instructions))
        printPart(2, part2(inputLines))
    }

    private fun part1(instructions: List<Pair<String, Int>>): String {
        return executeInstructions(instructions, 0, Position(Point(0,0), Direction.EAST)).point.let { abs(it.x) + abs(it.y) }.toString()
    }

    private fun part2(list: List<String>): String {
        TODO("Not yet implemented")
    }

    private fun processInput(lines: List<String>): List<Pair<String, Int>> {
        val regex = Regex("""(\w)(\d+)""")
        return lines.map { line ->
            regex.find(line)!!.let {
                val (action, value) = it.destructured
                Pair(action, value.toInt())
            }
        }
    }

    private fun executeInstructions(instructions: List<Pair<String, Int>>, index: Int, position: Position): Position {
        val instruction = instructions[index]
        val newPosition = when (instruction.first) {
            "N" -> goDirection(position, Direction.NORTH, instruction.second)
            "S" -> goDirection(position, Direction.SOUTH, instruction.second)
            "E" -> goDirection(position, Direction.EAST, instruction.second)
            "W" -> goDirection(position, Direction.WEST, instruction.second)
            "L" -> turn(position, instruction.second, false)
            "R" -> turn(position, instruction.second, true)
            "F" -> goForward(position, instruction.second)
            else -> unreachable()
        }

        val nextIndex = index + 1
        return if (nextIndex >= instructions.count()) newPosition
        else executeInstructions(instructions, nextIndex, newPosition)
    }

    enum class Direction(val dir: Int, val vector: Point) {
        NORTH(0, Point(0, 1)),
        EAST(1, Point(1, 0)),
        SOUTH(2, Point(0, -1)),
        WEST(3, Point(-1, 0))
    }

    private fun getDirection(value: Int): Direction {
        return when (value) {
            0 -> Direction.NORTH
            1 -> Direction.EAST
            2 -> Direction.SOUTH
            3 -> Direction.WEST
            else -> unreachable()
        }
    }

    data class Position(val point: Point, val direction: Direction)

    private fun turn(position: Position, degree: Int, right: Boolean): Position {
        val newDirection = getDirection(calcTurn(position.direction, degree, right))
        return Position(position.point, newDirection)
    }

    private fun calcTurn(direction: Direction, degree: Int, right: Boolean): Int {
        val newDirection = direction.dir + (degree / 90) % 4 * if (right) 1 else -1
        return when {
            newDirection > 3 -> newDirection - 4
            newDirection < 0 -> newDirection + 4
            else -> newDirection
        }
    }

    private fun goForward(position: Position, units: Int): Position {
        return goDirection(position, position.direction, units)
    }

    private fun goDirection(position: Position, direction: Direction, units: Int): Position {
        val newPoint = position.point + (direction.vector * units)
        return Position(newPoint, position.direction)
    }
}