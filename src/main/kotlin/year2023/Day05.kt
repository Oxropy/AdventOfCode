package year2023

import AoCApp

object Day05 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = inputGroupedLines
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<List<String>>): String {
        val seeds = input[0][0].split(" ").drop(1).map { it.toLong() }.toSet()
        val almanac = getAlmanac(input)

        return seeds.minOf { seed -> seedToLocation(seed, almanac) }.toString()
    }

    private fun part2(input: List<List<String>>): String {
        val seedRanges = input[0][0].split(" ").drop(1).map { it.toLong() }.zipWithNext().filterIndexed { index, _ -> index % 2 == 0 }.toSet()
        val almanac = getAlmanac(input)

        return seedRanges.minOf { seedRange ->
            (seedRange.first until seedRange.first + seedRange.second).minOf { seed -> seedToLocation(seed, almanac) }
        }.toString()
    }

    private fun seedToLocation(
        seed: Long,
        almanac: List<List<Entry>>
    ): Long {
        val soil = getDestinationOrDefault(seed, almanac[0])
        val fertilizer = getDestinationOrDefault(soil, almanac[1])
        val water = getDestinationOrDefault(fertilizer, almanac[2])
        val light = getDestinationOrDefault(water, almanac[3])
        val temperature = getDestinationOrDefault(light, almanac[4])
        val humidity = getDestinationOrDefault(temperature, almanac[5])
        return getDestinationOrDefault(humidity, almanac[6])
    }

    private fun getAlmanac(input: List<List<String>>): List<List<Entry>> {
        val seedToSoil = sourceToDestinationMap(input[1])
        val soilToFertilizer = sourceToDestinationMap(input[2])
        val fertilizerToWater = sourceToDestinationMap(input[3])
        val waterToLight = sourceToDestinationMap(input[4])
        val lightToTemperature = sourceToDestinationMap(input[5])
        val temperatureToHumidity = sourceToDestinationMap(input[6])
        val humidityToLocation = sourceToDestinationMap(input[7])
        return listOf(
            seedToSoil,
            soilToFertilizer,
            fertilizerToWater,
            waterToLight,
            lightToTemperature,
            temperatureToHumidity,
            humidityToLocation
        )
    }

    private fun sourceToDestinationMap(mapping: List<String>): List<Entry> {
        return mapping.drop(1).map { numbers ->
            numbers.split(" ").map { it.toLong() }.let {
                val destination = it[0]
                val source = it[1]
                val length = it[2]

                Entry(source, destination, length)
            }
        }
    }

    private fun getDestinationOrDefault(source: Long, mappings: List<Entry>): Long {
        mappings.forEach {
            val index = source - it.source
            if (index >= 0 && index <= it.length) {
                return it.destination + index
            }
        }

        return source
    }

    data class Entry(val source: Long, val destination: Long, val length: Long)
}