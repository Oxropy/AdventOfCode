package year2023

import AoCApp

object Day03: AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = inputLines
//        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<String>): String {
        val partNumbers = mutableListOf<Int>()
        for (y in input.indices) {
            var currentNumber = ""
            var hasAdjacentSymbol = false
            for (x in input[y].indices) {
                val currentChar = input[y][x]
                if (currentChar.isDigit()) {
                    currentNumber += currentChar
                    if (isAdjacentASymbol(input, Point(x, y))) hasAdjacentSymbol = true
                }
                else {
                    if (hasAdjacentSymbol && currentNumber.isNotEmpty())
                        partNumbers.add(currentNumber.toInt())
                    currentNumber = ""
                    hasAdjacentSymbol = false
                }
            }
            if (hasAdjacentSymbol && currentNumber.isNotEmpty())
                partNumbers.add(currentNumber.toInt())
        }

        return partNumbers.sum().toString()
    }

    private fun part2(input: List<String>): String {
        val ratio = mutableListOf<Int>()
        for (y in input.indices) {
            for (x in input[y].indices) {
                val currentChar = input[y][x]
                if (currentChar == '*') {
                    val adjacentNumbers = getAdjacentNumbers(input, Point(x, y))
                    if (adjacentNumbers.size == 2)
                        ratio.add(adjacentNumbers[0] * adjacentNumbers[1])
                }
            }
        }

        return ratio.sum().toString()
    }

    private fun isAdjacentASymbol(input: List<String>, point: Point): Boolean {
        return enumValues<Direction>().any {
            val directionPoint = point + it.vector
            if (!directionPoint.isNegative() && directionPoint.y < input.size && directionPoint.x < input[directionPoint.y].length) {
                val directionChar = input[directionPoint.y][directionPoint.x]
                if (directionChar != '.' && !directionChar.isDigit()) {
                    return@any true
                }
            }

            return@any false
        }
    }

    private fun getAdjacentNumbers(input: List<String>, point: Point): List<Int> {
        val numbers = mutableListOf<Int>()
        val leftChar = getCharFromPoint(input, Direction.LEFT.vector + point)
        if (leftChar.isDigit()) {
            val number = "" + getLeftDigits(input, point) + leftChar
            numbers.add(number.toInt())
        }

        val rightChar = getCharFromPoint(input, Direction.RIGHT.vector + point)
        if (rightChar.isDigit()) {
            val number = "" + rightChar + getRightDigits(input, point)
            numbers.add(number.toInt())
        }

        numbers.addAll(getNumbers(input, point, Direction.UPLEFT, Direction.UP, Direction.UPRIGHT))
        numbers.addAll(getNumbers(input, point, Direction.DOWNLEFT, Direction.DOWN, Direction.DOWNRIGHT))

        return numbers
    }

    private fun getNumbers(input: List<String>, point: Point, left: Direction, middle: Direction, right: Direction): List<Int> {
        val numbers = mutableListOf<Int>()
        var leftSide = ""
        var rightSide = ""

        val rightPoint = right.vector + point
        val rightChar = getCharFromPoint(input, rightPoint)
        if (rightChar.isDigit()){
            rightSide += rightChar + getRightDigits(input, rightPoint)
        }

        val leftPoint = left.vector + point
        val leftChar = getCharFromPoint(input, leftPoint)
        if (leftChar.isDigit()){
            leftSide += getLeftDigits(input, leftPoint) + leftChar
        }

        val middleChar = getCharFromPoint(input, middle.vector + point)
        if (middleChar.isDigit()){
            numbers.add((leftSide + middleChar + rightSide).toInt())
        }
        else {
            if (leftSide.isNotEmpty()) {
                numbers.add(leftSide.toInt())
            }
            if (rightSide.isNotEmpty()) {
                numbers.add(rightSide.toInt())
            }
        }

        return numbers
    }

    private fun getCharFromPoint(input: List<String>, point: Point): Char {
        return input[point.y][point.x]
    }

    private fun getLeftDigits(input: List<String>, point: Point): String {
        var number = ""
        var currentPoint = point
        while (true){
            val leftPoint = Direction.LEFT.vector + currentPoint
            if (leftPoint.isNegative())
                return number

            val char = input[leftPoint.y][leftPoint.x]
            if (char.isDigit()) {
                number = char + number
                        currentPoint = leftPoint
            }
            else
                return number
        }
    }

    private fun getRightDigits(input: List<String>, point: Point): String {
        var number = ""
        var currentPoint = point
        while (true){
            val rightPoint = Direction.RIGHT.vector + currentPoint
            if (rightPoint.x >= input[rightPoint.y].length)
                return number

            val char = input[rightPoint.y][rightPoint.x]
            if (char.isDigit()) {
                number += char
                currentPoint = rightPoint
            }
            else
                return number
        }
    }
}