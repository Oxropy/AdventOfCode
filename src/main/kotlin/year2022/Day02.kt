package year2022

import AoCApp

object Day02 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputLines)
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(rounds: List<Pair<String, String>>): String {
        return rounds.map { value -> Pair(getShape(value.first), getShape(value.second)) }
            .sumOf { round -> play(round.first, round.second).value + round.second.value }.toString()
    }

    private fun part2(rounds: List<Pair<String, String>>): String {
        return rounds.map { value ->
            val enemyShape = getShape(value.first)
            Pair(enemyShape, getMatchingShape(enemyShape, getHowToEnd(value.second)))
        }
            .sumOf { round -> play(round.first, round.second).value + round.second.value }.toString()
    }

    private fun processInput(inputLines: List<String>): List<Pair<String, String>> {
        return inputLines.map { line -> line.split(" ").let { Pair(it[0], it[1]) } }
    }

    enum class Shape(val value: Int) {
        Rock(1),
        Paper(2),
        Scissor(3)
    }

    enum class HowToEnd(val value: Int) {
        Win(6),
        Draw(3),
        Lose(0)
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

    private fun getHowToEnd(value: String): HowToEnd {
        return when (value) {
            "X" -> HowToEnd.Lose
            "Y" -> HowToEnd.Draw
            "Z" -> HowToEnd.Win
            else -> unreachable()
        }
    }

    private fun getMatchingShape(enemy: Shape, howToEnd: HowToEnd): Shape {
        return when (enemy) {
            Shape.Rock -> when (howToEnd) {
                HowToEnd.Lose -> Shape.Scissor
                HowToEnd.Draw -> Shape.Rock
                HowToEnd.Win -> Shape.Paper
            }

            Shape.Paper -> when (howToEnd) {
                HowToEnd.Lose -> Shape.Rock
                HowToEnd.Draw -> Shape.Paper
                HowToEnd.Win -> Shape.Scissor
            }

            Shape.Scissor -> when (howToEnd) {
                HowToEnd.Lose -> Shape.Paper
                HowToEnd.Draw -> Shape.Scissor
                HowToEnd.Win -> Shape.Rock
            }
        }
    }

    private fun play(enemy: Shape, player: Shape): HowToEnd {
        return when (enemy) {
            Shape.Rock -> when (player) {
                Shape.Rock -> HowToEnd.Draw
                Shape.Paper -> HowToEnd.Win
                Shape.Scissor -> HowToEnd.Lose
            }

            Shape.Paper -> when (player) {
                Shape.Rock -> HowToEnd.Lose
                Shape.Paper -> HowToEnd.Draw
                Shape.Scissor -> HowToEnd.Win
            }

            Shape.Scissor -> when (player) {
                Shape.Rock -> HowToEnd.Win
                Shape.Paper -> HowToEnd.Lose
                Shape.Scissor -> HowToEnd.Draw
            }
        }
    }
}