package storygame;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class StoryGame {

    public static Scanner input = new Scanner(System.in);

    public static int currentLocation = 1;

    public static ArrayList<String> saveData = new ArrayList<>();  // EACH VALUE MEANS (in order) : Money , lemonNum , lemonadeNum , kitchenLevel , StandLevel

    public static storyVariables keyVariables = new storyVariables(0, 10, 0);
    public static kitchen yourKicthen = new kitchen(1, 15);
    public static lemonadeStand yourStand = new lemonadeStand(1, 10);

    public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {

        String selection = "";
        while(selection.equals("")){
            System.out.println("(1) LOAD FILE");
            System.out.println("(2) NEW GAME");
            selection = input.next();
            if (selection.equals("1")) {
                readProgressFile();
                
                keyVariables = new storyVariables(Integer.valueOf(saveData.get(0)), Integer.valueOf(saveData.get(1)), Integer.valueOf(saveData.get(2)));
                yourKicthen = new kitchen(Integer.valueOf(saveData.get(3)), 15);
                yourStand = new lemonadeStand(Integer.valueOf(saveData.get(4)), 10);
            } 
            else if (selection.equals("2")) {
                
            }
            else {
                Spaces();
                System.out.println("INVALID INPUT");
                System.out.println("");
                System.out.println("");
                selection = "";
            }
        }
        Spaces();
        kitchen();

    }

    public static void kitchen() throws FileNotFoundException {
        save();
        String selection = "";
        while (selection.equals("")) {
            System.out.println("");
            System.out.println("~~~~~~~~~~~~~~~~~");
            System.out.println("|    Kitchen    |          Lemonade: " + keyVariables.lemonadeToSell + "   Lemons: " + keyVariables.lemons + "   Money: £" + keyVariables.money);
            System.out.println("~~~~~~~~~~~~~~~~~          Kitchen Level: " + yourKicthen.kitchenLevel);
            System.out.println("");
            System.out.println("(1) Go to Lemonade Stand");
            System.out.println("");
            System.out.println("(2) Make Lemonade");
            System.out.println("");
            System.out.println("(3) Upgrade Kitchen: £" + yourKicthen.costOfNextUpgrade);
            System.out.println("");
            selection = input.next();
            if (selection.equals("1")) {
                Spaces();
                market();
            } else if (selection.equals("2")) {
                int newLemonade = yourKicthen.makeLemonadeNum(keyVariables.lemons);
                keyVariables.lemonadeToSell += newLemonade;
                keyVariables.lemons -= newLemonade / 2;
            } else if (selection.equals("3")) {
                keyVariables.money = yourKicthen.upgradeKitchen_ReturnsNewMoney(keyVariables.money);
            } else {
                Spaces();
                System.out.println("INVALID INPUT");
                selection = "";
            }
        }
        kitchen();
    }

    public static void market() throws FileNotFoundException {
        save();
        String selection = "";
        while (selection.equals("")) {
            System.out.println("");
            System.out.println("~~~~~~~~~~~~~~~~~~~");
            System.out.println("| Lemonade Stand  |        Lemonade: " + keyVariables.lemonadeToSell + "   Lemons: " + keyVariables.lemons + "   Money: £" + keyVariables.money);
            System.out.println("~~~~~~~~~~~~~~~~~~~        Lemonade Stand Level: " + yourStand.lemonadStandLevel);
            System.out.println("");
            System.out.println("(1) Go to Kitchen");
            System.out.println("");
            System.out.println("(2) sell lemonade");
            System.out.println("");
            System.out.println("(3) buy lemons from store: £5");
            System.out.println("");
            System.out.println("(4) upgrade Lemonade Stand: £" + yourStand.costOfNextUpgrade);
            selection = input.next();
            if (selection.equals("1")) {
                Spaces();
                kitchen();
            } else if (selection.equals("2")) {
                int lemonadeSoldThisClick = yourStand.sellLemonadeNum(keyVariables.lemonadeToSell);
                keyVariables.lemonadeToSell -= lemonadeSoldThisClick;
                keyVariables.money += lemonadeSoldThisClick;
            } else if (selection.equals("3")) {
                int lemonsBought = yourStand.buyLemonsNum(keyVariables.money);
                keyVariables.money -= lemonsBought;
                keyVariables.lemons += lemonsBought;
            } else if (selection.equals("4")) {
                keyVariables.money = yourStand.upgradeKitchen_ReturnsNewMoney(keyVariables.money);
            } else {
                Spaces();
                System.out.println("INVALID INPUT");
                selection = "";
            }
        }
        market();
    }

    public static void Spaces() {
        for (int i = 0; i < 10; i++) {
            System.out.println("");
        }
    }

    public static void readProgressFile() throws FileNotFoundException, IOException {
        FileInputStream fstream = new FileInputStream("ProgressFile.txt");
        DataInputStream data_input = new DataInputStream(fstream);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(data_input));
        String str_line;

        while ((str_line = buffer.readLine()) != null) {
            str_line = str_line.trim();
            if ((str_line.length() != 0)) {
                saveData.add(str_line);
            }
        }
    }
    
    public static void save() throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter("ProgressFile.txt")) {
            out.println(keyVariables.money);
            out.println(keyVariables.lemons);
            out.println(keyVariables.lemonadeToSell);
            out.println(yourKicthen.kitchenLevel);
            out.println(yourStand.lemonadStandLevel);
        }
    }

}
