package game

import input.{Colours, Config, Rule, Suites, Values}

import scala.util.Random

object Snap {

  def setupGame(config: Config): List[Player] = {
    println("-"*30)
    println("Dealing cards")

    // create and shuffle the decks of cards
    val deck: List[Card] = Deck(config.numberOfDecks).shuffleDecks

    // deal the cards to the number of players
    val players: List[Player] = dealDeck(deck, config.numberOfPlayers)
      .zipWithIndex
      .map(deckAtIndex => Player(deckAtIndex._1, deckAtIndex._2))

    players
  }

  def startGame(players: List[Player], rules: List[Rule], maxNumberOfTurns: Option[Int] = Some(5)): Player = {
    println("Starting game")
    println("-"*30)

    val playersAtIndex: List[(Player, Int)] = players.zipWithIndex
    val startingPlayerNumber: Int = Random.shuffle(players.indices.toList).head

    // play cards until a winner is found or limit met
    playCard(playersAtIndex, startingPlayerNumber, rules, maxNumberOfTurns)
  }

  def playCard(
    playersAtIndex: List[(Player, Int)],
    currentPlayer: Int,
    rules: List[Rule],
    maxTurns: Option[Int],
    stack: List[Card] = List.empty,
    turns: Int = 0
  ): Player = {
    // if we reach the maximum number of turns then end game
    if (maxTurns.isDefined && turns > maxTurns.get) {
      println(s"The maximum number of turns ${maxTurns.get} has been reached.")
      Player(List.empty, -1)
    }

    // if there is only one remaining player, return them as the winner
    else if (playersAtIndex.size == 1) playersAtIndex.head._1

    // continue play
    else {
      val player: Player = playersAtIndex(currentPlayer)._1

      // returns the index for the next player
      val nextPlayer: Int = if (currentPlayer != playersAtIndex.size - 1) currentPlayer + 1 else 0

      // if the player has no remaining cards, remove them from the game
      if (player.cards.isEmpty) {
        println(s"${player.playerName} is out of playable cards, they have been removed from the game.")

        // remove the player from playersAtIndex
        val (firstListOfPlayers, secondListOfPlayers) = playersAtIndex.splitAt(currentPlayer)
        val updatedPlayersAtIndex: List[(Player, Int)] = firstListOfPlayers ++ secondListOfPlayers.tail

        playCard(updatedPlayersAtIndex, nextPlayer, rules, maxTurns, stack, turns + 1)
      }

      // if the player has cards then play their top card
      else {

        // retrieve the top card of the players deck, play it onto the existing stack
        val topCard: Card = player.cards.head
        val newStack: List[Card] = stack :+ topCard
        println(s"${player.playerName} played the ${topCard.mkString}")

        // retrieve top card from old stack if the stack exists, check for snap
        val snap: Boolean = if (stack.nonEmpty) {
          val topOfStack: Card = stack.head
          checkForSnap(topOfStack, topCard, rules)
        } else false

        // create a new pile for the players cards, replace their existing cards with this new pile
        val newCards: List[Card] = player.cards.tail
        val updatedPlayer: Player = player.copy(cards = newCards)
        val updatedPlayersAtIndex: List[(Player, Int)] = playersAtIndex
          .updated(currentPlayer, (updatedPlayer, currentPlayer))

        // if snap, randomly pick a player who called snap first or miss
        val possibleWinningPlayer: Option[Int] = if (snap) callSnap(playersAtIndex.size) else None
        val updatedPlayersAtIndexWithWinner: List[(Player, Int)] = if (possibleWinningPlayer.isDefined) {
          val winningPlayerNumber: Int = possibleWinningPlayer.getOrElse(throw new Throwable("Unable to get winningPlayer from Option"))
          val winningPlayer: Player = updatedPlayersAtIndex(winningPlayerNumber)._1
          updatedPlayersAtIndex.updated(winningPlayerNumber, (winningPlayer.copy(cards = winningPlayer.cards ++ stack), winningPlayerNumber))
        } else updatedPlayersAtIndex

        // new player play their cards
        playCard(
          updatedPlayersAtIndexWithWinner,
          nextPlayer,
          rules,
          maxTurns,
          if (possibleWinningPlayer.isDefined) List.empty else newStack,
          turns + 1
        )
      }
    }
  }

  // randomly pick a player or miss, returning either the player number or None respectively
  private def callSnap(numberOfPlayers: Int): Option[Int] = {
    val winningPlayer: Int = Random.shuffle((0 to numberOfPlayers).toList).head - 1
    if (winningPlayer > -1) {
      println(s"Player $winningPlayer called snap!")
      Some(winningPlayer)
    } else None
  }

  // compares the two cards to the active rules, returning true if the cards are a match
  def checkForSnap(oldCard: Card, newCard: Card, rules: List[Rule]): Boolean = {
    rules.foldLeft(false)((_, rule) => rule match {
      case Suites  => if (oldCard.suite == newCard.suite) true else false
      case Values  => if (oldCard.value == newCard.value) true else false
      case Colours => if (oldCard.suite.colour == newCard.suite.colour) true else false
      case _       => false
    })
  }

  // split the cards in the deck equally between the number of players, discarding any excess
  def dealDeck(deck: List[Card], numberOfPlayers: Int): List[List[Card]] = {
    val cardsPerPlayer: Int = deck.size / numberOfPlayers
    deck.take(cardsPerPlayer * numberOfPlayers).grouped(cardsPerPlayer).toList
  }
}
