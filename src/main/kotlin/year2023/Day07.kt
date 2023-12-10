package year2023

import AoCApp

object Day07 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = inputLines
//        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<String>): String {
        return input.map { line -> line.split(' ').let { val value = cardInputToHand1(it[0])
            Pair(getValue1(value), it[1].toInt()) } }
            .sortedBy { it.first }
            .mapIndexed { index, pair -> (index + 1) * pair.second }
            .sum().toString()
    }

    private fun part2(input: List<String>): String {
        return input.map { line -> line.split(' ').let { val value = cardInputToHand2(it[0])
            Triple(value, getValue2(value), it[1].toInt()) } }
            .sortedBy { it.second }
            .let {
                it.forEach { item ->  println("${item.first.javaClass.simpleName} ${item.first.card1.name}|${item.first.card2.name}|${item.first.card3.name}|${item.first.card4.name}|${item.first.card5.name} ${item.second}") }
                it
            }
            .mapIndexed { index, pair -> (index + 1) * pair.third }
            .sum().toString()
    }

    private fun cardInputToHand1(cardInput: String): Hand {
        val cards = cardInput.toCharArray().map { charToCard(it) }
        return getHand(cards, cards)
    }

    private fun cardInputToHand2(cardInput: String): Hand {
        val cards = cardInput.toCharArray().map { charToCard(it) }
        val cardsWithoutJoker = changeJokerCards(cards)
        return getHand(cardsWithoutJoker, cards)
    }

    private fun getHand(
        cardsToCheck: List<Card>,
        cardsToUse: List<Card>
    ): Hand {
        return cardsToCheck.groupBy { it }.let {
            when (it.size) {
                1 -> Hand.FiveOfAKind(cardsToUse[0], cardsToUse[1], cardsToUse[2], cardsToUse[3], cardsToUse[4])
                2 -> getHandWithTwoDifferentCardTypes(cardsToUse[0], cardsToUse[1], cardsToUse[2], cardsToUse[3], cardsToUse[4], it)
                3 -> getHandWithThreeDifferentCardTypes(cardsToUse[0], cardsToUse[1], cardsToUse[2], cardsToUse[3], cardsToUse[4], it)
                4 -> Hand.OnePair(cardsToUse[0], cardsToUse[1], cardsToUse[2], cardsToUse[3], cardsToUse[4])
                5 -> Hand.HighCard(cardsToUse[0], cardsToUse[1], cardsToUse[2], cardsToUse[3], cardsToUse[4])
                else -> unreachable()
            }
        }
    }

    private fun changeJokerCards(cards: List<Card>): List<Card> {
        val bestFitting = cards.groupBy { it }.map { Pair(it.key, it.value.size) }.sortedByDescending { it.second }[0].first
        return cards.map { if (it == Card.J) bestFitting else it }
    }

    private fun getValue1(hand: Hand): Long {
        return hand.value * 10000000000 + hand.card1.value1 * 100000000 + hand.card2.value1 * 1000000 + hand.card3.value1 * 10000 + hand.card4.value1 * 100 + hand.card5.value1
    }

    private fun getValue2(hand: Hand): Long {
        return hand.value * 10000000000 + hand.card1.value2 * 100000000 + hand.card2.value2 * 1000000 + hand.card3.value2 * 10000 + hand.card4.value2 * 100 + hand.card5.value2
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

    enum class Card(val value1: Int, val value2: Int) {
        A(13, 13),
        K(12, 12),
        Q(11, 11),
        J(10, 1),
        T(9, 10),
        Nine(8, 9),
        Eight(7, 8),
        Seven(6, 7),
        Six(5, 6),
        Five(4, 5),
        Four(3, 4),
        Three(2, 3),
        Two(1, 2)
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