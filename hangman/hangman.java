package hangman;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;  

class Hangman {
  private static String word = "";
  private static String clue = ""; 
  private static int remainingGuesses = 6; 
  private static ArrayList<String> wordList = new ArrayList<String>(); 
  private static ArrayList<String> guessList = new ArrayList<String>(); 
  private static boolean finished = false;

  private final static String possibleGuesses = "abcdefghijklmnopqrstuvwxyz";

  public static void guess(String c) {     
    if (guessList.contains(c)) { 
        System.out.println("Already guessed");
        return;
    }

    if (c.length() == 1 && !possibleGuesses.contains(c)) {
      System.out.println("Guess must be a letter");
      return;
    }
    
    guessList.add(c); 
    
    if (word.equals(c)){
      System.out.println("You guessed it right! You win!!");
      finished = true;
    } else {
      if (word.contains(c)) { 
        for(int i = 0; i < word.length(); i++) { 
            if (word.substring(i,i + 1).equals(c)) {
                clue = clue.substring(0, i) + c + clue.substring(i + 1);
            }
        }
      System.out.println("Correct!");
      if (!clue.equals(word)){ 
        System.out.print("Your Guesses: ");
        System.out.println(guessList);
        System.out.println(displayClue());
        System.out.println("");
      }
      else {
        System.out.println("You guessed it right! You win!!");
        finished = true;
      }
      }
      else {
        remainingGuesses--;
      }  
      hangmanPicture(); 
    }
  }

  public static void hangmanPicture() { 

    System.out.println("");
    System.out.println("Incorrect. Try again");
    System.out.println("You have " + remainingGuesses + " guesses left");
    System.out.println("");
    System.out.print("Your Guesses: ");
    System.out.println(guessList);
    System.out.println("Clue: " + displayClue());

    if (remainingGuesses == 6) {
      System.out.println(" _____");
      System.out.println(" |");
      System.out.println(" |");
      System.out.println(" |");
      System.out.println("_|_");
    }
      
    if (remainingGuesses == 5) {
      System.out.println(" _____");
      System.out.println(" |  {_}");
      System.out.println(" |");
      System.out.println(" |");
      System.out.println("_|_");
    }
    if (remainingGuesses == 4) {
      System.out.println(" _____");
      System.out.println(" |  {_}");
      System.out.println(" |   |");
      System.out.println(" |");
      System.out.println("_|_");
    }
    if (remainingGuesses == 3) {
      System.out.println(" _____");
      System.out.println(" |  {_}");
      System.out.println(" |  /|");
      System.out.println(" |");
      System.out.println("_|_");
    }
    if (remainingGuesses == 2) {
      System.out.println(" _____");
      System.out.println(" |  {_}");
      System.out.println(" |  /|\\");
      System.out.println(" |");
      System.out.println("_|_");
    }
    if (remainingGuesses == 1) {
      System.out.println(" _____");
      System.out.println(" |  {_}");
      System.out.println(" |  /|\\");
      System.out.println(" |  /");
      System.out.println("_|_");
    }
    if (remainingGuesses == 0) {
      System.out.println(" _____");
      System.out.println(" |  {_}");
      System.out.println(" |  /|\\");
      System.out.println(" |  / \\");
      System.out.println("_|_");
      System.out.println("GAME OVER! The word was " + word);
    }
  }

  public static String displayClue() {
    String res = "";

    for (int i = 0; i < clue.length(); i++) {
      res += clue.substring(i, i + 1) + " ";
    }

    return res;
  }
  public static void main(String[] args) { 

    try
      {
         Scanner input = new Scanner(new File("/Users/matthewglasser/csa_final/hangman/words.txt"));
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
    for(int j = 0; j < word.length(); j++) { 
        clue += "_"; 
    }

    System.out.println("");
    System.out.println("Clue: " + displayClue());
    while(remainingGuesses > 0 && ! finished) { 
        System.out.println("The word has " + word.length() + " letters");
        System.out.println("");
        System.out.println("Guess a letter or a word");
        String c = s.nextLine().toLowerCase();
        
        guess(c);
    }

    s.close();
    
  }
}