package budget;
import  java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;



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
                System.out.println();
                chooseAction();
                break;
            case 2:
                control.addPurchase();
                System.out.println();
                chooseAction();

                break;
            case 3:
                control.showPurchases();
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
        System.out.println("Choose your action");
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

    public static String purchasesToAdd() {
        return "Choose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n" +
                "5) Back";
    }
}

class Control {

    static double income = 0.0;

    Purchase nextPurchase () {
        Purchase purchase = new Purchase();
        return purchase;
    }
    static Scanner scanner = new Scanner(System.in);

    static HashMap<Integer, HashSet<Purchase>> expenses = new HashMap<>();

    static void populateMap() {
        expenses.put(1, new HashSet<>()); //create map set values to null
        expenses.put(2, new HashSet<>());
        expenses.put(3, new HashSet<>());
        expenses.put(4, new HashSet<>());
    }

    void addValues(int key) {
        try {
            expenses.computeIfAbsent(key, k -> new HashSet<>()).add(nextPurchase());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    void addPurchase() { //get list input from console
        System.out.println(Purchase.purchasesToAdd());
        int type = scanner.nextInt();
        switch (type) {
            case 1: case 2: case 3: case 4:
                addValues(type);
                break;
            case 5:
                return;
            default:
                addPurchase();
                break;

        }
        System.out.println("Purchase was added\n");
        addPurchase();
    }

    void showValues(int key) {
        double sum = 0.0;
        if (expenses.get(key).isEmpty()) {
            System.out.println("Purchase List is Empty");
            return;
        }
        for (var item : expenses.get(key)) {
            sum += item.getValue();
            System.out.println(item.getTitle() + ":" + item.getValue());
        }
        System.out.println("Total Sum : $" + sum );
    }

    void showAllValues() {
        double sum = 0.0;
            for (int i = 1; i < 5; i++) {
                if (!expenses.get(i).isEmpty()) {
                    for (var item : expenses.get(i)) {
                        sum += item.getValue();
                        System.out.println(item.getTitle() + ":" + item.getValue());
                    }
                }
                System.out.println("Total Sum : $" + sum );
            }
    }

    String purchasesToShow () {
        return "Choose the type of purchases\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n" +
                "5) All\n" +
                "6) Back\n";
    }

    void showPurchases() {
        System.out.println(purchasesToShow());
        int type = scanner.nextInt();
        switch (type) {
            case 1 :
                System.out.println("Food");
                showValues(type);
                System.out.println();
                break;
            case 2:
                System.out.println("Clothes");
                showValues(type);
                System.out.println();
                break;
            case 3:
                System.out.println("Entertainment");
                showValues(type);
                System.out.println();
                break;
            case 4:
                System.out.println("Other");
                showValues(type);
                System.out.println();
                break;
            case 5:
                System.out.println("All");
                showAllValues();
                System.out.println();
                break;
            case 6:
                return;
            default:
                showPurchases();
                break;

        }
        showPurchases();
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


