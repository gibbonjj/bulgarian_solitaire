// Name: James Gibbons
// USC NetID: 2993752268
// CSCI455 PA2
// Fall 2018

import java.util.Scanner;
import java.util.ArrayList;
/**
   class BulgarianSolitaireSimulator
   Simulates the playing of Bulgarian Solitaire.  Accepts two command line arguements -u and -s.
   When -u is turned on, the user can select the starting pile configuration. When -s is selected,
   the user can step through the game by entering the <enter> key.  Any combination of the two can be
   selected.  When no arguments are given, a random card pile is drawn and the game automatically runs
   to completion.
*/

public class BulgarianSolitaireSimulator {

   public static void main(String[] args) {
     
      boolean singleStep = false;
      boolean userConfig = false;

      for (int i = 0; i < args.length; i++) {
         if (args[i].equals("-u")) {
            userConfig = true;
         }
         else if (args[i].equals("-s")) {
            singleStep = true;
         }
      }

      Scanner in = new Scanner(System.in);

      if (singleStep == false && userConfig == false) {
         System.out.println(greetingMsg());
         SolitaireBoard board = new SolitaireBoard();
         System.out.println(initialConfig(board));
         play(board);
         System.out.println("Done!");
      }

      else if (userConfig == true && singleStep == true) {  
         System.out.println(greetingMsg());
         System.out.println(greetingMsgUser());

         ArrayList<Integer> arrayNumList = new ArrayList<Integer>();
         String finalString = "";

         while (finalString.equals("")) {
         
         System.out.println(userPrompt());
         
         String numList = in.nextLine();
         Scanner rawStringInput = new Scanner(numList);
         finalString = validatesNumerical(rawStringInput);
         Scanner stringInputNoLetters = new Scanner(finalString);
         finalString = validatesTotal(stringInputNoLetters);
         
         }

         Scanner stringInputValid = new Scanner(finalString);
         while (stringInputValid.hasNextInt()) {
            arrayNumList.add(new Integer(stringInputValid.nextInt()));
         }
         
         SolitaireBoard board = new SolitaireBoard(arrayNumList);
         System.out.println(initialConfig(board));
         playByStep(board, in);

         System.out.println("Done!");
         
      }
      else if (singleStep == true && userConfig == false) {
         System.out.println(greetingMsg());
         SolitaireBoard board = new SolitaireBoard();
         System.out.println(initialConfig(board));
         playByStep(board, in);
         System.out.println("Done!");
      }
      else {
         System.out.println(greetingMsg());
         System.out.println(greetingMsgUser());

         ArrayList<Integer> arrayNumList = new ArrayList<Integer>();
         String finalString = "";

         while (finalString.equals("")) {
         
         System.out.println(userPrompt());
         
         String numList = in.nextLine();
         Scanner rawStringInput = new Scanner(numList);
         finalString = validatesNumerical(rawStringInput);
         Scanner stringInputNoLetters = new Scanner(finalString);
         finalString = validatesTotal(stringInputNoLetters);
         
         }

         Scanner stringInputValid = new Scanner(finalString);
         while (stringInputValid.hasNextInt()) {
            arrayNumList.add(new Integer(stringInputValid.nextInt()));
         }
         
         SolitaireBoard board = new SolitaireBoard(arrayNumList);
         System.out.println(initialConfig(board));
         play(board);

         System.out.println("Done!");
      }
   }
   
   private static String errorMsg() {
      String errorMsg = "ERROR: Each pile must have at least one card and the total number of cards must be " + SolitaireBoard.CARD_TOTAL;
      return errorMsg;
   }

   private static String greetingMsg() {
      String greetingMsg = "Number of total cards is " + SolitaireBoard.CARD_TOTAL;
      return greetingMsg;
   }

   private static String greetingMsgUser() {
      String greetingMsgUser = "You will be entering the initial configuration of the cards (i.e., how many in each pile).";
      return greetingMsgUser;
   }

   private static String userPrompt() {
      String userPrompt = "Please enter a space-separated list of positive integers followed by newline:";
      return userPrompt;
   }

   private static String initialConfig(SolitaireBoard board) {
      String initialConfig = "Initial configuration: " + board.configString();
      return initialConfig;
   }

   private static void play(SolitaireBoard board) {
      int roundCount = 1;
         while (!board.isDone()) {
            board.playRound();
            System.out.println("[" + roundCount + "] " + "Current configuration: " + board.configString());
            ++roundCount;
         }
   }

   private static void playByStep(SolitaireBoard board, Scanner in) {
      int roundCount = 1;
         while (!board.isDone()) {
            board.playRound();
            System.out.println("[" + roundCount + "] " + "Current configuration: " + board.configString());
            System.out.print("<Type return to continue>");
            String nextnum = in.nextLine();
            ++roundCount;
         }
   }
   /**
      Validates that the entered board does not contain any letters and returns the string
   */
   private static String validatesNumerical(Scanner stringInput) {

         String stringy = "";
         boolean data = true;
            while (data && stringInput.hasNext()) {
               if (stringInput.hasNextInt()) {
                  stringy = stringy + " " + stringInput.nextInt();  
               }
               else {
                  data = false;
                  stringy = "";
                  System.out.println(errorMsg());
               }
            }
            return stringy;
   }
   /**
      Validates that the string board entered does not contain zeros and the total adds up to
      CARD_TOTAL.  Returns the string board
   */
   private static String validatesTotal(Scanner stringInput) {
      String stringy = "";
      int total = 0;
      ArrayList<Integer> arrayNum = new ArrayList<Integer>();
      while (stringInput.hasNextInt()) {
            int data = stringInput.nextInt();
            arrayNum.add(new Integer(data));
            stringy = stringy + " " + data;
         }
         for (int i = 0; i < arrayNum.size(); i++) {
            if (arrayNum.get(i) > SolitaireBoard.CARD_TOTAL || arrayNum.get(i) <= 0) {
               stringy = "";
            }
            else {
               total += arrayNum.get(i);
            }
         }
         if (total != SolitaireBoard.CARD_TOTAL) {
            stringy = "";
         }
         return stringy;
      } 
}
