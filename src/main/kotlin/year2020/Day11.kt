package year2020

import AoCApp

object Day11 : AoCApp() {

    private const val empty = 'L'
    private const val occupied = '#'
    private const val floor = '.'
    private const val undefined = ' '

    private val getAdjacentPoints = listOf(
        Point(-1, -1), Point(0, -1), Point(1, -1),
        Point(-1, 0), Point(1, 0),
        Point(-1, 1), Point(0, 1), Point(1, 1)
    )

    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputLines)
        val seatWidth = input[0].count()
        printPart(1, part1(input, seatWidth))
        printPart(2, part2(input, seatWidth))
    }

    private fun part1(seats: List<List<Char>>, seatWidth: Int): String {
        return getOccupiedSeatCount(simulateUntilChangeless(seats, 3, seatWidth, ::getAdjacent)).toString()
    }

    private fun part2(seats: List<List<Char>>, seatWidth: Int): String {
        return getOccupiedSeatCount(simulateUntilChangeless(seats, 4, seatWidth, ::getFirstSeenSeat)).toString()
    }

    private fun processInput(lines: List<String>): List<List<Char>> {
        return lines.map { line -> line.toCharArray().toList() }
    }

    private fun getOccupiedSeatCount(seats: List<List<Char>>): Int {
        return seats.sumOf { line -> line.count { it == occupied } }
    }

    private fun simulateUntilChangeless(
        seats: List<List<Char>>,
        maxOccupied: Int,
        seatWidth: Int,
        getAdjacent: (Point, List<List<Char>>, Int) -> List<Char>
    ): List<List<Char>> {
        val newSeats = simulate(seats, seatWidth, maxOccupied, getAdjacent)
        return if (newSeats == seats) newSeats
        else simulateUntilChangeless(newSeats, maxOccupied, seatWidth, getAdjacent)
    }

    private fun simulate(
        seats: List<List<Char>>,
        seatWidth: Int,
        maxOccupied: Int,
        getAdjacent: (Point, List<List<Char>>, Int) -> List<Char>
    ): List<List<Char>> {
        return seats.indices.map { y ->
            (0 until seatWidth).map { x ->
                getSeatValueByRule(
                    Point(x, y),
                    seats,
                    seatWidth,
                    getAdjacent,
                    maxOccupied
                )
            }
        }
    }

    private fun getSeatValueByRule(
        position: Point,
        seats: List<List<Char>>,
        seatWidth: Int,
        getAdjacent: (Point, List<List<Char>>, Int) -> List<Char>,
        maxOccupied: Int
    ) = when (getPositionValue(position, seats)) {
        floor -> floor
        empty -> simulateAdjacentSeatsEmpty(position, seats, seatWidth, getAdjacent)
        occupied ->
            simulateSetEmptyWhileToManyOccupied(position, seats, seatWidth, maxOccupied, getAdjacent)
        else -> unreachable()
    }

    private fun simulateAdjacentSeatsEmpty(
        position: Point,
        seats: List<List<Char>>,
        seatWidth: Int,
        getAdjacent: (Point, List<List<Char>>, Int) -> List<Char>
    ): Char {
        return if (getAdjacent(position, seats, seatWidth)
                .all { adjacent -> adjacent == empty || adjacent == floor || adjacent == undefined }
        ) occupied
        else empty

    }

    private fun simulateSetEmptyWhileToManyOccupied(
        position: Point,
        seats: List<List<Char>>,
        seatWidth: Int,
        maxOccupied: Int,
        getAdjacent: (Point, List<List<Char>>, Int) -> List<Char>
    ): Char {
        return if (getAdjacent(position, seats, seatWidth)
                .count { adjacent -> adjacent == occupied } > maxOccupied
        ) empty
        else occupied
    }

    private fun getAdjacent(position: Point, seats: List<List<Char>>, seatWidth: Int): List<Char> {
        return getAdjacentPoints.map { point -> point + position }
            .filter { point -> isInRange(point, seatWidth, seats) }
            .map { index -> getPositionValue(index, seats) }
    }

    private fun getFirstSeenSeat(position: Point, seats: List<List<Char>>, seatWidth: Int): List<Char> {
        return getAdjacentPoints.map { direction -> getFirstSeenSeat(position, seats, seatWidth, direction) }
    }

    private fun getFirstSeenSeat(position: Point, seats: List<List<Char>>, seatWidth: Int, direction: Point): Char {
        val newPosition = position + direction
        return if (isInRange(newPosition, seatWidth, seats))
            getFirstSeenSeatValue(newPosition, seats, seatWidth, direction)
        else undefined
    }

    private fun getFirstSeenSeatValue(
        newPosition: Point,
        seats: List<List<Char>>,
        seatWidth: Int,
        direction: Point
    ): Char {
        val value = getPositionValue(newPosition, seats)
        return if (value == floor) getFirstSeenSeat(newPosition, seats, seatWidth, direction)
        else value
    }

    private fun isInRange(
        position: Point,
        seatWidth: Int,
        seats: List<List<Char>>
    ) = position.x in 0 until seatWidth && position.y in 0 until seats.count()

    private fun getPositionValue(position: Point, seats: List<List<Char>>): Char {
        return seats[position.y][position.x]
    }
}