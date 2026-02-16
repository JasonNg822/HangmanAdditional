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

public class HangmanBasic {

    public static final Scanner input = new Scanner(System.in);

    protected static final int first_category = 1;
    protected static final int last_category = 4;
    protected static final int phrase = 5;
    protected static final int min_level = 1;
    protected static final int max_level = 3;

    protected static int number_of_guess_wrong;
    protected static int hint;
    protected static List<Character> use_letter = new ArrayList<>();

    protected static boolean continues;
    protected static char letter;
    protected static String ans;

    protected static String word;

    public static void hangmanBasic() {

        continues = true;
        int wrong = 0;
        int correct = 0;
        String name = "";
        String mask = "";
        Player player = new Player(name, wrong, correct, hint, use_letter, mask);
        while (true) {
            GameUI.print("\nPlease enter player name: ");
            name = HangmanBasic.input.nextLine();
            if (!name.trim().isEmpty()) {
                player = new Player(name, wrong, correct, hint, use_letter, mask);
                break;
            }
        }

        // game start and repeat from here if game over and player choose to continue
        while (continues == true) {
            player.wrong = 0;
            player.correct = 0;
            player.hint = 1;
            use_letter = new ArrayList<>();
            player.use_letter = use_letter;

            GameUI.println("\n===== Welcome to Hangman game! =====");
            // let computer get the word from WordLoader
            GameUI.categorys(last_category);
            int category = GameLogic.choice(first_category, last_category);
            GameUI.level();
            int level = GameLogic.choice(min_level, max_level);
            word = WordLoader.get_word(category, level).toUpperCase().trim();
            player.mask = GameLogic.hide_sentences(player.use_letter, word);
            boolean game_end = false;

            // round start and if not game over will continue from here for next round
            while (game_end == false) {
                GameUI.footer();
                GameUI.display_hangman(player.wrong);
                // print out "Word:" and a series of "_" or letters that player guess correct
                GameUI.hide_sentence(player.mask);
                // print out left how many time player guess wrong like "Incorrect Guesses: 3/6"
                GameUI.incorrect(player.wrong);
                GameUI.println("Used Letters: " + used_letters(player.use_letter));
                GameUI.println("Hint left: " + player.hint);
                GameUI.footer();
                // the letter player guess
                letter = GameLogic.letter(player.hint);
                if (letter == '?') { // check if player use hint
                    letter = GameLogic.hint(word, player.use_letter);
                    player.use_letter.add(letter); 
                    player.hint--;
                    player.mask = GameLogic.hide_sentences(player.use_letter, word);
                    game_end = GameLogic.basic_win_logic(player.use_letter, word, player.name); // check if win or not, because maybe player use hint on the last alphabet
                } else {
                    player.wrong = GameLogic.number_of_guess_wrong(player.wrong, word, letter, player.use_letter);
                    if (player.use_letter.contains(letter)) { // if the letter have been guess by player
                        continue;
                    } else {
                        player.use_letter.add(letter);
                        player.mask = GameLogic.hide_sentences(player.use_letter, word);
                    }
                    // check if win or not
                    if (GameLogic.basic_win_logic(player.use_letter, word, player.name)) {
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
        if (used_letter.isEmpty()){
            return "";
        }
        List<String> usedLetters = new ArrayList<>();
        for (char letters : used_letter) {
            usedLetters.add(String.valueOf(letters));
        }
        return String.join(", ", usedLetters);
    }
}