import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class GameLogic {

    public static int choice(int min, int max){
        String choose;
        int number;
        while (true){
            try {
                GameUI.print("Please enter a number " + min + " - " + max + ": ");
                choose = HangmanBasic.input.nextLine();
                number = Integer.parseInt(choose);
                if (number <= max && number >= min){
                    break;
                }
                else{
                    GameUI.println("\nInvalid input, please enter a number " + min + " - " + max);
                }
            } catch (Exception e) {
                GameUI.println("\nInvalid input, please enter a number " + min + " - " + max);
            }
        }
        return number;
    }

    // to setup multiplayer version like go to which multiplayer hangman version
    public static void setup_multiplayer(int n_Player) {
        final int computer_generate_words = 1;
        final int player_generate_words = 2;
        final int back_to_menu = 3;
        int choose = 0;
        while (choose != back_to_menu) { 
            try {
                GameUI.println("1. Computer romdom choose a words.");
                GameUI.println("2. Player choose words");
                GameUI.println("3. Back to menu");
                choose = choice(computer_generate_words, back_to_menu);
                switch (choose) {
                    case computer_generate_words:
                        GameUI.footer();
                        Multiplayer1.multiplayer1(n_Player);    
                        break;
                    case player_generate_words:
                        GameUI.footer();
                        Multiplayer2.multiplayer2(n_Player);
                        break;
                    case back_to_menu:
                        break;
                    default:
                        GameUI.println("Invalid input.");
                }
            } catch (Exception e) {
                GameUI.println("Invalid input, please enter 1 or 2.");
            }
        }
    }

    public static void get_player_name(int n_player, List <Player> players, List <Character> used_letter) {
        // to get every player name
        for (int i = 0; i < n_player; i++) {
            int wrong = 0;
            int correct = 0;
            int hint = 1;
            String name;
            String mask = "";
            while(true){
                GameUI.print("Please enter player" + (i+1) + " name: ");
                name = HangmanBasic.input.nextLine();
                if (!name.trim().isEmpty()){
                    break;
                }
            }
            Player player = new Player(name, wrong, correct, hint, HangmanBasic.use_letter, mask);
            players.add(player);
        }
    }

    public static void initialize(List <Player> players, List <Player> loser, List <Character> use_letter, String word) {
        // initialize
        
        // to store all player detail into players from l_player 
        for (Player player : loser){
            players.add(player);
        }
        
        loser.clear();
        
        // to reset number of player guess wrong and correct to 0
        for (Player player : players){
            use_letter = new ArrayList<>();
            player.wrong = 0;
            player.correct = 0;
            player.hint = 1;
            player.use_letter = use_letter;
            player.mask = GameLogic.hide_sentences(use_letter, word);
        }
    }

    // to choose which player to choose the question
    public static Player host(List <Player> players){
        int index;
        String player_number;
        while (true) {
            try {
                for (index = 0; index < players.size(); index++) {
                    GameUI.println((index + 1) + ". " + players.get(index).name);
                }
                GameUI.print("Please select a player to guess the word (enter the number, not name): ");
                player_number = HangmanBasic.input.nextLine().trim();
                index = Integer.parseInt(player_number) - 1;
                if ((index < players.size() && index >= 0)){
                    break;
                }
            } 
            catch (Exception e) {
                GameUI.println("Invalid input.");
            } 
        }
        return players.get(index);
    }

    public static String word(List<List<List<String>>> words, int category, int level){
        List <String> word = words.get(category - 1).get(level - 1);
        int index = choice(1, word.size());
        return word.get(index - 1);
    }

    // change the word or sentence to a series of "_", if got symbol or number will display out the symbol or number 
    public static String hide_sentences (List <Character> used_letter, String word){
        List <String> hide_letters = new ArrayList<>();
        String display;
        char[] words = word.toCharArray(); // toCharArray() means change the String to an char array
        for (char letter : words){
            String string_letter = String.valueOf(letter); // change char to string because .contains only for String
            if (used_letter.contains(letter)){
                display = string_letter;
                hide_letters.add(display);
            }
            else if (!string_letter.matches("[A-Za-z]+")){ // if letter not a alphabet
                display = string_letter;
                hide_letters.add(display);
            }
            else { // if letter haven't guess by player
                display = "_";
                hide_letters.add(display);
            }
        }
        String hide_letter = String.join(",", hide_letters).replace(",", "");// convert the hide_letters array to String without ","
        return hide_letter;
    }

    // for HangmanBasic project only
    public static char letter() {
        char letter;
        while (true) { 
            try {
                GameUI.print("Guess a letter: ");
                // to change the letter player input to uppercase and only get the first letter if player input more than one letter.
                letter = HangmanBasic.input.nextLine().toUpperCase().charAt(0);       
                if (String.valueOf(letter).matches("[A-Za-z]+")) { // .matches only can use on String
                    break;
                }
            } catch (Exception e) {
                GameUI.println("Invalid input.");
            }
        }
        return letter;
    }

    // to get what letter player guess
    public static char letter(int hints) {
        char letter;
        while (true) { 
            try {
                GameUI.print("Guess a letter: ");
                // to change the letter player input to uppercase and only get the first letter if player input more than one letter.
                letter = HangmanBasic.input.nextLine().toUpperCase().charAt(0);       
                if (letter == '?'){ // player enter "?" to use hint
                    if (hints == 0){
                        GameUI.println("No more hint.");
                    }
                    else{
                        return letter;
                    }
                }
                else if (String.valueOf(letter).matches("[A-Za-z]+")) { // .matches only can use on String
                    break;
                }
            } catch (Exception e) {
                GameUI.println("Invalid input.");
            }
        }
        return letter;
    }

    // to get how many time player guess wrong
    public static int number_of_guess_wrong(int number_of_guess_wrong, String word, char letter, List <Character> used_letter){
        String string_letter = String.valueOf(letter);
        if ((word.contains(string_letter)) || used_letter.contains(letter)){ // don't change number_of_guess_wrong if guess correct or guess before
            return number_of_guess_wrong;
        }
        else if (!word.contains(string_letter)){
            number_of_guess_wrong++;
        }
        return number_of_guess_wrong;
    }

    // to get how many time player guess correct
    public static int number_of_guess_correct(int number_of_guess_correct, String word, char letter, List <Character> used_letter){
        String string_letter = String.valueOf(letter);
        if ((word.contains(string_letter)) && used_letter.contains(letter)){
            return number_of_guess_correct;
        }
        else if (word.contains(string_letter)){
            number_of_guess_correct++;
        }
        return number_of_guess_correct;
    }

    // if player use hint -> random get a alphabet from word that haven't guess by player
    public static char hint(String word, List <Character> used_letter) {
        Random random = new Random();
        while (true) { 
            int index = random.nextInt(word.length());
            char letter = word.charAt(index);
            if (!used_letter.contains(letter)){
                return letter;
            }
        }
    }

    // check if win or not
    public static boolean basic_win_logic(List <Character> used_letter, String word, String name) {
        if (hide_sentences(used_letter, word).equals(word.trim())){  // check if the hide_sentences is all match with the word
            GameUI.display_win(name, word);
            return true;    
        }
        return false;
    }

    public static boolean basic_win_logic(List <Character> used_letter, String word, String name, String host) {
        if (hide_sentences(used_letter, word).equals(word.trim())){  // check if the hide_sentences is all match with the word
            GameUI.display_win(name, host, word);
            return true;    
        }
        return false;
    }

    // to check is lose or not
    public static boolean basic_lose_logic(int number_of_guess_wrong, String word, String name){
        if (number_of_guess_wrong >= 6){ 
            GameUI.display_lose(name, word);
            GameUI.display_hangman(number_of_guess_wrong);
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean basic_lose_logic(int number_of_guess_wrong, String word, String name, String host){
        if (number_of_guess_wrong >= 6){ 
            GameUI.display_lose(name, host, word);
            GameUI.display_hangman(number_of_guess_wrong);
            return true;
        }
        else{
            return false;
        }
    }

    // to check is win or not (for multiplayer)
    public static boolean multiplayer_win_logic(List <Player> players, List <Player> loser, int player_index, List <Character> used_letter, String word) {
        if (hide_sentences(used_letter, word).equals(word.trim())){ // check if the hide_sentences is all match with the word
            GameUI.multiplayer_display_win(word, players.get(player_index).name);
            GameUI.println("\n===== Ranking =====");
            // calculate and print user ranking that player haven't guess wrong 6 times
            GameLogic.calculator_ranking(players);
            // calculate and print user ranking that player guess wrong 6 times
            GameLogic.calculator_lose_ranking(loser, players.size());
            return true;
        }
        return false;
    }

    // to check is lose or not (for multiplayer)
    public static boolean multiplayer_lose_logic(List <Player> players, List <Player> loser, int player_index, List <Character> used_letter, String word) {
        if (players.get(player_index).wrong >= 6){
            GameUI.multiplayer_display_lose(players.get(player_index).name);
            GameUI.display_hangman(players.get(player_index).wrong);
            loser.add(players.get(player_index));
            players.remove(players.get(player_index));
        }
        if (players.isEmpty()){
            GameUI.println("===== Game Over! =====");
            GameUI.println("The word is: " + word);
            GameUI.println("\n===== Ranking =====");
            // calculate user ranking (follow by who use out the chance first)
            calculator_lose_ranking(loser, players.size());
            return true;
        }
        return false;
    }

    // to calculate the ranking follow by number of guess correct (from big to small)
    // if number of correct is same then follow by number of wrong (from small to big)
    public static void calculator_ranking(List <Player> players) {
        int rank = 1;
        players.sort(Comparator.comparingInt((Player p) -> p.correct).reversed().thenComparingInt(p -> p.wrong));
        for (int i = 0; i < players.size(); i++) {
            GameUI.println("No." + rank + ": " + players.get(i).name + " guess wrong " + players.get(i).wrong + " times, guess correct " + players.get(i).correct + " times.");
            rank++;
        }
    }

    // to print out the player ranking (for those players get 6 wrong guess (from latest "out"))
    public static void calculator_lose_ranking(List <Player> players, int player_w) {
        for (int i = players.size() - 1; i >= 0; i--) {
            GameUI.println("No." + (player_w + 1) + ": " + players.get(i).name + " guess wrong " + players.get(i).wrong + " times, guess correct " + players.get(i).correct + " times.");
            player_w++;
        }
    }
}
