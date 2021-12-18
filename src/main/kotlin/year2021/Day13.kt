package year2021

import AoCApp

object Day13 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputGroupedLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: Pair<List<Point>, List<FoldAlong>>): String {
        val (points, foldAlong) = input
        return foldAlong(points, foldAlong[0]).size.toString()
    }

    private fun part2(input: Pair<List<Point>, List<FoldAlong>>): String {
        val (points, foldAlong) = input
        val result = foldAlong.fold(points) { p, fold -> foldAlong(p, fold) }
        printFold(result, FoldAlong.NoFold)
        return ""
    }

    private fun processInput(inputLines: List<List<String>>): Pair<List<Point>, List<FoldAlong>> {
        val points = inputLines[0].map { line -> line.split(',').map { it.toInt() }.let { (x, y) -> Point(x, y) } }
        val folds = inputLines[1].map {
            it.substring("fold along ".length).split('=')
                .let { (orientation, position) -> getFoldAlong(orientation, position.toInt()) }
        }

        return Pair(points, folds)
    }

    private fun getFoldAlong(orientation: String, position: Int): FoldAlong {
        return when (orientation) {
            "y" -> FoldAlong.AlongY(position)
            "x" -> FoldAlong.AlongX(position)
            else -> unreachable()
        }
    }

    sealed class FoldAlong {
        class AlongX(val column: Int) : FoldAlong()
        class AlongY(val row: Int) : FoldAlong()
        object NoFold : FoldAlong()
    }

    private fun foldAlong(points: List<Point>, fold: FoldAlong): List<Point> {
        return when (fold) {
            is FoldAlong.AlongX -> {
                foldAlongX(points, fold.column)
            }
            is FoldAlong.AlongY -> {
                foldAlongY(points, fold.row)
            }
            is FoldAlong.NoFold -> {
                points
            }
        }
    }

    private fun foldAlongX(points: List<Point>, column: Int): List<Point> {
        return points.partition { it.x > column }.let { (rightSide, leftSide) ->
            val foldedPoints = rightSide.map { Point(it.x - ((it.x - column) * 2), it.y) }
            (leftSide + foldedPoints).distinct()
        }
    }

    private fun foldAlongY(points: List<Point>, row: Int): List<Point> {
        return points.partition { it.y > row }.let { (downSide, upSide) ->
            val foldedPoints = downSide.map { Point(it.x, it.y - ((it.y - row) * 2)) }
            (upSide + foldedPoints).distinct()
        }
    }

    private fun printFold(points: List<Point>, fold: FoldAlong) {
        val xSize = points.maxOf { it.x }
        val ySize = points.maxOf { it.y }

        for (y in 0..ySize) {
            if (fold is FoldAlong.AlongY && fold.row == y) {
                println("-".repeat(xSize + 1))
                continue
            }

            for (x in 0..xSize) {
                if (fold is FoldAlong.AlongX && fold.column == x) {
                    print("|")
                    continue
                }

                if (points.contains(Point(x, y))) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
        println()
    }
}