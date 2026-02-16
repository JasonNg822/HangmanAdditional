import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordLoader {

    // to add level into category
    public static void add_array(List<List<String>> category, String... word) { // String... means can put multiple Sting inside
        category.add(new ArrayList<>(List.of(word)));
    }

    public static List<List<List<String>>> words = new ArrayList<>();

    static { // static{} means when other file call this file, this file will run static{} first
        List<List<String>> fruit = new ArrayList<>();
        List<List<String>> animal = new ArrayList<>();
        List<List<String>> country = new ArrayList<>();
        List<List<String>> sport = new ArrayList<>();
        List<List<String>> phrase = new ArrayList<>();

        add_array(fruit, "apple", "banana", "orange", "kiwi", "grape", "pear", "peach");
        add_array(fruit, "mango", "lychee", "watermelon", "longan", "coconut", "cherry", "papaya");
        add_array(fruit, "strawberry", "jackfruit", "durian", "pineapple", "lemon", "pomelo", "avacado");

        add_array(animal, "cat", "dog", "bird", "fish", "cow", "lion", "horse");
        add_array(animal, "tiger", "elephant", "giraffe", "shark", "zebra", "sheep", "monkey");
        add_array(animal, "mouse", "rabbit", "snake", "chicken", "rhino", "bear", "hippo");

        add_array(country, "singapore", "malaysia", "thailand", "china", "america", "india", "japan");
        add_array(country, "rusia", "australia", "korea", "vietnam", "barzil", "Canada", "france");
        add_array(country, "germany", "bangladesh", "philippnes", "portugal", "finland", "italy", "spain");

        add_array(sport, "jogging", "swimming", "tennis", "badminton", "cycling", "golf", "football");
        add_array(sport, "hockey", "diving", "baseball", "bowling", "basketball", "pickleball", "diving");
        add_array(sport, "fencing", "volleyball", "surfing", "archery", "billiards", "gymnastics", "rugby");

        add_array(phrase, "Hello World!", "Hello Java Programming!");
        add_array(phrase, "I like to jogging.", "Milk tea is my favarite drink!");
        add_array(phrase, "Who are you?", "Are you ok?");

        words.add(fruit);
        words.add(animal);
        words.add(country);
        words.add(sport);
        words.add(phrase);
    }

    // computer random gerenate a word
    public static String get_word(int category, int level) {

        Random random = new Random();

        List<String> category_level = words.get(category - 1).get(level - 1);
        int index = random.nextInt(category_level.size());

        return category_level.get(index);
    }
}