package year2020

import AoCApp
import kotlin.math.abs

object Day12 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val instructions = parseInput(inputLines)
        printPart(1, part1(instructions))
        printPart(2, part2(instructions))
    }

    private fun part1(instructions: List<Instr>): String {
        return executeInstructionsPart1(instructions, 0, Position(Point(0,0), Direction.EAST)).point.let { abs(it.x) + abs(it.y) }.toString()
    }

    private fun part2(instructions: List<Instr>): String {
        return executeInstructionsPart2(instructions, 0, Position(Point(0,0), Direction.EAST), Point(10, 1)).point.let { abs(it.x) + abs(it.y) }.toString()
    }

    private tailrec fun executeInstructionsPart1(instructions: List<Instr>, index: Int, position: Position): Position {
        val newPosition = when (val instruction = instructions[index]) {
            is Instr.Move -> goDirection(position, instruction.direction)
            is Instr.Rotate -> turn(position, instruction.degree)
            is Instr.MoveForward -> goForward(position, instruction.n)
        }

        val nextIndex = index + 1
        return if (nextIndex >= instructions.count()) newPosition
        else executeInstructionsPart1(instructions, nextIndex, newPosition)
    }

    private tailrec fun executeInstructionsPart2(instructions: List<Instr>, index: Int, position: Position, waypoint: Point): Position {
        var newPosition = position
        var newWaypoint = waypoint
        when (val instruction = instructions[index]) {
            is Instr.Move -> newWaypoint = moveWaypoint(waypoint, instruction)
            is Instr.Rotate -> newWaypoint = rotateWaypoint(waypoint, instruction.degree)
            is Instr.MoveForward -> newPosition = goForwardToWaypoint(position, waypoint, instruction.n)
        }

        val nextIndex = index + 1
        return if (nextIndex >= instructions.count()) newPosition
        else executeInstructionsPart2(instructions, nextIndex, newPosition, newWaypoint)
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

    private fun turn(position: Position, degree: Int): Position {
        val newDirection = getDirection(calcTurn(position.direction, degree))
        return Position(position.point, newDirection)
    }

    private fun calcTurn(direction: Direction, degree: Int): Int {
        val newDirection = direction.dir + (degree / 90) % 4
        return when {
            newDirection > 3 -> newDirection - 4
            newDirection < 0 -> newDirection + 4
            else -> newDirection
        }
    }

    private fun goForward(position: Position, units: Int): Position {
        return goDirection(position, position.direction.vector * units)
    }

    private fun goDirection(position: Position, direction: Point): Position {
        val newPoint = position.point + direction
        return Position(newPoint, position.direction)
    }

    private fun moveWaypoint(waypoint: Point, instruction: Instr.Move) = waypoint + instruction.direction

    private fun rotateWaypoint(waypoint: Point, degree: Int): Point {
        val newDirection = ((degree / 90) + 4) % 4

        var newWaypoint = waypoint
        for (i in 1..newDirection) {
            newWaypoint = rotate90Degree(newWaypoint)
        }

        return newWaypoint
    }

    private fun rotate90Degree(waypoint: Point): Point {
        return Point(waypoint.y * 1, waypoint.x * -1)
    }

    private fun goForwardToWaypoint(position: Position, waypoint: Point, times: Int): Position {
        return Position(position.point + waypoint * times, position.direction)
    }
}