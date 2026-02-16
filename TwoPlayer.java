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

public class TwoPlayer extends Multiplayer2{

    public static void two_players(){

        List<List<List<String>>> words = WordLoader.words;

        continues = true;

        String name = "";
        int wrong = 0;
        int correct = 0;
        hint = 1;
        String mask = "";
        Player host = new Player(name, wrong, correct, hint, use_letter, mask);
        Player player = new Player(name, wrong, correct, hint, use_letter, mask);

        while(true){
            GameUI.print("\nPlease enter host name: ");
            name = HangmanBasic.input.nextLine();
            if (!name.trim().isEmpty()){
                host = new Player(name, wrong, correct, hint, use_letter, mask);
                break;
            }
        }

        while(true){
            GameUI.print("\nPlease enter player name: ");
            name = HangmanBasic.input.nextLine();
            if (!name.trim().isEmpty()){
                player = new Player(name, wrong, correct, hint, use_letter, mask);
                break;
            }
        }

        // game start and repeat from here if game over and player choose to continue
        while (continues == true) {
            player.correct = 0;
            player.wrong = 0;
            player.hint = 1;
            use_letter = new ArrayList<>();
            player.use_letter = use_letter;

            GameUI.println("\n===== Welcome to Hangman game! =====");
            GameUI.println(host.name + " please choose a category (Enter number).");
            // let host get the word from WordLoader
            GameUI.categorys(last_category);
            int category = GameLogic.choice(first_category, last_category);
            GameUI.print("\n" + host.name + " please choose a level (Enter number).");
            GameUI.level();
            int level = GameLogic.choice(min_level, max_level);
            GameUI.println("\n" + host.name + " please choose a word (Enter number).");
            GameUI.words(words, category, level);
            
            word = GameLogic.word(words, category, level).toUpperCase().trim();
            player.mask = GameLogic.hide_sentences(player.use_letter, word);
            boolean game_end = false;

            // round start and if not game over will continue from here for next round
            while (game_end == false) { 
                GameUI.footer();
                GameUI.display_hangman(player.wrong);
                // print out "Word:" and _ or letter that player guess correct
                GameUI.hide_sentence(player.mask);
                // print out left how many time player guess wrong like "Incorrect Guesses: 3/6"
                GameUI.incorrect(player.wrong);
                GameUI.println("Used Letters: " + used_letters(player.use_letter));
                GameUI.println("Hint left: " + player.hint);
                GameUI.footer();
                // the letter player guess
                letter = GameLogic.letter(player.hint);
                if (letter == '?'){ // check if player use hint
                    letter = GameLogic.hint(word, player.use_letter);
                    player.use_letter.add(letter);
                    player.hint--;
                    player.mask = GameLogic.hide_sentences(player.use_letter, word);
                    game_end = GameLogic.basic_win_logic(player.use_letter, word, player.name, host.name); // check if win because maybe player use hint on the last alphabet
                }
                else {
                    player.wrong = GameLogic.number_of_guess_wrong(player.wrong, word, letter, player.use_letter);
                    if (player.use_letter.contains(letter)){ // if the letter have been guess by player
                        continue;
                    }
                    else{
                        player.use_letter.add(letter);
                        player.mask = GameLogic.hide_sentences(player.use_letter, word);
                    }
                    // check if win or not
                    if (GameLogic.basic_win_logic(player.use_letter, word, player.name, host.name)){
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
}