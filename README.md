## Snap

This is a classic game of snap where all players are dealt an equal number of cards. They will take turns playing cards onto the pile until they run out.

If the card being played matches the card on the top of the pile, based on pre-determined rules, then any player can call snap. The first player to call snap adds the pile to their deck. If no players call snap then the game continues with no-one taking the pile.

The last player remaining with cards is the winner.

### Running the game

Use the `sbt run` command in the Main class to start the game.

Users will be primpted in the command terminal how they want to input the game config:
- Enter `1` to input the config in manually through command line prompts.
- Enter `2` to input the config from the application.conf file that lives in resources. You can modify the values manually in the file.

Use the `sbt test` command in the Main class to run the game unit tests.

### Game Settings

Users can modify the gameplay with the following settings:

Required:
- `numberOfDecks` is the number of decks of playing cards that are dealt between the players. This must be at least one.
- `numberOfPlayers` is the number of players in the game. This must be at least two.
- `rules` is a list of multiple rules that determine what players match their cards on
  - "suite" is matching on the cards suite (for example Diamonds or Spades)
  - "value" is matching on the cards number (for example Nine or King)
  - "colour" is matching on the suites colour

Optional:
- `maxTurns` is the maximum number of turns that can be played before the game ends
