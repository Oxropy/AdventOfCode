package year2020

import AoCApp

sealed class BusId {
    data class Bus(val id: Int, val minutesAfterTimestamp: Int) : BusId()
    data class UnConstraint(val minutesAfterTimestamp: Int) : BusId()
}

object Day13 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val lines = inputLines
        val departTime = lines[0].toInt()
        val busLines = parseBusLines(lines[1]).filterIsInstance<BusId.Bus>()

        printPart(1, part1(departTime, busLines))
        printPart(2, part2(busLines))
    }

    private fun parseBusLines(line: String): List<BusId> {
        return line.split(",")
            .mapIndexed { index, busLine ->
                when (busLine) {
                    "x" -> BusId.UnConstraint(index)
                    else -> BusId.Bus(busLine.toInt(), index)
                }
            }
    }

    private fun part1(departTime: Int, busLines: List<BusId.Bus>): String {
        val busLineAndTime =
            busLines.map { bus -> Pair(bus.id, ((departTime / bus.id) + 1) * bus.id - departTime) }
                .minByOrNull { it.second }
        return (busLineAndTime!!.first * busLineAndTime.second).toString()
    }

    private fun part2(busLines: List<BusId.Bus>): String {
        val timestamp = 100000000000000L
//        val timestamp = busLines[0].id.toLong()
        return findEarliestTimestamp(busLines, timestamp, busLines.count(),  0).toString()
    }

    private tailrec fun findEarliestTimestamp(
        busLines: List<BusId.Bus>, timestamp: Long, busLineCount: Int, index: Int
    ): Long {
        val busLine = busLines[index]
        val normalizedTimestamp = timestamp + busLine.minutesAfterTimestamp
        var newTimestamp = (normalizedTimestamp / busLine.id) * busLine.id
        if (newTimestamp < normalizedTimestamp) {
           newTimestamp += busLine.id
        }
        if (newTimestamp == normalizedTimestamp || index == 0) {
            newTimestamp -= busLine.minutesAfterTimestamp
            val newIndex = index + 1
            if (newIndex == busLineCount) {
                return timestamp
            }

            return findEarliestTimestamp(busLines, newTimestamp, busLineCount, newIndex)
        }

        return findEarliestTimestamp(busLines, newTimestamp, busLineCount, 0)
    }
}