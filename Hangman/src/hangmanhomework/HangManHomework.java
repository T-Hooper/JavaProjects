package hangmanhomework;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class HangManHomework {

    public static Scanner input = new Scanner(System.in);
    public static Random rand = new Random();

    public static ArrayList<String> words = new ArrayList<String>();
    public static ArrayList<Character> currentlyGuessed = new ArrayList<Character>();
    public static String wordChosen = null;
    public static int lettersWrong = 0;

    
    public static void main(String[] args) throws IOException, InterruptedException  {

        chooseWordFromFile();
        
        welcome();
        System.in.read();
        
        
        for (int i = 0; i < wordChosen.length(); i++) {
            currentlyGuessed.add('_');
        }

        boolean containsLetter = false;
        boolean won = false;
        
        
        printImage();
        printWordProgress();

        while (lettersWrong < 7 && !won) {

            System.out.println("");
            System.out.println("Guess a letter:");
            System.out.println("");

            String userInput = input.next().toUpperCase();
            char letterEntered = (userInput.charAt(0));
            
            for (int i = 0; i < wordChosen.length(); i++) {
                if (letterEntered == (wordChosen.charAt(i))) {
                    currentlyGuessed.set(i, letterEntered);
                    containsLetter = true;
                }
            }
            if (!containsLetter) {
                lettersWrong += 1;
            }
            containsLetter = false;

            printImage();   // prints image of hangman
            printWordProgress();    // prints all letters guessed so far

            int numOfLettersCorrect = 0;

            for (int i = 0; i < wordChosen.length(); i++) {

                if (currentlyGuessed.get(i) == (wordChosen.charAt(i))) {
                    numOfLettersCorrect += 1;
                }
            }
            if (numOfLettersCorrect == wordChosen.length()) {

                won = true;
            }

        }
        
        endMessage(won);

    }

    public static void printImage() {
        for (int i = 0; i < 10; i++) {
            System.out.println("");
        }
        System.out.println("__________");
        if (lettersWrong == 0) {
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|_");
        }
        if (lettersWrong == 1) {
            System.out.println("|     |");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|_");
        }
        if (lettersWrong == 2) {
            System.out.println("|     |");
            System.out.println("|     O");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|_");
        }
        if (lettersWrong == 3) {
            System.out.println("|     |");
            System.out.println("|     O");
            System.out.println("|     | ");
            System.out.println("|");
            System.out.println("|_");
        }
        if (lettersWrong == 4) {
            System.out.println("|     |");
            System.out.println("|     O ");
            System.out.println("|     | ");
            System.out.println("|    /");
            System.out.println("|_");
        }
        if (lettersWrong == 5) {
            System.out.println("|     |");
            System.out.println("|     O");
            System.out.println("|     | ");
            System.out.println("|    /|");
            System.out.println("|_");
        }
        if (lettersWrong == 6) {
            System.out.println("|     |");
            System.out.println("|    _O");
            System.out.println("|     | ");
            System.out.println("|    /|");
            System.out.println("| ");
        }
        if (lettersWrong == 7) {
            System.out.println("|     |");
            System.out.println("|    _O/");
            System.out.println("|     | ");
            System.out.println("|    /|");
            System.out.println("|_");
        }
    }

    public static void printWordProgress() {
        System.out.print("            ");
        for (int i = 0; i < wordChosen.length(); i++) {
            System.out.print(currentlyGuessed.get(i));
            System.out.print(" ");
        }
        System.out.println("");
        System.out.println("");
    }
    
    public static void chooseWordFromFile() throws FileNotFoundException, IOException{
        FileInputStream fstream = new FileInputStream("wordsForHangman.txt"); 
        DataInputStream data_input = new DataInputStream(fstream); 
        BufferedReader buffer = new BufferedReader(new InputStreamReader(data_input)); 
        String str_line; 

        while ((str_line = buffer.readLine()) != null) 
        { 
            str_line = str_line.trim(); 
            if ((str_line.length()!=0))  
            { 
                words.add(str_line);
            } 
        }
        wordChosen = words.get(rand.nextInt(words.size()));
    }
    
    public static void endMessage(boolean won){
        if(lettersWrong == 7){
            System.out.println("");
            System.out.println("YOU LOSE");
            System.out.println("The word was "+ wordChosen);
        }else if(won == true){
            System.out.println("");
            System.out.println("YOU WIN");
        }
    }
    
    public static void welcome() throws InterruptedException{
        System.out.println("Welcome to this hangman game");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("You must guess words that relate to programming");
        TimeUnit.SECONDS.sleep(2);
        System.out.print("Press ENTER to continue...");
        
    }
}
