import java.io.IOException

abstract class AoCApp {

    val input: String
        get() = javaClass.getResourceAsStream("${javaClass.simpleName}.txt")?.readBytes()?.let { String(it, Charsets.UTF_8) } ?: throw IOException("File not found!")

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

    operator fun Point.minus(o: Point): Point {
        return Point(x - o.x, y - o.y)
    }

    operator fun Point.times(n: Int): Point {
        return Point(x * n, y * n)
    }

    operator fun Point.plus(dir: Direction) = this + dir.vector

    data class Point(val x: Int, val y: Int){
        fun setX(x: Int): Point {
            return Point(x, y)
        }

        fun setY(y: Int): Point {
            return Point(x, y)
        }

        fun isNegative(): Boolean {
            return x < 0 || y < 0
        }
    }

    companion object {
        fun unreachable(): Nothing = throw Exception("Should not be reachable!")
    }

    enum class Direction(val vector: Point) {
        UPLEFT(Point(-1, 1)),
        UP(Point(0, 1)),
        UPRIGHT(Point(1, 1)),
        RIGHT(Point(1, 0)),
        DOWNRIGHT(Point(1, -1)),
        DOWN(Point(0, -1)),
        DOWNLEFT(Point(-1, -1)),
        LEFT(Point(-1, 0));

        fun opposite() = when (this) {
            UPLEFT -> DOWNRIGHT
            UP -> DOWN
            UPRIGHT -> DOWNLEFT
            RIGHT -> LEFT
            DOWNRIGHT -> UPLEFT
            DOWN -> UP
            DOWNLEFT -> UPRIGHT
            LEFT -> RIGHT
        }
    }

}