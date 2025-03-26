import game.Snap.startGame
import game.{Player, Snap}
import input.{Config, LoadConfig, UserInput}

import scala.io.StdIn

object Main extends App {

  println("-"*30)
  println("Welcome to Arthur's snap game")
  println("How would you like to enter the game config?")
  println("1: enter manually through command line prompts")
  println("2: read from application config file")

  val configSource: String = StdIn.readLine()

  // pattern match the user response, throws an exception for invalid response
  val config: Either[Exception, Config] = configSource match {
    case "1" => Right(UserInput.apply)
    case "2" => LoadConfig.apply
    case _   => Left(new Exception(s"Invalid input $configSource, please enter either 1 or 2. Ending game."))
  }

  // load and validate game config. Launch game if successful, otherwise throw exception
  config match {
    case Right(config) =>
      if (config.validateConfig) {
        val players: List[Player] = Snap.setupGame(config)
        val winner: Player = startGame(players, config.rules, config.maxTurns)
        println("-"*30)
        winner.playerNumber match {
          case -1 => println("Game over, no winner.")
          case _ => println(s"${winner.playerName} is the winner!")
        }
      } else throw new Exception(s"Invalid config: ${config.mkString}. Please refer to docs for valid config.")
    case Left(err) => println(err)
  }
}

