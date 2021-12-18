package year2021

import AoCApp

object Day04 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = processInput(inputGroupedLines)
        printPart(1, part1(input.first, input.second))
        printPart(2, part2(input.first, input.second))
    }

    private fun processInput(inputLines: List<List<String>>): Pair<List<Int>, List<List<List<BingoNumber>>>> {
        val numbers = inputLines[0].flatMap { line -> line.split(',').map { it.toInt() } }
        val boards = inputLines.drop(1).map { group ->
            group.map { row ->
                row.split(" ").filter { it.isNotBlank() }.map { BingoNumber(it.toInt(), false) }
            }
        }
        return Pair(numbers, boards)
    }

    private fun part1(numbers: List<Int>, boards: List<List<List<BingoNumber>>>): String {
        for (number in numbers) {
            setNumberInBoards(boards, number)

            val (isBingo, values) = isBingoBoards(boards)
            if (isBingo) {
                return (values.sum() * number).toString()
            }
        }

        return ""
    }

    private fun part2(numbers: List<Int>, boards: List<List<List<BingoNumber>>>): String {

        var activeBoards = boards
        for (number in numbers) {
            setNumberInBoards(activeBoards, number)

            val newActiveBoards = activeBoards.filter { !isBingoBoard(it).first }

            if (newActiveBoards.isEmpty()) {
                return (activeBoards[0].flatten().filter { !it.isDrawn }
                    .sumOf { value -> value.number } * number).toString()
            }

            activeBoards = newActiveBoards
        }

        return ""
    }

    private fun setNumberInBoards(
        boards: List<List<List<BingoNumber>>>,
        number: Int
    ) {
        boards.forEach { board ->
            board.forEach { value ->
                value.forEach { item ->
                    if (item.number == number) {
                        item.isDrawn = true
                    }
                }
            }
        }
    }

    private fun isBingoBoards(boards: List<List<List<BingoNumber>>>): Pair<Boolean, List<Int>> {
        for (board in boards) {
            val bingoBoard = isBingoBoard(board)
            if (bingoBoard.first) {
                return bingoBoard
            }
        }

        return Pair(false, emptyList())
    }

    private fun isBingoBoard(board: List<List<BingoNumber>>): Pair<Boolean, List<Int>> {
        val result = isBingoRows(board)
        if (result.first) {
            return result
        }

        return isBingoColumns(board)
    }

    private fun isBingoRows(board: List<List<BingoNumber>>): Pair<Boolean, List<Int>> {
        for (row in board) {
            if (isBingo(row)) {
                val unmarkedNumbers = board.flatten().filter { !it.isDrawn }.map { value -> value.number }
                return Pair(true, unmarkedNumbers)
            }
        }

        return Pair(false, emptyList())
    }


    private fun isBingoColumns(board: List<List<BingoNumber>>): Pair<Boolean, List<Int>> {
        for (i in 0 until board[0].size) {
            val col = board.map { it[i] }
            if (isBingo(col)) {
                val unmarkedNumbers = board.flatten().filter { !it.isDrawn }.map { value -> value.number }
                return Pair(true, unmarkedNumbers)
            }
        }

        return Pair(false, emptyList())
    }

    private fun isBingo(line: List<BingoNumber>): Boolean {
        return line.all { it.isDrawn }
    }

    private class BingoNumber(val number: Int, var isDrawn: Boolean)
}