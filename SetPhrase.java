public class SetPhrase extends Multiplayer2{

    public static void set_phrase() {
        GameUI.println("");
        // to choose add new word/phrase/sentence in which category and level
        GameUI.categorys();
        int category = GameLogic.choice(first_category, phrase);
        GameUI.level();
        int level = GameLogic.choice(min_level, max_level);
        
        while (true) { 
            try {
                GameUI.print("Please enter the word/phrase/sentence you want to set: ");
                String new_word = HangmanBasic.input.nextLine().toUpperCase().trim();
                if (!new_word.isEmpty()){
                    WordLoader.words.get(category - 1).get(level - 1).add(new_word);
                    break;
                }   
            } catch (Exception e) {
                GameUI.println("Invalid input");
            }
        }
    }
}
