package game

import game.Snap.checkForSnap
import input.{Colours, Rule, Suites, Values}
import org.scalatest.funsuite.AnyFunSuite

class CheckForSnapSpec extends AnyFunSuite {

  val aceSpades: Card   = Card(Spades, 1)
  val aceHearts: Card   = Card(Hearts, 1)
  val nineSpades: Card  = Card(Spades, 9)
  val twoDiamonds: Card = Card(Diamonds, 2)

  test("checkForSnap should return true if the values match and the Values rule is enabled") {
    val rules: List[Rule] = List(Values)
    assert(checkForSnap(aceSpades, aceHearts, rules))
  }

  test("checkForSnap should return false if the values don't match and the Values rule is enabled") {
    val rules: List[Rule] = List(Values)
    assert(!checkForSnap(aceSpades, twoDiamonds, rules))
  }

  test("checkForSnap should return true if the suites match and the Suites rule is enabled") {
    val rules: List[Rule] = List(Suites)
    assert(checkForSnap(aceSpades, nineSpades, rules))
  }

  test("checkForSnap should return false if the suites don't match and the Suites rule is enabled") {
    val rules: List[Rule] = List(Suites)
    assert(!checkForSnap(aceSpades, twoDiamonds, rules))
  }

  test("checkForSnap should return true if the colours match and the Colours rule is enabled") {
    val rules: List[Rule] = List(Colours)
    assert(checkForSnap(aceHearts, twoDiamonds, rules))
  }

  test("checkForSnap should return false if the colours don't match and the Colours rule is enabled") {
    val rules: List[Rule] = List(Colours)
    assert(!checkForSnap(aceSpades, twoDiamonds, rules))
  }

}
