package rockpaperscissors;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RockPaperScissors {

    public static ArrayList<String> action = new ArrayList<>();
    public static ArrayList<String> description = new ArrayList<>();
    public static ArrayList<String> descriptionsPlayer = new ArrayList<>();
    public static ArrayList<String> actionsPlayer = new ArrayList<>();

    public static Scanner input = new Scanner(System.in);
    public static Random rand = new Random();

    public static int playerScore = 0;
    public static int computerScore = 0;

    public static void main(String[] args) throws IOException, InterruptedException {

        
        
        
        
        
        readFileAction();  // imports the files into array lists
        readFileDescriptions();  //
        readFileDescriptionsPlayer();
        readFileActionsPlayer();

        System.out.println("Welcome to rock, paper scissors against the computer. Best of three.");
        TimeUnit.SECONDS.sleep(2);
        System.out.print("Lets begin...");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("");

        playGame();

    }

    public static void playGame() throws InterruptedException {

        printScore(computerTurn(), playerTurn());
        printScore(computerTurn(), playerTurn());
        printScore(computerTurn(), playerTurn());

        System.out.println("");
        System.out.println("Would you like to play again? ");
        String yORn = input.next();
        if (yORn.equalsIgnoreCase("yes") || yORn.equalsIgnoreCase("y")) {
            
            playGame();
        }
    }

    public static int playerTurn() {
        String moveChosen = "";
        int actionNum = 0;
        int randDescription = 0;

        while (moveChosen.equals("")) {
            System.out.print("Enter your move: ");
            moveChosen = input.nextLine();
        }

        int lastLength = 100;
        for (int i = 0; i < actionsPlayer.size(); i++) {

            String currentItemBeingChecked = actionsPlayer.get(i);
            if (actionsPlayer.get(i).length() > 6) {
                if (actionsPlayer.get(i).substring(0, 6).equals("/your ")) {
                    currentItemBeingChecked = currentItemBeingChecked.replace("/your ", "");
                }
            }

            String lowerCaseAction = currentItemBeingChecked.toLowerCase();

            if (lowerCaseAction.contains(moveChosen.toLowerCase()) && currentItemBeingChecked.length() < lastLength && !currentItemBeingChecked.contains("--")) {
                actionNum = i;
                lastLength = currentItemBeingChecked.length();
            }

        }

        String actionType = "";
        if (actionNum != 0) {
            moveChosen = actionsPlayer.get(actionNum);

            for (int i = 0; i < actionsPlayer.size(); i++) {
                if (actionsPlayer.get(i).charAt(0) == '-' && i < actionNum) {
                    actionType = actionsPlayer.get(i);
                }
            }
        } else {
            actionType = "--defaultDescriptions-------------------";
        }

        // following finds a random but valid description for the action picked
        char testchar = '-';
        while (testchar == '-') {
            randDescription = rand.nextInt(descriptionsPlayer.size());
            testchar = descriptionsPlayer.get(randDescription).charAt(0);

            String desciptionType = "";
            for (int i = 0; i < descriptionsPlayer.size(); i++) {
                if (descriptionsPlayer.get(i).charAt(0) == '-' && i < randDescription) {
                    desciptionType = descriptionsPlayer.get(i);
                }
            }
            if (!desciptionType.equals(actionType)) {  // checks if the description chosen has the same type as the action
                testchar = '-';
            }
        }

        System.out.println("");
        String descriptionString = descriptionsPlayer.get(randDescription);
        String vowel = "aeiou";
        String aORan = "a ";
        if (vowel.contains(moveChosen.substring(0, 1))) {
            aORan = "an ";
        } else if (moveChosen.substring(0, 1).equals("/")) {
            aORan = "";
            moveChosen = moveChosen.replaceFirst("/", "");
        }else if(actionType.equals("--countries-------------------")){
            aORan = "";
        }

        if (actionNum > 0) {
            if (descriptionString.contains("###")) {
                descriptionString = descriptionString.replace("###", moveChosen);
            }

            System.out.println("You have chosen to attack the computer with " + aORan + moveChosen + ", " + descriptionString);
        } else {
            if (descriptionString.contains("###")) {
                descriptionString = descriptionString.replace("###", moveChosen);
            }

            System.out.println("You have chosen to attack the computer with " + aORan + moveChosen + ", " + descriptionString);
        }

        return descriptionsPlayer.get(randDescription).length();
    }

    public static void printScore(int computerLenght, int playerLenght) {
        if (computerLenght > playerLenght) {
            computerScore++;
        } else {
            playerScore++;
        }

        System.out.println("");
        System.out.println("score:");
        System.out.println("COMPUTER " + computerScore + " : " + playerScore + " YOU");
        System.out.println("");
        if (computerScore + playerScore == 3 && computerScore > playerScore) {
            System.out.println("YOU LOSE");
            playerScore = 0;
            computerScore = 0;
        } else if (computerScore + playerScore == 3) {
            System.out.println("YOU WIN");
            playerScore = 0;
            computerScore = 0;
        }
    }

    public static int computerTurn() throws InterruptedException {

        TimeUnit.SECONDS.sleep(2);

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

        System.out.println("");
        System.out.println("The computer has chosen to attack you with " + action.get(randAction) + ", " + description.get(randDescription));
        System.out.println("");
        TimeUnit.SECONDS.sleep(2);
        return description.get(randDescription).length();
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

    public static void readFileActionsPlayer() throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream("actionsPlayer.txt");
        DataInputStream data_input = new DataInputStream(fstream);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(data_input));
        String str_line;

        while ((str_line = buffer.readLine()) != null) {
            str_line = str_line.trim();
            if ((str_line.length() != 0)) {
                actionsPlayer.add(str_line);
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

    public static void readFileDescriptionsPlayer() throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream("descriptionsPlayer.txt");
        DataInputStream data_input = new DataInputStream(fstream);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(data_input));
        String str_line;

        while ((str_line = buffer.readLine()) != null) {
            str_line = str_line.trim();
            if ((str_line.length() != 0)) {
                descriptionsPlayer.add(str_line);
            }
        }
    }

}
