/*
Hangman Basic Version
Single Player
Computer random generate a word from WordLoader.words (with category and level)
Player only can guess wrong 5 times, guess wrong 6 times consider lose
Player guess all the letter within 6 times then win
Player got one hint chance
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// to coordinate the overall game application functionality, (single player, computer random generate word)
public class HangmanBasic {

    public static final Scanner input = new Scanner(System.in);

    protected static final int first_category = 1;
    protected static final int last_category = 4;
    protected static final int phrase = 5;
    protected static final int min_level = 1;
    protected static final int max_level = 3;

    protected static int number_of_guess_wrong;
    protected static int hint;
    protected static List<Character> used_letter;

    protected static boolean continues;
    protected static char letter;
    protected static String ans;

    protected static String word;

    public static void hangmanBasic() {

        continues = true;
        int wrong = 0;
        int correct = 0;
        String name = "";
        Player player = new Player(name, wrong, correct);
        while (true) {
            GameUI.print("\nPlease enter player name: ");
            name = HangmanBasic.input.nextLine();
            if (!name.trim().isEmpty()) {
                player = new Player(name, wrong, correct);
                break;
            }
        }

        while (continues == true) {
            player.wrong = 0;
            player.correct = 0;
            hint = 1;
            used_letter = new ArrayList<>();

            GameUI.println("\n===== Welcome to Hangman game! =====");
            // let computer get the word from WordLoader
            GameUI.categorys(last_category);
            int category = GameLogic.choice(first_category, last_category);
            GameUI.level();
            int level = GameLogic.choice(min_level, max_level);
            word = WordLoader.get_word(category, level).toUpperCase().trim();
            boolean game_end = false;

            // round start
            while (game_end == false) {
                GameUI.footer();
                GameUI.display_hangman(player.wrong);
                // print out "Word:" and _ or letter that player guess correct
                GameUI.hide_sentence(used_letter, word);
                // print out left how many time player guess wrong like "Incorrect Guesses: 3/6"
                GameUI.incorrect(player.wrong);
                GameUI.println("Used Letters: " + used_letters(used_letter));
                GameUI.println("Hint left: " + hint);
                GameUI.footer();
                // the letter player guess
                letter = GameLogic.letter(hint);
                if (letter == '?') { // check if player use hint
                    letter = GameLogic.hint(word, used_letter);
                    used_letter.add(letter);
                    hint--;
                    game_end = GameLogic.basic_win_logic(used_letter, word, player.name); // check if win because maybe
                                                                                          // player use hint on the last
                                                                                          // alphabet
                } else {
                    player.wrong = GameLogic.number_of_guess_wrong(player.wrong, word, letter, used_letter);
                    if (used_letter.contains(letter)) {
                        continue;
                    } else {
                        used_letter.add(letter);
                    }
                    // check if win or not
                    if (GameLogic.basic_win_logic(used_letter, word, player.name)) {
                        game_end = true;
                    }
                    // check if lose or not
                    else if (GameLogic.basic_lose_logic(player.wrong, word, player.name)) {
                        game_end = true;
                    }
                }
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

    // to convert list to String
    protected static String used_letters(List<Character> used_letter) {
        List<String> usedLetters = new ArrayList<>();
        for (char letters : used_letter) {
            usedLetters.add(String.valueOf(letters));
        }
        return String.join(", ", usedLetters);
    }
}