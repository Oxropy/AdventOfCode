package year2022

import AoCApp

object Day02 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(rounds: List<Pair<Shape, Shape>>): String {
       return rounds.sumOf { round -> play(round.first, round.second) + round.second.value }.toString()
    }

    private fun part2(rounds: List<Pair<Shape, Shape>>): String {
        TODO("Not yet implemented")
    }

    private fun processInput(inputLines: List<String>): List<Pair<Shape, Shape>> {
        return inputLines.map { line -> line.split(" ").map { value -> getShape(value) }.let { Pair(it[0], it[1]) } }
    }

    enum class Shape(val value: Int) {
        Rock(1),
        Paper(2),
        Scissor(3)
    }

    private fun getShape(value: String): Shape {
        return when (value) {
            "A" -> Shape.Rock
            "B" -> Shape.Paper
            "C" -> Shape.Scissor
            "X" -> Shape.Rock
            "Y" -> Shape.Paper
            "Z" -> Shape.Scissor
            else -> unreachable()
        }
    }

    private fun play(enemy: Shape, player: Shape): Int {
        return when (enemy){
            Shape.Rock -> when (player){
                Shape.Rock -> 3
                Shape.Paper -> 6
                Shape.Scissor -> 0
            }
            Shape.Paper -> when (player){
                Shape.Rock -> 0
                Shape.Paper -> 3
                Shape.Scissor -> 6
            }
            Shape.Scissor -> when (player){
                Shape.Rock -> 6
                Shape.Paper -> 0
                Shape.Scissor -> 3
            }
        }
    }
}