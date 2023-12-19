package year2023

import AoCApp

object Day10 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = inputLines
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<String>): String {
        val tiles = parseInput(input)
        return (getPipeLoop(tiles).size / 2).toString()
    }

    private fun part2(input: List<String>): String {
        val tiles = parseInput(input)
        val route = getPipeLoop(tiles)
        val startPipeStyle = getStartPipeStyle(route)

        val top = route.minOf { it.y }
        val left = route.minOf { it.x }

        val bottom = route.maxOf { it.y }
        val right = route.maxOf { it.x }

        val correctedTiles = tiles.map { rows -> rows.map { if (it is Tile.Start) Tile.Pipe(startPipeStyle) else it } }

        var enclosedCount = 0
        for (y in top..bottom) {
            for (x in left..right) {
                if (Point(x, y) in route) {
                    continue
                }
                fun countIntersections(xCoords: Iterable<Int>): Int {
                    return xCoords
                        .map { Point(it, y) }
                        .filter { it in route }
                        .map { correctedTiles[it.y][it.x] }
                        .count { it is Tile.Pipe && it.style.isVertical() && it.style !in setOf(PipeStyle.SouthAndEast, PipeStyle.SouthAndWest) }
                }
                val toLeft = countIntersections(left..<x)

                if (toLeft % 2 == 1) {
                    enclosedCount++
                }
            }
        }

        return enclosedCount.toString()
    }

    private fun getPipeLoop(tiles: List<List<Tile>>): List<Point> {
        val startPoint = getStartPoint(tiles)
        for (direction in listOf(Direction.DOWN, Direction.RIGHT, Direction.UP, Direction.LEFT)) {
            val loop = findLoop(tiles, startPoint + direction.vector, direction, emptyList())
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
        route: List<Point>
    ): List<Point> {
        if (point.x < 0 || point.y < 0 || point.x >= pipes[0].size || point.y >= pipes.size) {
            return emptyList()
        }
        return when (val currentTile = pipes[point.y][point.x]) {
            is Tile.Start ->
                route + point

            is Tile.Ground ->
                emptyList()

            is Tile.Pipe -> {
                val nextDirection = currentTile.style.outputFor(direction.opposite())
                if (nextDirection != null) {
                    val nextPoint = point + nextDirection
                    findLoop(
                        pipes,
                        nextPoint,
                        nextDirection,
                        route + point,
                    )
                } else {
                    emptyList()
                }
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

    private fun getStartPipeStyle(route: List<Point>): PipeStyle {
        val start = route[route.size - 1]
        val before = route[route.size - 2]
        val after = route[0]

        fun f(point1: Point, point2: Point): PipeStyle? {

            val firstDirection = point1 - start
            val lastDirection = point2 - start

            return when {
                firstDirection.x == 0 && firstDirection.y == 1 && lastDirection.x == 0 && lastDirection.y == -1 -> PipeStyle.Vertical
                firstDirection.x == 1 && firstDirection.y == 0 && lastDirection.x == -1 && lastDirection.y == 0 -> PipeStyle.Horizontal
                firstDirection.x == 0 && firstDirection.y == -1 && lastDirection.x == -1 && lastDirection.y == 0 -> PipeStyle.NorthAndWest
                firstDirection.x == 0 && firstDirection.y == -1 && lastDirection.x == 1 && lastDirection.y == 0 -> PipeStyle.NorthAndEast
                firstDirection.x == 0 && firstDirection.y == 1 && lastDirection.x == 1 && lastDirection.y == 0 -> PipeStyle.SouthAndEast
                firstDirection.x == 0 && firstDirection.y == 1 && lastDirection.x == -1 && lastDirection.y == 0 -> PipeStyle.SouthAndWest
                else -> null
            }
        }

        return f(before, after)
            ?: f(after, before)
            ?: unreachable()
    }

    enum class PipeStyle(
        private val input1: Direction,
        private val input2: Direction,
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

        fun isVertical() = when (this) {
            Vertical -> true
            Horizontal -> false
            NorthAndEast -> true
            NorthAndWest -> true
            SouthAndEast -> true
            SouthAndWest -> true
        }
    }

    sealed interface Tile {
        data object Start : Tile
        data object Ground : Tile
        data class Pipe(val style: PipeStyle) : Tile
    }
}