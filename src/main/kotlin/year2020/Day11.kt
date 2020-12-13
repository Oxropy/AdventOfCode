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
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(seats: List<List<Char>>): String {
        return simulate(seats, 3, ::getAdjacent).toString()
    }

    private fun part2(seats: List<List<Char>>): String {
        return simulate(seats, 4, ::getFirstSeenSeat).toString()
    }

    private fun processInput(lines: List<String>): List<List<Char>> {
        return lines.map { line -> line.toCharArray().toList() }
    }

    private fun simulate(
        seats: List<List<Char>>,
        maxOccupied: Int,
        getAdjacent: (Point, List<List<Char>>, Int) -> List<Char>
    ): Int {
        val seatWidth = seats[0].count()
        var oldList = listOf<List<Char>>()
        var newList = seats

        printSeats(newList)
        while (oldList != newList) {
            oldList = newList
            newList = simulate(oldList, seatWidth, maxOccupied, getAdjacent)
            printSeats(newList)
        }

        return newList.sumOf { line -> line.count { it == occupied } }
    }

    private fun simulate(
        seats: List<List<Char>>,
        seatWidth: Int,
        maxOccupied: Int,
        getAdjacent: (Point, List<List<Char>>, Int) -> List<Char>
    ): List<List<Char>> {
        val newSeats = mutableListOf<MutableList<Char>>()
        for (y in seats.indices) {
            val newSeatLine = mutableListOf<Char>()
            for (x in 0 until seatWidth) {
                val position = Point(x, y)
                when (getPositionValue(position, seats)) {
                    floor -> floor
                    empty -> simulateAdjacentSeatsEmpty(position, seats, seatWidth, getAdjacent)
                    occupied -> simulateSetEmptyWhileToManyOccupied(
                        position,
                        seats,
                        seatWidth,
                        maxOccupied,
                        getAdjacent
                    )
                    else -> unreachable()
                }.let { newSeatLine.add(it) }
            }
            newSeats.add(newSeatLine)
        }

        return newSeats.toList()
    }

    private fun simulateAdjacentSeatsEmpty(
        position: Point,
        seats: List<List<Char>>,
        seatWidth: Int,
        getAdjacent: (Point, List<List<Char>>, Int) -> List<Char>
    ): Char {
        return if (areAdjacentSeatsEmpty(position, seats, seatWidth, getAdjacent)) {
            occupied
        } else {
            empty
        }
    }

    private fun simulateSetEmptyWhileToManyOccupied(
        position: Point,
        seats: List<List<Char>>,
        seatWidth: Int,
        maxOccupied: Int,
        getAdjacent: (Point, List<List<Char>>, Int) -> List<Char>
    ): Char {
        return if (areToManyAdjacentSeatsOccupied(position, seats, seatWidth, maxOccupied, getAdjacent)) {
            empty
        } else {
            occupied
        }
    }

    private fun areAdjacentSeatsEmpty(
        position: Point, seats: List<List<Char>>, seatWidth: Int,
        getAdjacent: (Point, List<List<Char>>, Int) -> List<Char>
    ): Boolean {
        return getAdjacent(position, seats, seatWidth).all { adjacent -> adjacent == empty || adjacent == floor }
    }

    private fun areToManyAdjacentSeatsOccupied(
        position: Point,
        seats: List<List<Char>>,
        seatWidth: Int,
        maxOccupied: Int,
        getAdjacent: (Point, List<List<Char>>, Int) -> List<Char>
    ): Boolean {
        return getAdjacent(position, seats, seatWidth).count { adjacent -> adjacent == occupied } > maxOccupied
    }

    private fun getAdjacent(position: Point, seats: List<List<Char>>, seatWidth: Int): List<Char> {
        return getAdjacentPoints.map { point -> point + position }
            .filter { point -> point.x in 0 until seatWidth && point.y in 0 until seats.count() }
            .map { index -> getPositionValue(index, seats) }
    }

    private fun getFirstSeenSeat(position: Point, seats: List<List<Char>>, seatWidth: Int): List<Char> {
        return getAdjacentPoints.map { direction -> getFirstSeenSeat(position, seats, seatWidth, direction) }
            .filter { seat -> seat != undefined }
    }

    private fun getFirstSeenSeat(position: Point, seats: List<List<Char>>, seatWidth: Int, direction: Point): Char {
        val newPosition = position + direction
        return if (newPosition.x in 0 until seatWidth && newPosition.y in 0 until seats.count()) {
            val value = getPositionValue(newPosition, seats)
            if (value == floor) {
                getFirstSeenSeat(newPosition, seats, seatWidth, direction)
            } else {
                value
            }
        } else {
            undefined
        }
    }

    private fun getPositionValue(position: Point, seats: List<List<Char>>): Char {
        return seats[position.y][position.x]
    }

    data class Point(val x: Int, val y: Int)

    operator fun Point.plus(o: Point): Point {
        return Point(x + o.x, y + o.y)
    }

    private fun printSeats(seats: List<List<Char>>) {
        for (seatLine in seats) {
            println(seatLine.toCharArray())
        }
        println()
    }
}