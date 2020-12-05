package year2020

import AoCApp

object Day02 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        printPart(1, part1(inputLines))
        printPart(2, part2(inputLines))
    }

    private fun part1(lines: List<String>): String {
        return countValidPasswords(lines) { min, max, char, pw ->
            pw.count { it == char } in min..max
        }
    }

    private fun part2(lines: List<String>): String {
        return countValidPasswords(lines) { min, max, char, pw ->
            (pw[min - 1] == char) xor (pw[max - 1] == char)
        }
    }

    private fun countValidPasswords(lines: List<String>, block: (Int, Int, Char, String) -> Boolean): String {
        val regex = Regex("""(\d+)-(\d+) (\w): (.*)""")

        return lines.count { line ->
            regex.find(line)?.let {
                val (mi, ma, char, pw) = it.destructured
                val min = mi.toInt()
                val max = ma.toInt()
                block(min, max, char[0], pw)
            } ?: false
        }.toString()
    }
}