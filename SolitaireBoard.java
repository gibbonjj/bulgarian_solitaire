// Name: James Gibbons
// USC NetID: 2993752269
// CSCI455 PA2
// Fall 2018

import java.lang.Integer;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

/*
  class SolitaireBoard
  The board for Bulgarian Solitaire.  You can change what the total number of cards is for the game
  by changing NUM_FINAL_PILES, below.  Don't change CARD_TOTAL directly, because there are only some values
  for CARD_TOTAL that result in a game that terminates.
  (See comments below next to named constant declarations for more details on this.)
*/
public class SolitaireBoard {
   
   public static final int NUM_FINAL_PILES = 9;
   // number of piles in a final configuration
   // (note: if NUM_FINAL_PILES is 9, then CARD_TOTAL below will be 45)
   
   public static final int CARD_TOTAL = NUM_FINAL_PILES * (NUM_FINAL_PILES + 1) / 2;
   // bulgarian solitaire only terminates if CARD_TOTAL is a triangular number.
   // see: http://en.wikipedia.org/wiki/Bulgarian_solitaire for more details
   // the above formula is the closed form for 1 + 2 + 3 + . . . + NUM_FINAL_PILES

   // Note to students: you may not use an ArrayList -- see assgt description for details.
   
   
   /**
      Representation invariant:
         numPiles <= CARD_TOTAL > 0
         Sum of all Piles == CARD_TOTAL
         All cardPiles[i] > 0 for i < numPiles
         All of the numPiles in the array are contained within the partial array cardPiles[0]
         to cardPiles[numPiles - 1] 
   */
   private int[] cardPiles = new int[CARD_TOTAL];
   private int numPiles;
   private Random generator = new Random();
   private int roundNum;

 
   /**
      Creates a solitaire board with the configuration specified in piles.
      piles has the number of cards in the first pile, then the number of cards in the second pile, etc.
      PRE: piles contains a sequence of positive numbers that sum to SolitaireBoard.CARD_TOTAL
   */
   public SolitaireBoard(ArrayList<Integer> piles) {

      roundNum = 0;
      numPiles = piles.size();
      Integer[] tempArray = piles.toArray(new Integer[piles.size() - 1]);
      
      for (int i = 0; i < tempArray.length; i++) {
         cardPiles[i] = tempArray[i];
      }

      assert isValidSolitaireBoard();   // sample assert statement (you will be adding more of these calls)
   }
 
   
   /**
      Creates a solitaire board with a random initial configuration.
   */
   public SolitaireBoard() {

      numPiles = generator.nextInt(CARD_TOTAL - 1) + 1;
      cardPiles[numPiles - 1] = CARD_TOTAL - numPiles;
      roundNum = 0;

      for (int i = 0; i <= numPiles - 2; i++) {
         cardPiles[i] = generator.nextInt(CARD_TOTAL - numPiles);
      }

      Arrays.sort(cardPiles, 0, numPiles - 1);

      for (int i = numPiles - 1; i >= 0; i--) {
         if (i != 0) {
            cardPiles[i] = cardPiles[i] - cardPiles[i-1];
         }
         else {
            cardPiles[i] = cardPiles[i] - 0;
         }
      }

      for (int i = 0; i < numPiles; i++) {
         cardPiles[i] = cardPiles[i] + 1;
      }

      assert isValidSolitaireBoard();
   }
  
   /**
      Plays one round of Bulgarian solitaire.  Updates the configuration according to the rules
      of Bulgarian solitaire: Takes one card from each pile, and puts them all together in a new pile.
      The old piles that are left will be in the same relative order as before, 
      and the new pile will be at the end.
   */
   public void playRound() {

      int tempCounter = 0;

      for (int i = 0; i < numPiles; i++) {
         cardPiles[i] = --cardPiles[i];
         tempCounter++;
      }

      for (int i = 0; i < numPiles; i++) {
         if (cardPiles[i] == 0) {
            for (int j = i; j < numPiles; j++) {
               cardPiles[j] = cardPiles[j + 1];
            }
            numPiles--;
            i--;
         }
      }
      numPiles++;
      cardPiles[numPiles - 1] = tempCounter;
      roundNum++;
      assert isValidSolitaireBoard();
   }
   
   /**
      Returns true iff the current board is at the end of the game.  That is, there are NUM_FINAL_PILES
      piles that are of sizes 1, 2, 3, . . . , NUM_FINAL_PILES, in any order.
   */
   public boolean isDone() {
      assert isValidSolitaireBoard();

      if (numPiles != NUM_FINAL_PILES) {
         return false;
      }
      else {

         int[] testArray = Arrays.copyOfRange(cardPiles, 0, NUM_FINAL_PILES);

         Arrays.sort(testArray);
         int[] testAgainst = new int[NUM_FINAL_PILES];

         for (int i = 0; i < NUM_FINAL_PILES; i ++) {
            testAgainst[i] = i + 1;
         }

         return Arrays.equals(testAgainst, testArray);
      }

   }
   
   /**
      Returns current board configuration as a string with the format of
      a space-separated list of numbers with no leading or trailing spaces.
      The numbers represent the number of cards in each non-empty pile.
   */
   public String configString() {

      assert isValidSolitaireBoard();
      String boardString = "";

      for (int i = 0; i < numPiles; i++) {
         boardString += " " + cardPiles[i];
      }

      return boardString;
   }
   
   
   /**
      Returns true iff the solitaire board data is in a valid state
      (See representation invariant comment for more details.)
   */
   private boolean isValidSolitaireBoard() {

      boolean isValid = true;
      int totalTest = 0;

      for (int i = 0; i < numPiles; i++) {
         totalTest += cardPiles[i];
         if (cardPiles[i] <= 0) {isValid = false;}
      }

      if (totalTest != CARD_TOTAL) {isValid = false;}

      return isValid;
   }
}
