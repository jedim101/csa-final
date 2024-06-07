package wordle;


import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

class Wordle {
  private static ArrayList<String> wordList = new ArrayList<String>();
  private static String word;
  private static ArrayList<String> guesses = new ArrayList<String>();

  public static void display() {
    System.out.println();

    for (String guess : guesses) {

      String[] result = new String[word.length()];
      boolean[] matched = new boolean[word.length()];

      // First pass to mark correct positions (Green)
      for (int i = 0; i < guess.length(); i++) {
        if (guess.substring(i, i + 1).equals(word.substring(i, i + 1))) {
          result[i] = "\033[42m";
          matched[i] = true;
        } else {
          result[i] = "\033[40m";  // default to Gray, will check for Yellow next
        }
      }

      // Second pass to mark letters that are correct but in the wrong position (Yellow)
      for (int i = 0; i < guess.length(); i++) {
        if (!guess.substring(i, i + 1).equals(word.substring(i, i + 1))) {
          for (int j = 0; j < word.length(); j++) {
            if (guess.substring(i, i + 1).equals(word.substring(j, j + 1)) && !matched[j] && result[i].equals("\033[40m")) {
              result[i] = "\033[43m";
              matched[j] = true;
              break;
            }
          }
        }
      }

      for (int i = 0; i < result.length; i++) {
        System.out.print(result[i] + " " + guess.substring(i, i + 1) + " \033[0m");
      }

      System.out.println();

    }
  }

  public static void main(String[] args) {

    try
      {
        Scanner input = new Scanner(new File("/Users/matthewglasser/csa_final/wordle/words.txt"));
        while (input.hasNextLine())
         {
          wordList.add(input.nextLine());
         }
         input.close();
      }
      catch (Exception e)
      {
        System.out.println("Error reading or parsing word list file.");
      }

    Scanner s = new Scanner(System.in);  
    word = wordList.get((int) (Math.random() * wordList.size()));

    while (!(guesses.contains(word) || guesses.size() == 6)) {
      System.out.println("Guess a 5 letter word");
      String g = s.nextLine().toLowerCase();

      while (g.length() != 5) {
        System.out.println("Guess must be 5 letters. Try again");
        g = s.nextLine().toLowerCase();
      }

      guesses.add(g);
      display();
    }

    if (guesses.contains(word)) System.out.println("You Win!!!!");
    else if (guesses.size() == 6) System.out.print("loser\nThe correct word was: " + word);

    s.close();
  }
}
