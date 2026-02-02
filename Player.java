import java.util.List;
public class Player {
  public String name;
  public int wrong;
  public int correct;
  public int hint;
  public List <Character> use_letter;
  public String mask;

  public Player(String name, int wrong, int correct, int hint, List <Character> use_letter, String mask) {
    this.name = name;
    this.wrong = wrong;
    this.correct = correct;
    this.hint = hint;
    this.use_letter = use_letter;
    this.mask = mask;
  }
}