import java.util.Scanner;

public class CoffeeBot{
    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);

        // check the number of arguments are excatly 2.
        if (args.length == 0){
            System.out.println("No arguments. System terminating.");
            System.exit(1);
        }
        else if (args.length == 1){
            System.out.println("Not enought arguments. System terminating.");
            System.exit(1);
        }
        else if (args.length > 2){
            System.out.println("Too many arguments. System terminating.");
            System.exit(1);
        }

        // set amount of total cups and shots
        int cups = Integer.parseInt(args[0]);
        int shots = Integer.parseInt(args[1]);

        //check the two arguments are both positive integers.
        if (cups < 0 && shots > 0){
            System.out.println("Negative supply of coffee cups. System terminating.");
            System.exit(1);
        }
        else if (cups > 0 && shots < 0){
            System.out.println("Negative supply of coffee shots. System terminating.");
            System.exit(1);
        }
        else if (cups < 0 && shots < 0){
            System.out.println("Negative chain. System terminating.");
            System.exit(1);
        }

        // if two arugments are both non-negative, then greet the user.
        System.out.print("Hello, what's your name? ");
        String name = scan.nextLine();

        //keep asking until user enter vaild input(y/n)
        String choice;
        while(true){
            System.out.printf("Would you like to order some coffee, %s? (y/n) ", name);
            choice = scan.nextLine();
            if (choice.equals("n")){
                System.out.printf("Come back next time, %s\n", name);
                System.exit(1);
            }
            else if (choice.equals("y")){
                System.out.println("Greet! Let's get started.");
                break;
            }
            else{
                System.out.println("Invalid response. Try again.");
            }
        }

        // Once the user decides to order then display the banner and avilable supply
        System.out.print("\nOrder selection\n---------------\n\n");
        // check if cups or shots great than 1, use correct grammer
        if (cups > 1){
            System.out.printf("There are %d coffee cups in stock and each costs $2.00.\n", cups);
        }
        else {
            System.out.printf("There is %d coffee cup in stock and each costs $2.00.\n", cups);
        }
        if (shots > 1){
            System.out.printf("There are %d coffee shots in stock and each costs $1.00.\n\n", shots);
        }
        else {
            System.out.printf("There is %d coffee shot in stock and each costs $1.00.\n\n", shots);
        }

        // ask how many cups the user would like
        System.out.print("How many cups of coffee wuold you like? ");
        int cups_demand = scan.nextInt();
        if (cups_demand == 0){
            System.out.println("No cups, no coffee. Goodbey.");
            System.exit(1);
        }
        else if (cups_demand < 0){
            System.out.println("Does not compute. System terminating.");
            System.exit(1);
        }
        else if (cups_demand > cups){
            System.out.println("Not enought stock. Come back later.");
            System.exit(1);
        }

        // choosing the number of coffee shots in each cup
        System.out.println();
        int[] shots_in_cups = new int[cups_demand];
        for (int i = 0; i < cups_demand; i++){
            System.out.printf("How many coffee shots in cup %d? ", i+1);
            shots_in_cups[i] = scan.nextInt();
            if(shots_in_cups[i] < 0){
                System.out.println("Does not compute. Try again.");
                i--;
            }
            else if (shots_in_cups[i] > shots){
                if (shots == 1){
                    System.out.println("There is only 1 coffee shots left. Try again.");
                }
                else {
                    System.out.printf("There are only %d coffee shots left. Try again.\n", shots);
                }
                i--;
            }
            else {
                shots = shots - shots_in_cups[i];
            }
        }

        // Once teh order is selected, display an order summary
        System.out.print("\nOrder summary\n-------------\n\n");
        String grammer;
        double cost = 0.00;
        for (int j = 0; j < cups_demand; j++){
            grammer = shots_in_cups[j] > 1? "shots":"shot";
            System.out.printf("Cup %d has %d %s and will cost $%.2f\n", j+1, shots_in_cups[j], grammer, (double)(2+shots_in_cups[j]));
            cost += 2 + shots_in_cups[j];
        }

        System.out.println();
        if (cups_demand > 1){
            System.out.printf("%d coffees to purchase.\n", cups_demand);
        }
        else {
            System.out.println("1 coffee to purchase.");
        }
        System.out.printf("Purchase price is $%.2f\n", cost);

        // Check whether to confirm the order
        while (true){
            System.out.print("Proceed to payment? (y/n) ");
            choice = scan.nextLine();
            if (choice.equals("y")){
                break;
            }
            else if (choice.equals("n")){
                System.out.printf("Come back next time, %s\n", name);
                return;
            }
            else{
                System.out.println("Invalid response. Try again.");
            }
        }

        //Payment process
        System.out.print("\nOrder payment\n-------------\n\n");
        double[] money = {100.00, 50.00, 20.00, 10.00, 5.00, 2.00, 1.00, 0.50, 0.20, 0.10, 0.05};
        String[] money_string = {"$100.00", "$50.00", "$20.00", "$10.00", "$5.00", "$2.00", "$1.00", "$0.50", "$0.20", "$0.10", "$0.05"};

        double paid = 0;
        double remaining;
        while (true){
            remaining = cost - paid;
            System.out.printf("%.2f remains to be paid. Enter coin or note: ", remaining);
            choice = scan.nextLine();
            int index = 0;
            while (index < money.length){
                if (money_string[index].equals(choice)) break;
                index++;
            }
            //not found
            if (index == money.length){
                System.out.println("Invalid coin or note. Try again.");
                continue;
            }
            paid += money[index];
            if (paid >= cost) break;
        }

        // Refunding change
        System.out.printf("\nYou gave %.2f\n", paid);

        if (paid - cost < 0.01) System.out.println("Perfect! No change given.");
        else{
            System.out.println("Your change:");
            double change = paid - cost;
            for (double i : money){
                int amount = (int)Math.floor((change + 0.01) / i);
                if (amount >= 1){
                    System.out.printf("%d x $%.2f\n", amount, i);
                }
                change -= amount * i;
            }
        }
        System.out.printf("\nThank you, %s.\nSee you next time.\n", name);
    }
}
