import year2020.Day11
import java.nio.charset.StandardCharsets.UTF_8

abstract class AoCApp {

    val input: String
        get() = String(javaClass.getResourceAsStream("${javaClass.simpleName}.txt").readBytes(), UTF_8)

    val inputLines: List<String>
        get() = input.split("\n").map { it.trim() }

    val inputGroupedLines: List<List<String>>
        get() = input.split("\r\n\r\n").map { group -> group.split("\n").map { it.trim() } }

    fun printPart(part: Int, result: String) {
        println("Part $part:  $result")
    }

    operator fun Point.plus(o: Point): Point {
        return Point(x + o.x, y + o.y)
    }

    operator fun Point.times(n: Int): Point {
        return Point(x * n, y * n)
    }

    data class Point(val x: Int, val y: Int)

    companion object {
        fun unreachable(): Nothing = throw Exception("Should not be reachable!")
    }
}