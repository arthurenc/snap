package game

import game.Snap.dealDeck
import org.scalatest.funsuite.AnyFunSuite

class DealDeckSpec extends AnyFunSuite {

  val card: Card = Card(Spades, 1)

  test("the dealDeck function should split split the cards equally between the players") {
    val cards: List[Card] = List.fill(4)(card)
    val numberOfPlayers: Int = 2

    val decks: List[List[Card]] = dealDeck(cards, numberOfPlayers)
    assert(decks(0) == decks(1))
  }

  test("the number of decks created should be the same as the number of players") {
    val cards: List[Card] = List.fill(4)(card)
    val numberOfPlayers: Int = 2

    assert(dealDeck(cards, numberOfPlayers).size == 2)
  }

  test("any excess cards should be discard") {
    val cards: List[Card] = List.fill(5)(card)
    val numberOfPlayers: Int = 2

    assert(dealDeck(cards, numberOfPlayers).size == 2)
  }

}
