package budget;
import  java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        chooseAction();
    }

    static void showMenu() {
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("0) Exit");
    }

    static void chooseAction() {
        Scanner scanner = new Scanner(System.in);
        showMenu();
        int x = scanner.nextInt();
        System.out.println();
        switch (x) {
            case 1:
                Income.addIncome();
                System.out.println();
                chooseAction();
                break;
            case 2:
                System.out.println(Purchase.addPurchase());
                System.out.println();
                chooseAction();
                break;
            case 3:
                System.out.println(Purchase.getList());
                System.out.println();
                chooseAction();
                break;
            case 4:
                System.out.println(Income.getBalance());
                System.out.println();
                chooseAction();
                break;
            case 0:
                exit();
                break;
            default:
                chooseAction();
        }
    }

    static void exit() {
        System.out.println("Bye!");
    }
}

class Income {

    static double balance;

    static void addIncome() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Income");
        balance += scanner.nextDouble();
        System.out.println("Income was added");
    }

    static String getBalance() {
       return "Balance: $" + balance;
    }

    static void updateBalance(Double price) {
        balance -= price;
    }
}

class Purchase {

    private static double sum;
    static StringBuilder list = new StringBuilder();

    static String getList() {
        if(!(list.toString()).isEmpty()) {
            return list.toString() + "Total sum: $" + sum + "\n";
        }
        return "Purchase list is empty";
    }

    private static void calculateSum(String line) { //calculate total dollars on list
        sum += Double.parseDouble(line.substring(line.indexOf("$") + 1));
    }

    public static String addPurchase() { //get list input from console
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter purchase name:");
            list.append(scanner.nextLine());
            System.out.println("Enter its price:");
            String price = scanner.nextLine();
            list.append(" $" + price + "\n");
            calculateSum(price);
            Income.updateBalance(Double.parseDouble(price));

        return "Purchase was added";
    }

}

