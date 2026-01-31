/*
Group Member: Ng Kok Bing
            : Qin Zi Yi
            : Tang Ying Xiang
            : Cheng Hao Nan
 */

public class HangmanAdditional extends HangmanBasic{

    public static void main(String[] args){

        final int single_player = 1;
        final int two_players = 2;
        final int multiplayer = 3;
        final int set_pharse = 4;
        final int exit_game = 5;
        
        final int min_player = 2;
        final int max_player = 5;

        int choose = 0;
        int number_of_player;

        while (choose != exit_game) {
            GameUI.println("===== Welcome to Hangman game! =====");
            GameUI.println("1. Single player");
            GameUI.println("2. 2 Players");
            GameUI.println("3. Multiplayer");
            GameUI.println("4. Set word or phrase");
            GameUI.println("5. Exit game");

            choose = GameLogic.choice(single_player, exit_game);

            switch (choose) {
                case single_player:
                    HangmanBasic.hangmanBasic();
                    break;
                case two_players:
                    TwoPlayer.two_players();
                    break;
                case multiplayer:
                    GameUI.footer();
                    number_of_player = GameLogic.choice(min_player, max_player);
                    GameUI.footer();
                    GameUI.println("1. Computer generate words");
                    GameUI.println("2. Player generate words");
                    GameLogic.setup_multiplayer(number_of_player);
                    break;
                case set_pharse:
                    SetPhrase.set_phrase();
                    break;
                case exit_game:
                    GameUI.println("Thank you for playing.");
                    break;
                default:
                    GameUI.println("Invalid choice.");
            }
        }
    }
}
