package hotelbooking;

import java.util.Scanner;

public class HotelBooking {

    public static Scanner input = new Scanner(System.in);

    public static enum roomType {
        Family,
        Single,
        Double,
    }

    public static enum boardType {
        selfCatering,
        halfBoard,
        fullBoard,
    }

    public static int singlePrice = 50;
    public static int doublePrice = 75;
    public static int familyPrice = 105;
    public static int selfCateringPrice = 0;
    public static int halfBoardPrice = 10;
    public static int fullBoardPrice = 20;

    public static void main(String[] args) {
        roomType room;
        boardType board;
        int daysStaying;
        while (true) {
            room = chooseRoomType();
            //input.next();
            board = chooseBoardType();
            daysStaying = getDaysStaying();
            System.out.println("You are staying in a " + room + " room, with " + board + ", for " + daysStaying + " days");
            System.out.println("CONFIRM Y/N");
            if (input.next().equalsIgnoreCase("y")) {
                break;
            } else {
                spaces();
            }
        }
        System.out.println("");
        System.out.println("CONFIRMED");
        System.out.println("");

        int totBeforeDiscount = 0;
        int totAfterDiscount = 0;
        switch (room) {
            case Single:
                totBeforeDiscount = singlePrice * daysStaying;
                break;
            case Double:
                totBeforeDiscount = doublePrice * daysStaying;
                break;
            case Family:
                totBeforeDiscount = familyPrice * daysStaying;
                break;
        }
        switch (board) {
            case selfCatering:
                totBeforeDiscount += selfCateringPrice * daysStaying;
                break;
            case halfBoard:
                totBeforeDiscount += halfBoardPrice * daysStaying;
                break;
            case fullBoard:
                totBeforeDiscount += fullBoardPrice * daysStaying;
                break;
        }
        totAfterDiscount = totBeforeDiscount;
        for (int i = 0; i < daysStaying - 7; i++) {
            totAfterDiscount = totAfterDiscount - ((totBeforeDiscount/daysStaying)*2/10);
        }
        
        System.out.println("Price before discount: £" + totBeforeDiscount);
        System.out.println("Price after discount: £" + totAfterDiscount);
    }

    public static boardType chooseBoardType() {
        spaces();
        String selection = "";
        boardType boardChosen = boardType.selfCatering;
        while (selection.equals("")) {
            System.out.println("Choose a board type: ");
            System.out.println("      type:           price: (per day)");
            System.out.println("(A)    self-catering    £" + selfCateringPrice);
            System.out.println("(B)    half Board       £" + halfBoardPrice);
            System.out.println("(C)    full Board       £" + fullBoardPrice);
            input.nextLine();
            selection = input.next().toLowerCase();
            switch (selection) {
                case "self-catering":
                case "self":
                case "1":
                case "a":
                    boardChosen = boardType.selfCatering;
                    break;
                case "half":
                case "2":
                case "b":
                    boardChosen = boardType.halfBoard;
                    break;
                case "full":
                case "3":
                case "c":
                    boardChosen = boardType.fullBoard;
                    break;
                default:
                    selection = "";
                    spaces();
                    System.out.println("INVALID SELECTION!");
                    System.out.println("");
                    break;
            }

            if (!selection.equals("")) {
                System.out.println(boardChosen + " selected");
                System.out.println("CONFIRM BOARD TYPE Y/N");
                if (input.next().equalsIgnoreCase("y")) {
                    System.out.println("CONFIRMED");
                } else {
                    spaces();
                    selection = "";
                }
            }
        }
        return boardChosen;
    }

    public static roomType chooseRoomType() {

        String selection = "";
        roomType roomChosen = roomType.Single;
        while (selection.equals("")) {
            System.out.println("Choose a room type: ");
            System.out.println("     type:     guests:    price: (per day)");
            System.out.println("(A)    Single    1          £" + singlePrice);
            System.out.println("(B)    Double    2          £" + doublePrice);
            System.out.println("(C)    Family    3-4        £" + familyPrice);
            selection = input.next().toLowerCase();
            switch (selection) {
                case "single":
                case "1":
                case "a":
                    roomChosen = roomType.Single;
                    break;
                case "double":
                case "2":
                case "b":
                    roomChosen = roomType.Double;
                    break;
                case "family":
                case "3":
                case "4":
                case "c":
                    roomChosen = roomType.Family;
                    break;
                default:
                    selection = "";
                    spaces();
                    System.out.println("INVALID SELECTION!");
                    System.out.println("");
                    break;
            }

            if (!selection.equals("")) {
                System.out.println(roomChosen + " selected");
                System.out.println("CONFIRM ROOM Y/N");
                String yORn = input.next();
                if (yORn.equalsIgnoreCase("y")) {
                    System.out.println("CONFIRMED");
                } else {
                    spaces();
                    selection = "";
                }
            }
        }
        return roomChosen;
    }

    private static int getDaysStaying() {
        spaces();
        int days = 0;
        while (days < 1) {
            System.out.println("How many days would you like to stay?");
            try {
                days = input.nextInt();
            } catch (Exception e) {
                spaces();
                System.out.println("INPUT INVALID");
                input.next();
                days = 0;
            }
            
            if (days != 0) {
                System.out.println(days + " days selected");
                System.out.println("CONFIRM STAY TIME Y/N");
                if (input.next().equalsIgnoreCase("y")) {
                    System.out.println("CONFIRMED");
                } else {
                    spaces();
                    days = 0;
                }
            }else{
                spaces();
            }
        }
        spaces();
        return days;
    }

    public static void spaces() {
        for (int i = 0; i < 20; i++) {
            System.out.println("");
        }
    }

}
