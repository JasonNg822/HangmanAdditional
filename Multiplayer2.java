/*
Hangman Multiplayer Version 2
Multiplayer (Up to 5 player)
One of the player (host) generate a word/phrase/sentence from WordLoader.words (with category and level)
All players (except host, host no need guess) only can guess wrong 5 times, guess wrong 6 times consider lose
One of the player guess all the letter within 6 times then round end, the player win
Only one hints for all players (not each player)
The player who use hint, his number of guess correct not increase (hint is for all players, so no count in number of guess correct)
End of the game will show ranking, ranking is follow by the number of guess correct (from big to small)
If number of correct is same then will follow by number of guess wrong (from small to big)
Host will not in the ranking, because if got player guess correct host lose else host win
*/

import java.util.ArrayList;
import java.util.List;

public class Multiplayer2 extends Multiplayer1 {

    protected static List<List<List<String>>> words = WordLoader.words;

    public static void multiplayer2(int n_player) {

        continues = true;

        // to store players detail like name and "life"
        List<Player> players = new ArrayList<>();

        // to store those players details who lose before game end
        List<Player> loser = new ArrayList<>();

        Player host;

        // to get every player name
        GameLogic.get_player_name(n_player, players, use_letter);

        while ((continues == true)) {
            use_letter = new ArrayList<>();
            player_index = 0;

            GameUI.println("\n===== Welcome to Hangman game! =====");

            // choose which player to be host
            host = GameLogic.host(players);
            players.remove(host);

            // let host get the word from WordLoader
            GameUI.categorys(phrase);
            int category = GameLogic.choice(first_category, phrase);
            GameUI.level();
            int level = GameLogic.choice(min_level, max_level);
            GameUI.words(words, category, level);

            word = GameLogic.word(words, category, level).toUpperCase().trim();
            GameLogic.initialize(players, loser, use_letter, word);
            boolean game_end = false;

            // game/round start
            while (game_end == false) {
                GameUI.footer();
                GameUI.display_hangman(players.get(player_index).wrong);
                GameUI.println(players.get(player_index).name + " round");
                // print out "Word:" and _ or letter that user guess correct
                GameUI.hide_sentence(players.get(player_index).mask);
                // print out left how many time user guess wrong like "Incorrect Guesses: 3/6"
                GameUI.incorrect(players.get(player_index).wrong);
                GameUI.println("Used Letters: " + used_letters(players.get(player_index).use_letter));
                GameUI.println("Hint left: " + players.get(player_index).hint);
                GameUI.footer();
                // the letter player guess
                letter = GameLogic.letter(players.get(player_index).hint);
                if (letter == '?') { // check if player use hint
                    letter = GameLogic.hint(word, players.get(player_index).use_letter);
                    players.get(player_index).use_letter.add(letter);
                    players.get(player_index).hint--;
                    players.get(player_index).mask = GameLogic.hide_sentences(players.get(player_index).use_letter, word);
                    game_end = GameLogic.multiplayer_win_logic(players, loser, player_index, players.get(player_index).use_letter, word); // check if win because maybe player use hint on the last alphabet
                }
                // to calculate number of player guess wrong and correct
                players.get(player_index).wrong = GameLogic.number_of_guess_wrong(players.get(player_index).wrong, word, letter, players.get(player_index).use_letter);
                players.get(player_index).correct = GameLogic.number_of_guess_correct(players.get(player_index).correct, word, letter, players.get(player_index).use_letter);
                if (players.get(player_index).use_letter.contains(letter)) {
                    continue;
                } else {
                    players.get(player_index).use_letter.add(letter);
                    players.get(player_index).mask = GameLogic.hide_sentences(players.get(player_index).use_letter, word);
                }

                // get the number of player before check the player lose or not (if lose will
                // remove from players)
                int before_remove = players.size();

                // check if win or not
                if (GameLogic.multiplayer_win_logic(players, loser, player_index, players.get(player_index).use_letter, word)) {
                    game_end = true;
                }
                // check if lose or not
                else if (GameLogic.multiplayer_lose_logic(players, loser, player_index, players.get(player_index).use_letter, word)) {
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
            } else {
                players.add(host);
            }
        }
    }
}
