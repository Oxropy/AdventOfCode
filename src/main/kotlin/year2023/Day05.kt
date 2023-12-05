package year2023

import AoCApp

object Day05 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = inputGroupedLines
        printPart(1, part1(input))
//        printPart(2, part2(input))
    }

    private fun part1(input: List<List<String>>): String {
        val seeds = input[0][0].split(" ").drop(1).map { it.toLong() }.toSet()
        val seedToSoil = sourceToDestinationMap(input[1])
        val soilToFertilizer = sourceToDestinationMap(input[2])
        val fertilizerToWater = sourceToDestinationMap(input[3])
        val waterToLight = sourceToDestinationMap(input[4])
        val lightToTemperature = sourceToDestinationMap(input[5])
        val temperatureToHumidity = sourceToDestinationMap(input[6])
        val humidityToLocation = sourceToDestinationMap(input[7])

        return seeds.minOf { seed ->
            val soil = getDestinationOrDefault(seed, seedToSoil)
            val fertilizer = getDestinationOrDefault(soil, soilToFertilizer)
            val water = getDestinationOrDefault(fertilizer, fertilizerToWater)
            val light = getDestinationOrDefault(water, waterToLight)
            val temperature = getDestinationOrDefault(light, lightToTemperature)
            val humidity = getDestinationOrDefault(temperature, temperatureToHumidity)
            getDestinationOrDefault(humidity, humidityToLocation)
        }.toString()
    }

    private fun part2(input: List<List<String>>): String {
        TODO("Not yet implemented")
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