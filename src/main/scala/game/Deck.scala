package game

import scala.util.Random

case class Deck(numberOfDecks: Int) {
  private val suites: List[Suite] = List(Hearts, Spades, Clubs, Diamonds)

  // creates a value for each suite and outputs as a List of Suite and Int tuple (deck of cards)
  private val deck: List[Card] =
    for {
      suite <- suites
      value <- 1 to 13
    } yield Card(suite, value)

  // combines multiple decks into one list
  private def combineDecks: List[Card] = List.fill(numberOfDecks)(deck).flatten

  // shuffles the combined deck
  def shuffleDecks: List[Card] = Random.shuffle(combineDecks)
}
