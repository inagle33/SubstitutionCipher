/**
 * This program will give the user three choices. Depending on the choice chosen the user will be asked 
 * for two files and for a shift amount. Then the program will shift the code by the shift amount to 
 * encode the file, or shift the file backwards by the shift amount to decode the file.
 * 
 * @author Isaac Nagle
 * @version 11092022
 */
import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SubstitutionCipher {

    /**
     * Private constants used to shift characters for the substitution cipher.
     */
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";

    /**
     * Constructs a new String where each letter in the String input is shifted
     * by the amount shift to the right, preserving whether the original
     * character was uppercase or lowercase. For example, the String "ABC" with
     * shift 3 would cause this method to return "DEF". A negative value should
     * shift to the left. For example, the String "ABC" with shift -3 would
     * cause this method to return "XYZ". Punctuation, numbers, whitespace and
     * other non-letter characters should be left unchanged.
     *
     * @param input
     *            String to be encrypted
     * @param shift
     *            Amount to shift each character of input to the right
     * @return the encrypted String as outlined above
     */
    public static String shift(String input, int shift) {
        StringBuilder sb = new StringBuilder(); //create StringBuilder object
        sb.append(input); //append StringBuilder to be the input
        for (int i = 0; i < sb.length(); i++) { //for loop to iterate through StringBuilder
           if (Character.isLetter(sb.charAt(i))) {
              //if statement to check if character is a letter
                    if (Character.isUpperCase(sb.charAt(i))) { //if statement if character is uppercase 
                       int letterIndex = UPPERCASE.indexOf(sb.charAt(i));
                       //int with index of letter in UPPERCASE based on character in StringBuilder
                       int newIndex = (letterIndex + shift + UPPERCASE.length()) % UPPERCASE.length();
                       //int that gets index of new letter after shift is applied
                       char newLetter = UPPERCASE.charAt(newIndex);
                       //gets the new letter from the shift
                       sb.setCharAt(i, newLetter); 
                       //changes old character in StringBuilder to new one after shift is applied
                    }
                    else { //if statement if character is lowercase 
                       int letterIndex = LOWERCASE.indexOf(sb.charAt(i));
                       //int with index of letter in UPPERCASE based on character in StringBuilder
                       int newIndex = (letterIndex + shift + LOWERCASE.length()) % LOWERCASE.length();
                       //int that gets index of new letter after shift is applied
                       char newLetter = LOWERCASE.charAt(newIndex);
                       //gets the new letter from the shift
                       sb.setCharAt(i, newLetter); 
                       //changes old character in StringBuilder to new one after shift is applied
                    }
           }
        }
        return sb.toString(); //returns StringBuilder
    }

    /**
     * Displays the message "promptMsg" to the user and reads the next full line
     * that the user enters. If the user enters an empty string, reports the
     * error message "ERROR! Empty Input Not Allowed!" and then loops,
     * repeatedly prompting them with "promptMsg" to enter a new string until
     * the user enters a non-empty String
     *
     * @param in
     *            Scanner to read user input from
     * @param promptMsg
     *            Message to display to user to prompt them for input
     * @return the String entered by the user
     */
    public static String promptForString(Scanner scnr, String promptMsg) {
        System.out.print(promptMsg); //prints promt
        String input = scnr.nextLine(); //creates string and assigns it with next input
        while (input.isEmpty()) { //while loop to ensure user enters a string
           System.out.println("ERROR! Empty Input Not Allowed!");
           System.out.print(promptMsg);
           input = scnr.nextLine();
        }
        return input; //returns inputted value from scanner
    }

    /**
     * Opens the file inFile for reading and the file outFile for writing,
     * reading one line at a time from inFile, shifting it the number of
     * characters given by "shift" and writing that line to outFile. If an
     * exception occurs, must report the error message: "ERROR! File inFile not
     * found or cannot write to outFile" where "inFile" and "outFile" are the
     * filenames given as parameters.
     *
     * @param inFile
     *            the file to be transformed
     * @param outFile
     *            the file to write the transformed output to
     * @param shift
     *            the amount to shift the characters from inFile by
     * @return false if an exception occurs and the error message is written,
     *         otherwise true
     */
    public static boolean transformFile(String inFile, String outFile, int shift) {
        boolean W = true; //variable assignments
        String contents;
        try {
           File in = new File(inFile); //open inFile
           Scanner inScan = new Scanner(in); //create Scanner object
           File out = new File(outFile); //open outFile
           PrintWriter outWrite = new PrintWriter(out); //create PrintWriter object
           while (inScan.hasNext()) { //while loop to check if inFile has more contents
              contents = inScan.nextLine(); //assigns contents with next line of file
              contents = shift(contents, shift); //method call to shift the contents of the file
              outWrite.println(contents); //writes shifted contents to outFile
           }
           inScan.close(); //close Scanner
           outWrite.close(); //close PrintWriter
        } catch (FileNotFoundException e) {
           W = false;
           System.out.println("ERROR! File " + inFile + " not found or cannot write to " + outFile);
        }
        return W; //returns true
    }

    /**
     * Prompts the user to enter a single character choice. The only allowable
     * values are 'E', 'D' or 'Q'. All other values are invalid, including all
     * values longer than one character in length, however the user is allowed
     * to enter values in either lower or upper case. If the user enters an
     * invalid value, the method displays the error message "ERROR! Enter a
     * valid value!" and then prompts the user repeatedly until a valid value is
     * entered. Returns a single uppercase character representing the user's
     * choice.
     *
     * @param scnr
     *            Scanner to read user choices from
     * @return the user's choice as an uppercase character
     */
    public static char getChoice(Scanner scnr) {
        String choice = promptForString(scnr, "Enter your choice: "); //method call to get choice
        char letter = choice.toUpperCase().charAt(0); //gets first character in uppercase 
        while (((letter != 'E') && (letter != 'D') && (letter != 'Q')) || (choice.length() > 1)) {
           //while loop to check for valid values
              System.out.println("ERROR! Enter a valid value!");
              choice = promptForString(scnr, "Enter your choice: ");
              letter = choice.toUpperCase().charAt(0);
        }
        return letter; //returns user's choice
    }

    /**
     * Displays the menu of choices to the user.
     */
    public static void displayMenu() {
        System.out.println("[E]ncode a file"); //user's options
        System.out.println("[D]ecode a file");
        System.out.println("[Q]uit");
    }

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        displayMenu(); //method call to show user their options
        char decision = getChoice(scnr); //variable that holds user's choice
        while (decision != 'Q') { //while loop for if user does not quit
           String inputFile = promptForString(scnr, "Enter an input file: ");
           String outputFile = promptForString(scnr, "Enter an output file: ");
           int shiftAmount = Integer.parseInt(promptForString(scnr, "Enter a shift amount: "));
           //method calls to get values for files and shift amount
           if (decision == 'E') { //if statement for if user chooses to encode
              transformFile(inputFile, outputFile, shiftAmount); //method call to alter file
           }
           else { //if statement for if user chooses to decode
              transformFile(inputFile, outputFile, (-shiftAmount)); //method call to alter file
           }
           System.out.println("Finished writing to file."); //tells user that operation is finished
           System.out.println(); //newline
           displayMenu(); //method call to show user their options again
           decision = getChoice(scnr); //variable that holds user's next choice
        }
        System.out.println(); //newline
        System.out.println("Goodbye!"); //if user decides to quit meaning variable decision == 'Q'

        scnr.close(); //close scanner object
    }

}
