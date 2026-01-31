import java.util.List;
import java.util.Scanner;

public class GameUI {

    public static final Scanner input = new Scanner(System.in);

    public static void println(String a){
        System.out.println(a);
    }

    public static void print(String a){
        System.out.print(a);
    }
    
    public static void footer (){
        println("------------------------------------");
    }

    public static void categorys () {
        println("Category: ");
        println("1. Fruit");
        println("2. Animal");
        println("3. Country");
        println("4. Sport");
        println("5. Phrase or sentence");
    }

    public static void level () {
        println("\nLevel: ");
        println("1. Easy");
        println("2. Medium");
        println("3. Hard");
    }

    public static void hide_sentence (List <Character> used_letter, String word){
        print("Word:");
        println(GameLogic.hide_sentences(used_letter, word));
    }

    public static void incorrect (int number_of_guess_wrong){
        println("Incorrect Guesses: " + number_of_guess_wrong + "/6");
    }

    public static void display_win (String name){
        println("===== Game Over! =====");
        println("Congratulations, " + name + " have WON!");
    }

    public static void display_win (String name, String host){
        println("===== Game Over! =====");
        println("Congratulations, " + name + " have WON!");
        println("Sorry, " + host + " LOST! " + name + " guess correct the word.");
    }

    public static void display_lose (String name, String word){
        println("===== Game Over! =====");
        println("Sorry, " + name + " LOST! The word was: " + word);
    }

    public static void display_lose (String name, String host, String word){
        println("===== Game Over! =====");
        println("Sorry, " + name + " LOST! The word was: " + word);
        println("Congratulations, " + host + " have WON!");
    }

    public static void multiplayer_display_win (String word, String name){
        println("===== Game Over! =====");
        println("Congratulations," + name + " guess correct!");
    }

    public static void multiplayer_display_lose (String name){
        println("Sorry," + name + " LOST!");
    }

    // print out all the word in the level that in the category 
    public static void words(List<List<List<String>>> words, int category, int level) {
        List <String> word = words.get(category - 1).get(level - 1);
        for (int i = 0; i < word.size(); i++) {
            println((i + 1) + ". " + word.get(i));
        }
    }

    // display the part of hangman figure as a tally mark  
    public static void display_hangman (int number_of_guess_wrong){
        switch (number_of_guess_wrong) {
            case 0:
                println("+---+");
                println("|   |");
                println("|   ");
                println("|   ");
                println("|   ");
                println("|   ");
                println("=======");
                break;
            case 1:
                println("+---+");
                println("|   |");
                println("|   O");
                println("|   ");
                println("|   ");
                println("|   ");
                println("=======");
                break;
            case 2:
                println("+---+");
                println("|   |");
                println("|   O");
                println("|   |");
                println("|   ");
                println("|   ");
                println("=======");
                break;
            case 3:
                println("+---+");
                println("|   |");
                println("|   O");
                println("|  /|");
                println("|   ");
                println("|   ");
                println("=======");
                break;
            case 4:
                println("+---+");
                println("|   |");
                println("|   O");
                println("|  /|\\");
                println("|   ");
                println("|   ");
                println("=======");
                break;
            case 5:
                println("+---+");
                println("|   |");
                println("|   O");
                println("|  /|\\");
                println("|  /");
                println("|   ");
                println("=======");
                break;
            default:
                println("+---+");
                println("|   |");
                println("|   O");
                println("|  /|\\");
                println("|  / \\");
                println("|   ");
                println("=======");
                break;
        }
    }
}
