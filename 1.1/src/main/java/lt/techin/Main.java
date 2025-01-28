package lt.techin;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Yes or no?");
            
            if ((!scanner.nextLine().equalsIgnoreCase("Yes"))) {
                break;
            }
        }
    }
}