package year2023

import AoCApp
import kotlin.reflect.typeOf

object Day07 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = inputLines
        printPart(1, part1(input))
//        printPart(2, part2(input))
    }

    private fun part1(input: List<String>): String {
        return input.map { line -> line.split(' ').let { val value = cardInputToHand(it[0])
            Triple(value, getValue(value), it[1].toInt()) } }
            .sortedBy { it.second }
            .let {
                it.forEach { item ->  println("${item.first.javaClass.simpleName} ${item.first.card1.name}|${item.first.card2.name}|${item.first.card3.name}|${item.first.card4.name}|${item.first.card5.name} ${item.second}") }
                it
            }
            .mapIndexed { index, pair -> (index + 1) * pair.third }
            .sum().toString()
    }

    private fun part2(input: List<String>): String {
        TODO("Not yet implemented 249391089 / 247771633")
    }

    private fun cardInputToHand(cardInput: String): Hand {
        val cards = cardInput.toCharArray().map { charToCard(it) }
        return cards.groupBy { it }.let {
            when (it.size) {
                1 -> Hand.FiveOfAKind(cards[0], cards[1], cards[2], cards[3], cards[4])
                2 -> getHandWithTwoDifferentCardTypes(cards[0], cards[1], cards[2], cards[3], cards[4], it)
                3 -> getHandWithThreeDifferentCardTypes(cards[0], cards[1], cards[2], cards[3], cards[4], it)
                4 -> Hand.OnePair(cards[0], cards[1], cards[2], cards[3], cards[4])
                5 -> Hand.HighCard(cards[0], cards[1], cards[2], cards[3], cards[4])
                else -> unreachable()
            }
        }
    }

    private fun getValue(hand: Hand): Long {
        return hand.value * 1000000000000 + hand.card1.value * 100000000 + hand.card2.value * 1000000 + hand.card3.value * 10000 + hand.card4.value * 100 + hand.card5.value
    }

    private fun getHighValue(hand: Hand): Int {
        return if (hand is Hand.HighCard) listOf(
            hand.card1.value,
            hand.card2.value,
            hand.card3.value,
            hand.card4.value,
            hand.card5.value
        ).maxByOrNull { it }!!
        else 0
    }

    private fun charToCard(char: Char): Card {
        return when (char) {
            'A' -> Card.A
            'K' -> Card.K
            'Q' -> Card.Q
            'J' -> Card.J
            'T' -> Card.T
            '9' -> Card.Nine
            '8' -> Card.Eight
            '7' -> Card.Seven
            '6' -> Card.Six
            '5' -> Card.Five
            '4' -> Card.Four
            '3' -> Card.Three
            '2' -> Card.Two
            else -> unreachable()
        }
    }

    private fun getHandWithTwoDifferentCardTypes(
        card1: Card,
        card2: Card,
        card3: Card,
        card4: Card,
        card5: Card,
        map: Map<Card, List<Card>>
    ): Hand {
        return map.map { it.value.size }.let {
            val first = it.sortedDescending()[0]

            if (first == 4) {
                Hand.FourOfAKind(card1, card2, card3, card4, card5)
            } else {
                Hand.FullHouse(card1, card2, card3, card4, card5)
            }
        }
    }

    private fun getHandWithThreeDifferentCardTypes(
        card1: Card,
        card2: Card,
        card3: Card,
        card4: Card,
        card5: Card,
        map: Map<Card, List<Card>>
    ): Hand {
        return map.map { it.value.size }.let {
            val first = it.sortedDescending()[0]

            if (first == 3) {
                Hand.ThreeOfAKind(card1, card2, card3, card4, card5)
            } else {
                Hand.TwoPair(card1, card2, card3, card4, card5)
            }
        }
    }

    enum class Card(val value: Int) {
        A(13),
        K(12),
        Q(11),
        J(10),
        T(9),
        Nine(8),
        Eight(7),
        Seven(6),
        Six(5),
        Five(4),
        Four(3),
        Three(2),
        Two(1)
    }

    sealed interface Hand {
        val value: Int
        val card1: Card
        val card2: Card
        val card3: Card
        val card4: Card
        val card5: Card

        data class FiveOfAKind(
            override val card1: Card,
            override val card2: Card,
            override val card3: Card,
            override val card4: Card,
            override val card5: Card
        ) : Hand {
            override val value = 7
        }


        data class FourOfAKind(
            override val card1: Card,
            override val card2: Card,
            override val card3: Card,
            override val card4: Card,
            override val card5: Card
        ) : Hand {
            override val value = 6
        }

        data class FullHouse(
            override val card1: Card,
            override val card2: Card,
            override val card3: Card,
            override val card4: Card,
            override val card5: Card
        ) : Hand {
            override val value = 5
        }

        data class ThreeOfAKind(
            override val card1: Card,
            override val card2: Card,
            override val card3: Card,
            override val card4: Card,
            override val card5: Card
        ) : Hand {
            override val value = 4
        }

        data class TwoPair(
            override val card1: Card,
            override val card2: Card,
            override val card3: Card,
            override val card4: Card,
            override val card5: Card
        ) : Hand {
            override val value = 3
        }

        data class OnePair(
            override val card1: Card,
            override val card2: Card,
            override val card3: Card,
            override val card4: Card,
            override val card5: Card
        ) : Hand {
            override val value = 2
        }

        data class HighCard(
            override val card1: Card,
            override val card2: Card,
            override val card3: Card,
            override val card4: Card,
            override val card5: Card
        ) : Hand {
            override val value = 1
        }
    }
}