package year2024

import AoCApp

object Day03: AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = inputLines
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<String>): String {
        val regex = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")

        return input.sumOf { line ->
            regex.findAll(line).let { finding ->
                finding.sumOf {
                    val (multiplicand, multiplier) = it.destructured
                    multiplicand.toInt() * multiplier.toInt()
                }
            }
        }.toString()
    }

    private fun part2(input: List<String>): String {
        val lineRegex = Regex("""(^|do\(\)|don't\(\))(.*?)(?=do\(\)|don't\(\)|$)""")
        val regex = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")

        return input.sumOf { line ->
            lineRegex.findAll(line).let { finding ->
                finding.sumOf { a ->
                    val (status, values) = a.destructured
                    if (status == "don't()"){
                        0
                    } else{
                        regex.findAll(values).let { mul ->
                            mul.sumOf {
                                val (multiplicand, multiplier) = it.destructured
                                multiplicand.toInt() * multiplier.toInt()
                            }
                        }
                    }
                }
            }
        }.toString()
    }
}