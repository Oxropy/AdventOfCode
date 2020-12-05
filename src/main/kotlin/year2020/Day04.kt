package year2020

import AoCApp

object Day04 : AoCApp() {

    private val eyeColors = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    private val expectedFields = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
//    private const val optionalField = "cid";

    @JvmStatic
    fun main(args: Array<String>) {
        printPart(1, part1(inputLines))
        printPart(2, part2(inputLines))
    }

    private fun part1(lines: List<String>): String {
        val passports = splitPassport(lines)
        val validPassports = passports.count { it.keys.containsAll(expectedFields) }
        return validPassports.toString()
    }

    private fun part2(lines: List<String>): String {
        val passports = splitPassport(lines)
        val validPassports = passports.count { it.keys.containsAll(expectedFields) && validatePassport(it) }
        return validPassports.toString()
    }

    private fun splitPassport(lines: List<String>): List<Map<String, String>> {

        val passports: MutableList<Map<String, String>> = mutableListOf()
        val passportLines: MutableList<String> = mutableListOf()
        for (line in lines) {
            if (line.isEmpty()) {
                passports.add(splitFields(passportLines))
                passportLines.clear()
                continue
            }

            passportLines.add(line)
        }

        if (passportLines.count() > 0) {
            passports.add(splitFields(passportLines))
        }

        return passports
    }

    private fun splitFields(lines: List<String>): Map<String, String> {
        return lines.flatMap { it.split(' ') }
            .map { field ->
                field.split(':')
                    .let {
                        it.zip(it.drop(1))
                            .first()
                    }
            }
            .toMap()
    }

    private fun validatePassport(passport: Map<String, String>): Boolean {
        return passport.all { (field, value) ->
            when (field) {
                "byr" -> validateBirthYear(value)
                "iyr" -> validateIssueYear(value)
                "eyr" -> validateExpirationYear(value)
                "hgt" -> validateHeight(value)
                "hcl" -> validateHairColor(value)
                "ecl" -> validateEyeColor(value)
                "pid" -> validatePassportId(value)
                "cid" -> true
                else -> false
            }
        }
    }

    private fun validateBirthYear(year: String): Boolean {
        return isInputNumberAndInRange(year, 1920, 2002)
    }

    private fun validateIssueYear(year: String): Boolean {
        return isInputNumberAndInRange(year, 2010, 2020)
    }

    private fun validateExpirationYear(year: String): Boolean {
        return isInputNumberAndInRange(year, 2020, 2030)
    }

    private fun validateHeight(height: String): Boolean {
        val system = height.takeLast(2)
        return when (system) {
            "cm" -> isInputNumberAndInRange(height.take(height.length - 2), 150, 193)
            "in" -> isInputNumberAndInRange(height.take(height.length - 2), 59, 76)
            else -> false
        }
    }

    private fun isInputNumberAndInRange(input: String, min: Int, max: Int): Boolean {
        return input.toIntOrNull()?.let {
            return it in min..max
        } ?: false
    }

    private fun validateHairColor(color: String): Boolean {
        return Regex("#[0-9a-f]{6}").matches(color)
    }

    private fun validateEyeColor(color: String): Boolean {
        return eyeColors.contains(color)
    }

    private fun validatePassportId(id: String): Boolean {
        return Regex("[0-9]{9}").matches(id)
    }
}