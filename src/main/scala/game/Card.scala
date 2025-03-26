package game

case class Card(suite: Suite, value: Int) {
  // function that returns the name of the card as a String
  def mkString: String = value match {
    case 1  => s"Ace of $suite"
    case 11 => s"Jack of $suite"
    case 12 => s"Queen of $suite"
    case 13 => s"King of $suite"
    case _  => s"$value of $suite"
  }
}

sealed trait Suite {
  def colour: Colour
}
case object Hearts extends Suite {
  override def colour: Colour = Red
}
case object Spades extends Suite {
  override def colour: Colour = Black
}
case object Clubs extends Suite {
  override def colour: Colour = Black
}
case object Diamonds extends Suite {
  override def colour: Colour = Red
}

sealed trait Colour
case object Black extends Colour
case object Red extends Colour
