public class SetPhrase extends Multiplayer2{

    public static void set_phrase() {
        GameUI.println("");
        // to choose add new word/phrase/sentence in which category and level
        GameUI.categorys(phrase);
        int category = GameLogic.choice(first_category, phrase);
        GameUI.level();
        int level = GameLogic.choice(min_level, max_level);
        
        while (true) { 
            try {
                GameUI.print("Please enter the word/phrase/sentence you want to set: ");
                String new_word = HangmanBasic.input.nextLine().toUpperCase().trim();
                if (!new_word.isEmpty()){ // make sure players enter the word is not empty
                    if (category != phrase){ // to make sure player won't add any pharse or sentence in the category that not for phrase or sentence.
                        if (!new_word.matches("[A-Za-z]+")){
                            GameUI.println("In this category you can't add a word that contain space or any symbol.\n" );
                            continue;
                        }
                    }
                    WordLoader.words.get(category - 1).get(level - 1).add(new_word);
                    break;
                }
                else {
                    GameUI.println("The word/phrase/sentence can't be empty.\n");
                }
            } catch (Exception e) {
                GameUI.println("Invalid input");
            }
        }
    }
}