import java.nio.charset.StandardCharsets.UTF_8

abstract class AoCApp {
    val input: String
        get() = String(javaClass.getResourceAsStream("${javaClass.simpleName}.txt").readBytes(), UTF_8)

    val inputLines: List<String>
        get() = input.split("\n").map { it.trim() }

    fun printPart(part: Int, result: String) {
        println("Part $part:  $result")
    }
}