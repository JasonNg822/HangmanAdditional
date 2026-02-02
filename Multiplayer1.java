/*
Hangman Multiplayer Version 1
Multiplayer (Up to 5 player)
Players need to discuss to choose category and level
Computer random generate a word from the choose category and level players choose in WordLoader.words
All players only can guess wrong 5 times, guess wrong 6 times consider lose
One of the player guess all the letter within 6 times then round end, the player win
Only one hints for all players (not each player)
The player who use hint, his number of guess correct not increase (hint is for all players, so no count in number of guess correct)
End of the game will show ranking, ranking is follow by the number of guess correct (from big to small)
If number of correct is same then will follow by number of guess wrong (from small to big)
*/

import java.util.ArrayList;
import java.util.List;

public class Multiplayer1 extends HangmanBasic {

    protected static int player_index;

    public static void multiplayer1(int n_player) {

        continues = true;

        // to store players detail like name and "life"
        List<Player> players = new ArrayList<>();
        // to store those players details who lose before game end
        List<Player> loser = new ArrayList<>();

        // to get every player name
        GameLogic.get_player_name(n_player, players);

        while ((continues == true)) {

            GameUI.println("\n===== Welcome to Hangman game! =====");
            GameLogic.initialize(players, loser);
            used_letter = new ArrayList<>();
            player_index = 0;
            hint = 1;

            // let computer get the word from WordLoader
            GameUI.categorys(phrase);
            int category = GameLogic.choice(first_category, last_category);
            GameUI.level();
            int level = GameLogic.choice(min_level, max_level);
            word = WordLoader.get_word(category, level).toUpperCase().trim();
            boolean game_end = false;

            // game/round start
            while (game_end == false) {
                GameUI.footer();
                GameUI.display_hangman(players.get(player_index).wrong);
                GameUI.println(players.get(player_index).name + " round");
                // print out "Word:" and _ or letter that user guess correct
                GameUI.hide_sentence(used_letter, word);
                // print out left how many time user guess wrong like "Incorrect Guesses: 3/6"
                GameUI.incorrect(players.get(player_index).wrong);
                GameUI.println("Used Letters: " + used_letters(used_letter));
                GameUI.println("Hint left: " + hint);
                GameUI.footer();
                // the letter player guess
                letter = GameLogic.letter(hint);
                if (letter == '?') { // check if player use hint
                    letter = GameLogic.hint(word, used_letter);
                    used_letter.add(letter);
                    hint--;
                    game_end = GameLogic.multiplayer_win_logic(players, loser, player_index, used_letter, word); // check
                                                                                                                 // if
                                                                                                                 // win
                                                                                                                 // because
                                                                                                                 // maybe
                                                                                                                 // player
                                                                                                                 // use
                                                                                                                 // hint
                                                                                                                 // on
                                                                                                                 // the
                                                                                                                 // last
                                                                                                                 // alphabet
                }
                // to calculate number of player guess wrong and correct
                players.get(player_index).wrong = GameLogic.number_of_guess_wrong(players.get(player_index).wrong, word,
                        letter, used_letter);
                players.get(player_index).correct = GameLogic.number_of_guess_correct(players.get(player_index).correct,
                        word, letter, used_letter);
                if (used_letter.contains(letter)) {
                    continue;
                } else {
                    used_letter.add(letter);
                }

                // get the number of player before check the player lose or not (if lose will
                // remove from players)
                int before_remove = players.size();

                // check if win or not
                if (GameLogic.multiplayer_win_logic(players, loser, player_index, used_letter, word)) {
                    game_end = true;
                }
                // check if lose or not
                else if (GameLogic.multiplayer_lose_logic(players, loser, player_index, used_letter, word)) {
                    game_end = true;
                }

                // get the number of player before check the player lose or not (if lose will
                // remove from players)
                int after_remove = players.size();

                if (after_remove < before_remove) { // check got player have been remove or not
                    if (player_index >= players.size()) {
                        player_index = 0;
                    }
                    continue;
                }

                // if no player have been remove then continue next player
                player_index = (player_index + 1) % players.size();
            }
            do {
                GameUI.print("\nYou want to continue for next game? (y/n): ");
                ans = input.nextLine();
            } while (!ans.equalsIgnoreCase("y") && !ans.equalsIgnoreCase("n"));
            if (ans.equalsIgnoreCase("n")) {
                GameUI.println("");
                continues = false;
            }
        }
    }
}
