package numberssort;

import java.util.ArrayList;
import java.util.Scanner;

public class NumbersSort {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        ArrayList<Integer> numbers = new ArrayList<>();

        int numOfnums = 1;

        while (numOfnums < 10) {
            try {
                System.out.println("How many numbers would you like to sort? (must be >= 10) ");
                numOfnums = Integer.parseInt(input.next());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number ...");
            }
        }

        System.out.println("");
        System.out.println("Enter your numbers: ");

        for (int i = 0; i < numOfnums; i++) {
            try {
                numbers.add(input.nextInt());    // adding numbers to array
            } catch (Exception e) {
                i--;
            }
        }
        
        System.out.println("");
        
        
        
        ///// Insersion sort:

        for (int i = 1; i < numbers.size(); i++) {  // starts at one because in insertion sort the first number doesnt need to be sorted

            int j = i - 1;

            int addInt = numbers.get(i);
            int locationToAdd = i;

            while (numbers.get(i) < numbers.get(j)) {
                addInt = numbers.get(i);
                locationToAdd = j;

                j--;

                if (j == -1) {
                    break;
                }
            }

            numbers.remove(i);
            numbers.add(locationToAdd, addInt);

            for (int t = 0; t < numbers.size(); t++) {
                System.out.print(numbers.get(t) + ", ");
            }
            System.out.println("");
        }
        
        /////
        
        
        
        int min = numbers.get(0);
        int max = numbers.get(numbers.size()-1);
        float average = 0;

        System.out.println("");
        
        System.out.println("sorted:");
        for (int i = 0; i < numbers.size(); i++) {
            System.out.print(numbers.get(i) + ", ");
            average = average + numbers.get(i);
        }
        
        average = average/numbers.size();
        
        System.out.println("");
        System.out.println("");
        System.out.println("Minimum: " + min);
        System.out.println("Maximum: " + max);
        System.out.println("Mean: " + average);
    }

}
