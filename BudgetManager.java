package budget;
import  java.util.Scanner;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {

        chooseAction();
    }


    static void chooseAction() {
        Control control = new Control();
        Scanner scanner = new Scanner(System.in);

        showMenu();
        int x = scanner.nextInt();
        System.out.println();
        switch (x) {
            case 1:
                control.setIncome();

                chooseAction();
                break;
            case 2:
                Purchase purchase = new Purchase();
                control.addPurchase(purchase);
                System.out.println("Purchase was added\n");
                chooseAction();
                break;
            case 3:
                System.out.println(control.showPurchases());
                System.out.println();
                chooseAction();
                break;
            case 4:
                System.out.println(control.getIncome());
                System.out.println();
                chooseAction();
                break;
            case 0:
                control.exit();
                break;
            default:
                chooseAction();
        }
    }

    static void showMenu() {
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("0) Exit");
    }

}


class Purchase {

    Double value;
    String title;

    public Purchase() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter purchase name:");
        setTitle(scanner.nextLine());
        System.out.println("Enter its price:");
        setValue(Double.parseDouble(scanner.nextLine()));
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

class Control {

    static ArrayList<Purchase> purchases = new ArrayList<>();
    static double sumOfPurchases = 0.0;
    static double income = 0.0;

    void addPurchase(Purchase p) { //get list input from console
        purchases.add(p);
        sumOfPurchases += p.getValue();
        updateIncome(p.getValue());
    }


    String showPurchases() {
        if(!purchases.isEmpty()) {
            for (Purchase p : purchases) {
                System.out.println(p.getTitle() + " $" + p.getValue());
            }
            return ("Total sum: $" + sumOfPurchases);
        }
        return "Purchase list is empty";
    }

    private void updateIncome(double value) { //calculate total dollars on list
        this.income -= value;
    }

    void setIncome() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter Income");
        this.income += Double.parseDouble(scanner.nextLine());
        System.out.println("Income was added\n");
    }

    String getIncome() {
        return "Balance: $" + income;
    }

    void exit() {
        System.out.println("Bye!");
    }
}

