package game

import game.Snap.{checkForSnap, playCard}
import input.Rule
import org.scalatest.funsuite.AnyFunSuite

class PlayCardSpec extends AnyFunSuite {

  val card: Card = Card(Spades, 1)
  val player1: Player = Player(List(card, card), 1)
  val player2: Player = Player(List(card), 2)
  val player3: Player = Player(List.empty, 3)
  val rules: List[Rule] = List.empty

  test("playCardSpec should return the winning player if there is only one left") {
    val playersAtIndex: List[(Player, Int)] = List((player1, 1))

    assert(playCard(playersAtIndex, 1, rules, None) == player1)
  }

  test("playCardSpec should end the game and return Player -1 if maxTurns is reached") {
    val playersAtIndex: List[(Player, Int)] = List((player1, 1), (player2, 2))

    assert(playCard(playersAtIndex, 1, rules, Some(10), turns = 10) == Player(List.empty, -1))
  }

  test("playCardSpec should remove a player if they run out of cards, then continue play") {
    val playersAtIndex: List[(Player, Int)] = List((player1, 1), (player3, 3))

    assert(playCard(playersAtIndex, 1, rules, None) == player1)
  }

  test("reduce the players cards by 1 after each turn") {
    val playersAtIndex: List[(Player, Int)] = List((player1, 1), (player2, 2))

    assert(playCard(playersAtIndex, 1, rules, None).cards.size == player1.cards.size - 1)
  }
}
