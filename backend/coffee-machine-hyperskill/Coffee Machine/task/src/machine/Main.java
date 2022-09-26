package machine;

import java.util.Scanner;

public class Main {

    final private static Scanner readIp = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize an instance of coffee machine.
        CoffeeMachine coffeeMachine = new CoffeeMachine();

        // Check for coffee machine running or close.
        boolean isMachineClosed = false;
        while (!isMachineClosed) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String action = readIp.nextLine();

            switch (action) {
                case "buy" -> {
                    System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                    String choice = readIp.nextLine();
                    if (choice.equals("back")) {
                        break;
                    }
                    int coffeeChoice = Integer.parseInt(choice);
                    switch (coffeeChoice) {
                        case 1 -> System.out.println(coffeeMachine.buyEspresso());
                        case 2 -> System.out.println(coffeeMachine.buyLatte());
                        case 3 -> System.out.println(coffeeMachine.buyCappuccino());
                    }
                }
                case "fill" -> {
                    System.out.println("Write how many ml of water you want to add:");
                    int filledWater = readIp.nextInt();

                    System.out.println("Write how many ml of milk you want to add:");
                    int filledMilk = readIp.nextInt();

                    System.out.println("Write how many grams of coffee beans you want to add:");
                    int filledBeans = readIp.nextInt();

                    System.out.println("Write how many disposable cups of coffee you want to add:");
                    int filledCups = readIp.nextInt();
                    coffeeMachine.refillCoffeeMachine(filledWater, filledMilk, filledBeans, filledCups);
                }
                case "take" -> coffeeMachine.withdrawCash();
                case "remaining" -> coffeeMachine.printStatus();
                case "exit" -> isMachineClosed = true;
            }
        }
    }
}
