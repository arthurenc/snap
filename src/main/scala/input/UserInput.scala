package input

import scala.io.StdIn

object UserInput {

  // function that prompts the user to input the game config manually
  def apply: Config = Config(
    { println("Number of decks: "); StdIn.readInt() },
    { println("Number of players: "); StdIn.readInt() },
    { println("Rules: "); requestRule().toList },
    { println("Optional max number of turns (enter any key to skip): "); requestMaxTurns() }
  )

  // function that prompts the user for max turns, returning either a Some(Int) or returning None
  private def requestMaxTurns(rules: Set[Rule] = Set.empty): Option[Int] = {
    val maxTurns: String = StdIn.readLine()
    maxTurns.toIntOption
  }

  // function that prompts the user to input a game rule, appending it to the existing set of rules
  private def requestRule(rules: Set[Rule] = Set.empty): Set[Rule] = {
    if (rules.nonEmpty) println(s"Current rule/s: ${rules.mkString(", ")}")

    println(s"What would you like to match on? (suite/value/colour)")
    val rule: String = StdIn.readLine().toLowerCase

    rule match {
      case "suite" => addRule(rules, Suites)
      case "value" => addRule(rules, Values)
      case "colour" => addRule(rules, Colours)
      case _       =>
        println("Invalid rule, please enter either 'suite', 'value' or 'colour'.")
        requestRule(rules)
    }
  }

  private def addRule(existingRules: Set[Rule], rule: Rule): Set[Rule] = {
    val newRules: Set[Rule] = existingRules ++ Set(rule)
    println(s"Matching on ${newRules.mkString(", ")}. Would you like to add another rule? (yes/no)")
    val addAnotherRule: String = StdIn.readLine()
    if (addAnotherRule == "yes") requestRule(newRules) else newRules
  }

}
