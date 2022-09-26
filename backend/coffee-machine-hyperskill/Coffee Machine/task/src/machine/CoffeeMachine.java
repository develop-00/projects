package machine;

public class CoffeeMachine {

    private int availableWater;
    private int availableMilk;
    private int availableBeans;
    private int availableDisposableCups;
    private int cash;

    public CoffeeMachine() {
        // Initialize coffee machine with pre-defined quantity.
        this.availableWater = 400;
        this.availableMilk = 540;
        this.availableBeans = 120;
        this.availableDisposableCups = 9;
        this.cash = 550;
    }

    protected String buyCappuccino() {
        // Needed resource for making cappuccino.
        final int neededWater = 200;
        final int neededMilk = 100;
        final int neededBeans = 12;
        final int cost = 6;

        return buyCoffee(neededWater, neededMilk, neededBeans, cost);
    }

   protected String buyLatte() {
        // Needed resource for making latte.
       final int neededWater = 350;
       final int neededMilk = 75;
       final int neededBeans = 20;
       final int cost = 7;

       return buyCoffee(neededWater, neededMilk, neededBeans, cost);
   }
   protected String buyEspresso() {
        // Needed resource for making espresso.
       final int neededWater = 250;
       final int neededMilk = 0;
       final int neededBeans = 16;
       final int cost = 4;

       return buyCoffee(neededWater, neededMilk, neededBeans, cost);
   }

    private String buyCoffee(int neededWater, int neededMilk, int neededBeans, int cost) {
        // Check if required resources available and make specified coffee.
        if (this.availableWater < neededWater) {
            return "Sorry, not enough water!";
        }

        if (this.availableMilk < neededMilk) {
            return "Sorry, not enough milk!";
        }

        if (this.availableBeans < neededBeans) {
            return "Sorry, not enough coffee beans!";
        }

        if (this.availableDisposableCups < 1) {
            return "Sorry, not enough disposable cups!";
        }

        this.cash += cost;
        this.availableWater -= neededWater;
        this.availableMilk -= neededMilk;
        this.availableBeans -= neededBeans;
        this.availableDisposableCups -= 1;
        return "I have enough resources, making you a coffee!";

    }
    protected void withdrawCash() {
        // Give all available cash.
        System.out.println("I gave you $" + cash);
        this.cash = 0;
    }

    protected void refillCoffeeMachine(int filledWater, int filledMilk, int filledBeans, int filledCups) {
        // Refill coffee machine with specified resources.
        this.availableWater += filledWater;
        this.availableMilk += filledMilk;
        this.availableBeans += filledBeans;
        this.availableDisposableCups += filledCups;
    }

    protected void printStatus() {
        // Print status of coffee machine.
        System.out.println();
        System.out.println("The coffee machine has:");
        System.out.println(this.availableWater + " ml of water");
        System.out.println(this.availableMilk + " ml of milk");
        System.out.println(this.availableBeans + " g of coffee beans");
        System.out.println(this.availableDisposableCups + " disposable cups");
        System.out.println("$" + this.cash + " of money");
        System.out.println();
    }
}
