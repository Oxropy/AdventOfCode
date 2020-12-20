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
        val highestBus = busLines.maxByOrNull { bus -> bus.id }!!
        val timestamp = (100000000000000L / highestBus.id) * highestBus.id
//        val timestamp = highestBus.id.toLong()
        return findEarliestTimestamp(busLines, timestamp, highestBus).toString()
    }

    private tailrec fun findEarliestTimestamp(busLines: List<BusId.Bus>, timestampAfterLast: Long, highestBus: BusId.Bus): Long {
        val timestamp = timestampAfterLast - highestBus.minutesAfterTimestamp
        if (checkBusLinesOnTimestamp(busLines, timestamp, 0)) {
            return timestamp
        }

        return findEarliestTimestamp(busLines, timestampAfterLast + highestBus.id, highestBus)
    }

    private tailrec fun checkBusLinesOnTimestamp(busLines: List<BusId.Bus>, timestamp: Long, index: Int): Boolean {
        if (index >= busLines.count()) {
            return true
        }

        val (id, minutesAfterTimestamp) = busLines[index]
        if ((timestamp + minutesAfterTimestamp) % id != 0L) {
            return false
        }

        return checkBusLinesOnTimestamp(busLines, timestamp, index + 1)
    }
}