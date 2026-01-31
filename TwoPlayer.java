/*
Hangman Two Player Version
Two Players
One of the player generate a word from WordLoader.words (with category and level)
The other player only can guess wrong 5 times, guess wrong 6 times consider lose
The other Player guess all the letter within 6 times then win
Player got one hint chance
*/

import java.util.ArrayList;
import java.util.List;

// to coordinate the overall game application functionality, (single player, computer random generate word)
public class TwoPlayer extends Multiplayer2{

    public static void two_players(){

        List<List<List<String>>> words = WordLoader.words;

        continues = true;

        int wrong = 0;
        int correct = 0;
        String name = "";
        Player host = new Player(name, wrong, correct);
        Player player = new Player(name, wrong, correct);

        while(true){
            GameUI.print("\nPlease enter host name: ");
            name = HangmanBasic.input.nextLine();
            if (!name.trim().isEmpty()){
                host = new Player(name, wrong, correct);
                break;
            }
        }

        while(true){
            GameUI.print("\nPlease enter player name: ");
            name = HangmanBasic.input.nextLine();
            if (!name.trim().isEmpty()){
                player = new Player(name, wrong, correct);
                break;
            }
        }

        while (continues == true) {
            player.correct = 0;
            player.wrong = 0;
            hint = 1;
            used_letter = new ArrayList<>();

            GameUI.println("\n===== Welcome to Hangman game! =====");
            GameUI.println(host.name + " please choose a category (Enter number).");
            // let host get the word from WordLoader
            GameUI.categorys();
            int category = GameLogic.choice(first_category, last_category);
            GameUI.print("\n" + host.name + " please choose a level (Enter number).");
            GameUI.level();
            int level = GameLogic.choice(min_level, max_level);
            GameUI.println("\n" + host.name + " please choose a word (Enter number).");
            GameUI.words(words, category, level);
            
            word = GameLogic.word(words, category, level).toUpperCase().trim();
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
                if (letter == '?'){ // check if player use hint
                    letter = GameLogic.hint(word, used_letter);
                    used_letter.add(letter);
                    hint--;
                    game_end = GameLogic.basic_win_logic(used_letter, word, player.name, host.name); // check if win because maybe player use hint on the last alphabet
                }
                else {
                    player.wrong = GameLogic.number_of_guess_wrong(player.wrong, word, letter, used_letter);
                    if (used_letter.contains(letter)){
                        continue;
                    }
                    else{
                        used_letter.add(letter);
                    }
                    GameUI.footer();
                    // check if win or not
                    if (GameLogic.basic_win_logic(used_letter, word, player.name, host.name)){
                        game_end = true;
                    }
                    // check if lose or not
                    else if (GameLogic.basic_lose_logic(player.wrong, word, player.name, host.name)){
                        game_end = true;
                    }
                }
            }
            do {
                GameUI.print("\nYou want to continue for next game? (y/n): "); 
                ans = input.nextLine();
            } while (!ans.equalsIgnoreCase("y") && !ans.equalsIgnoreCase("n"));
            if (ans.equalsIgnoreCase("n")){
                GameUI.println("");
                continues = false;
            }
        }
    }

    // to convert list to String
    protected static String used_letters(List <Character> used_letter) {
        List <String> usedLetters = new ArrayList<>();
        for (char letter : used_letter) {
            usedLetters.add(String.valueOf(letter));
        }
        return String.join(", ", usedLetters);
    }
}