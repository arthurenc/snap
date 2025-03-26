package input

// case class representing the game config
case class Config(
                   numberOfDecks: Int,
                   numberOfPlayers: Int,
                   rules: List[Rule],
                   maxTurns: Option[Int] = None
) {
  private val minNumberOfPlayers: Int = 2
  private val minNumberOfDecks: Int = 1

  // function that validates values for game config, returning a boolean if the config is valid
  def validateConfig: Boolean = {
    numberOfDecks >= minNumberOfDecks && numberOfPlayers >= minNumberOfPlayers && rules.nonEmpty
  }

  // function that summarise the game config, returning it as a String
  def mkString: String = {
    s"number of decks ${numberOfDecks.toString}, number of players ${numberOfPlayers.toString}, rule/s ${rules.mkString(", ")},"
  }
}

sealed trait Rule
case object Suites extends Rule
case object Values extends Rule
case object Colours extends Rule

