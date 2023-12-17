package year2023

import AoCApp

object Day10 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = inputLines
        printPart(1, part1(input))
//        printPart(2, part2(input))
    }

    private fun part1(input: List<String>): String {
        val pipes = parseInput(input)
        val startPoint = getStartPoint(pipes)
        return (getPipeLoop(pipes, startPoint).size / 2).toString()
    }

    private fun part2(input: List<String>): String {
        TODO("Not yet implemented")
    }

    private fun getPipeLoop(pipes: List<List<Tile>>, startPoint: Point): List<Tile> {
        val routeStart = listOf(pipes[startPoint.y][startPoint.x])
        for (direction in listOf(Direction.DOWN, Direction.RIGHT, Direction.UP, Direction.LEFT)) {
            val loop = findLoop(pipes, startPoint + direction.vector, direction, routeStart)
            if (loop.isNotEmpty()) {
                return loop
            }
        }

        return emptyList()
    }

    private tailrec fun findLoop(
        pipes: List<List<Tile>>,
        point: Point,
        direction: Direction,
        route: List<Tile>
    ): List<Tile> = when (val currentTile = pipes[point.y][point.x]) {
        is Tile.Start ->
            route

        is Tile.Ground ->
            emptyList()

        is Tile.Pipe -> {
            val nextDirection = currentTile.style.outputFor(direction.opposite())
            if (nextDirection != null) {
                findLoop(
                    pipes,
                    point + nextDirection,
                    nextDirection,
                    route + currentTile
                )
            } else {
                emptyList()
            }
        }
    }

    private fun parseInput(input: List<String>): List<List<Tile>> = input.map { line ->
        line.map {
            when (it) {
                '|' -> Tile.Pipe(PipeStyle.Vertical)
                '-' -> Tile.Pipe(PipeStyle.Horizontal)
                'L' -> Tile.Pipe(PipeStyle.NorthAndEast)
                'J' -> Tile.Pipe(PipeStyle.NorthAndWest)
                '7' -> Tile.Pipe(PipeStyle.SouthAndWest)
                'F' -> Tile.Pipe(PipeStyle.SouthAndEast)
                '.' -> Tile.Ground
                'S' -> Tile.Start
                else -> unreachable()
            }
        }
    }


    private fun getStartPoint(pipes: List<List<Tile>>): Point {
        for (y in pipes.indices) {
            for (x in pipes[y].indices) {
                if (pipes[y][x] == Tile.Start) {
                    return Point(x, y)
                }
            }
        }

        unreachable()
    }

    enum class PipeStyle(
        val input1: Direction,
        val input2: Direction,
    ) {
        Vertical(Direction.DOWN, Direction.UP),
        Horizontal(Direction.LEFT, Direction.RIGHT),
        NorthAndEast(Direction.DOWN, Direction.RIGHT),
        NorthAndWest(Direction.DOWN, Direction.LEFT),
        SouthAndEast(Direction.UP, Direction.RIGHT),
        SouthAndWest(Direction.UP, Direction.LEFT);

        fun outputFor(direction: Direction): Direction? = when {
            input1 == direction -> input2
            input2 == direction -> input1
            else -> null
        }
    }

    sealed interface Tile {
        data object Start : Tile
        data object Ground : Tile
        data class Pipe(val style: PipeStyle) : Tile
    }
}