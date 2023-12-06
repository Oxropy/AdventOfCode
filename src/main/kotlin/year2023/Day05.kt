package year2023

import AoCApp

object Day05 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = inputGroupedLines
//        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<List<String>>): String {
        val seeds = input[0][0].split(" ").drop(1).map { IdRange(it.toLong(), 1) }.toSet()
        val almanac = getAlmanac(input)

        return seeds.minOf { seed -> seedRangeToLocation(seed, almanac).minOf { it.source } }.toString()
    }

    private fun part2(input: List<List<String>>): String {
        val seedRanges = input[0][0].split(" ").asSequence().drop(1)
            .map { it.toLong() }.zipWithNext().filterIndexed { index, _ -> index % 2 == 0 }.map { IdRange(it.first, it.second) }.toSet()
        val almanac = getAlmanac(input)

        return seedRanges.minOf { seed -> seedRangeToLocation(seed, almanac).minOf { it.source } }.toString()
    }

    private fun seedRangeToLocation(
        seedRange: IdRange,
        almanac: List<List<Entry>>
    ): List<IdRange> {
        val soil = getDestinationOrDefault(seedRange, almanac[0])
        val fertilizer = getDestinationOrDefault(soil, almanac[1])
        val water = getDestinationOrDefault(fertilizer, almanac[2])
        val light = getDestinationOrDefault(water, almanac[3])
        val temperature = getDestinationOrDefault(light, almanac[4])
        val humidity = getDestinationOrDefault(temperature, almanac[5])
        val location = getDestinationOrDefault(humidity, almanac[6])
        return location
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

    private fun getDestinationOrDefault(ranges: List<IdRange>, mappings: List<Entry>): List<IdRange> {
        return ranges.flatMap { getDestinationOrDefault(it, mappings) }
    }

    private fun getDestinationOrDefault(range: IdRange, mappings: List<Entry>): List<IdRange> {
        val result = mutableListOf<IdRange>()
        var lastRange = range
        var mappingIndex = 0
        while (mappingIndex < mappings.size) {
            val mapping = mappings[mappingIndex]
            val index = lastRange.source - mapping.source
            if (index >= 0 && index <= mapping.length) {
                if (mapping.length - index >= lastRange.length) {
                    result.add(IdRange(mapping.destination + index, lastRange.length))
                    return result
                } else {
                    val splitRange = IdRange(mapping.destination + index, lastRange.length - (mapping.length - index))
                    result.add(splitRange)
                    lastRange = IdRange(lastRange.source + splitRange.length, lastRange.length - splitRange.length)
                    mappingIndex = 0
                    continue
                }
            }

            mappingIndex++
        }

        result.add(lastRange)
        return result
    }

    data class IdRange(val source: Long, val length: Long)
    data class Entry(val source: Long, val destination: Long, val length: Long)
}