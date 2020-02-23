package rockpaperscissors2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors2 {

    public static Scanner input = new Scanner(System.in);
    public static Random rand = new Random();
    public static int playerScore = 0;
    public static int computerScore = 0;
    public static String[][] vsTable = {
        {"", "rock", "paper", "scissors"},
        {"rock", "draw", "paper", "rock"},
        {"paper", "paper", "draw", "scissors"},
        {"scissors", "rock", "scissors", "draw"},};
    public static ArrayList<String> action = new ArrayList<>();
    public static ArrayList<String> description = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException, IOException {
        readFileAction();
        readFileDescriptions();
        System.out.println("Welcome to rock paper scissors, first to three");
        game();
    }

    public static void game() throws InterruptedException {
        while (computerScore + playerScore < 3) {
            round();
        }
        if (computerScore > playerScore) {
            System.out.println("-- COMPUTER WINS --");
        } else {
            System.out.println("-- YOU WIN --");
        }
        System.out.println("");
        System.out.println("Would you like to play again?");
        if (input.next().equalsIgnoreCase("yes") || input.next().equalsIgnoreCase("y")) {
            System.out.println("");
            computerScore = 0;
            playerScore = 0;
            game();
        }
    }

    public static void round() throws InterruptedException {
        String winner = "";
        String playerMove = "";
        while (winner.equals("")) {
            System.out.println("Enter your move:");
            playerMove = input.next();
            System.out.println("Are you sure?");
            if (input.next().equalsIgnoreCase("yes")) {
                System.out.println("");
                winner = findWinner(playerMove);
            }
        }
        if (winner.equalsIgnoreCase("draw")) {
            System.out.println("-- " + winner + " --");
        } else {
            System.out.println("-- " + winner + " wins --");
            if (playerMove.equalsIgnoreCase(winner)) {
                playerScore++;
            } else {
                computerScore++;
            }
        }
        System.out.println("");
        System.out.println("YOU " + playerScore + " : " + computerScore + " COMPUTER");
        System.out.println("");
    }

    public static String findWinner(String move) {
        String winner = "";
        int randNum = rand.nextInt(2) + 1;
        int playerInt = 0;
        for (int i = 0; i < vsTable.length; i++) {
            if (vsTable[0][i].equalsIgnoreCase(move)) {
                playerInt = i;
                i = vsTable.length;
            }
        }
        if (playerInt == 0) { // if they enter
            int randAction = 0;
            int randDescription = 0;

            // following finds a random action
            char testchar = '-';
            while (testchar == '-') {
                randAction = rand.nextInt(action.size());
                testchar = action.get(randAction).charAt(0);
            }
            // gets the action type
            String actionType = "";
            for (int i = 0; i < action.size(); i++) {
                if (action.get(i).charAt(0) == '-' && i < randAction) {
                    actionType = action.get(i);
                }
            }

            // following finds a random but valid description for the action picked
            testchar = '-';
            while (testchar == '-') {
                randDescription = rand.nextInt(description.size());
                testchar = description.get(randDescription).charAt(0);

                String desciptionType = "";
                for (int i = 0; i < description.size(); i++) {
                    if (description.get(i).charAt(0) == '-' && i < randDescription) {
                        desciptionType = description.get(i);
                    }
                }
                if (!desciptionType.equals(actionType)) {  // checks if the description chosen has the same type as the action
                    testchar = '-';
                }
            }
            System.out.println("you: " + move);
            System.out.println("computer: has attacked you with " + action.get(randAction) + ", " + description.get(randDescription));
            return "computer";
        } else {
            System.out.println("you: " + vsTable[0][playerInt]);
            System.out.println("computer: " + vsTable[randNum][0]);
            winner = vsTable[randNum][playerInt];
            return winner;
        }
    }

    public static void readFileAction() throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream("actions.txt");
        DataInputStream data_input = new DataInputStream(fstream);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(data_input));
        String str_line;

        while ((str_line = buffer.readLine()) != null) {
            str_line = str_line.trim();
            if ((str_line.length() != 0)) {
                action.add(str_line);
            }
        }
    }

    public static void readFileDescriptions() throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream("descriptions.txt");
        DataInputStream data_input = new DataInputStream(fstream);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(data_input));
        String str_line;

        while ((str_line = buffer.readLine()) != null) {
            str_line = str_line.trim();
            if ((str_line.length() != 0)) {
                description.add(str_line);
            }
        }
    }
}
