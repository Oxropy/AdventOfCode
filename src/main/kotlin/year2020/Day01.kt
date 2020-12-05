package year2020

import AoCApp

object Day01: AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        printPart(1, part1(inputLines))
        printPart(2, part2(inputLines))
    }

    private fun part1(lines: List<String>): String {
        for (i in lines.indices) {
            for (k in lines.indices) {

                val inp1 = lines[i]
                val inp2 = lines[k]
                if (inp1 != inp2) {
                    val val1 = inp1.toInt()
                    val val2 = inp2.toInt()
                    if (val1 + val2 == 2020) {
                        return (val1 * val2).toString()
                    }
                }
            }
        }
        return ""
    }

    private fun part2(lines: List<String>): String {
        for (i in lines.indices) {
            for (k in lines.indices) {
                for (l in lines.indices) {
                    val inp1 = lines[i]
                    val inp2 = lines[k]
                    val inp3 = lines[l]
                    if (inp1 != inp2 && inp2 != inp3) {
                        val val1 = inp1.toInt()
                        val val2 = inp2.toInt()
                        val val3 = inp3.toInt()
                        if (val1 + val2 + val3 == 2020) {
                            return (val1 * val2 * val3).toString()
                        }
                    }
                }
            }
        }
        return ""
    }
}