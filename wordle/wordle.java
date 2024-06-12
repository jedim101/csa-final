package wordle;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

class Wordle {
  private static ArrayList<String> wordList = new ArrayList<String>();
  private static String word;
  private static ArrayList<String> guesses = new ArrayList<String>();
  private static ArrayList<Integer> greenIndices = new ArrayList<Integer>();
  private static ArrayList<String> yellowLetters = new ArrayList<String>();

  public static void display() {
    System.out.println();

    for (String guess : guesses) {
      greenIndices.clear();
      yellowLetters.clear();

      String[] result = new String[word.length()];
      boolean[] matched = new boolean[word.length()];

      // First pass to mark correct positions (Green)
      for (int i = 0; i < guess.length(); i++) {
        if (guess.substring(i, i + 1).equals(word.substring(i, i + 1))) {
          result[i] = "\033[42m";
          matched[i] = true;
          greenIndices.add(i);
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
              yellowLetters.add(guess.substring(i, i + 1));
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

    System.out.print("Hard mode? y/n ");
    boolean hardMode = s.nextLine().toLowerCase().equals("y");

    while (!(guesses.contains(word) || guesses.size() == 6)) {
      boolean isValid = false;

      System.out.println("Guess a 5 letter word");
      String g = "";

      while (!isValid) {
        g = s.nextLine().toLowerCase();

        isValid = true;
        if (g.length() != 5) {
          System.out.println("Guess must be 5 letters. Try again");
          isValid = false;
        }

        if (hardMode) {
          ArrayList<String> temp = new ArrayList<String>(yellowLetters);

          for (int i = 0; i < g.length(); i++) {
            // Check for missing greens in hard mode
            if (greenIndices.contains(i) && !g.substring(i, i + 1).equals(word.substring(i, i + 1))) {
              System.out.println("Must contain \"" + word.substring(i, i + 1) + "\" in the correct spot (hard mode)");
              isValid = false;
            } else {
              temp.remove(g.substring(i, i + 1));
            }
            
          }

          if (temp.size() > 0) {
            System.out.println("Must contain all yellow letters (hard mode)");
            isValid = false;
          }
        }

      }

      guesses.add(g);
      display();
    } 

    if (guesses.contains(word)) System.out.println("You Win!!!!");
    else if (guesses.size() == 6) System.out.print("loser\nThe correct word was: " + word);

    s.close();
  }
}
