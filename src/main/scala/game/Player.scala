package game

case class Player(cards: List[Card], playerNumber: Int) {
  val playerName: String = s"Player $playerNumber"
}
