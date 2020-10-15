package budget;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
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
            case 5:
                System.out.println(control.saveToFile());
                System.out.println();
                chooseAction();
                break;
            case 6:
                System.out.println(control.loadFromFile());
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
        System.out.println("Choose your action:\n" +
                "1) Add income\n" +
                "2) Add purchase\n" +
                "3) Show list of purchases\n" +
                "4) Balance\n" +
                "5) Save\n" +
                "6) Load\n" +
                "0) Exit");

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
    static Scanner scanner = new Scanner(System.in);
    static HashMap<Category, HashSet<Purchase>> expenses = new HashMap<>();
    static double income = 0.0;

    Purchase nextPurchase () {
        Purchase purchase = new Purchase();
        this.updateIncome(purchase.getValue());
        return purchase;
    }

    void addValues(Category category) {
        try {
            expenses.computeIfAbsent(category, k -> new HashSet<>()).add(this.nextPurchase());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    void addPurchase() { //get list input from console
        System.out.println(Purchase.purchasesToAdd());
        Category cat;
        int type = scanner.nextInt();
        switch (type) {
            case 1:
                cat = Category.FOOD;
                this.addValues(cat);
                break;
            case 2:
                cat = Category.CLOTHES;
                this.addValues(cat);
                break;
            case 3:
                cat = Category.ENTERTAINMENT;
                this.addValues(cat);
                break;
            case 4:
                cat = Category.OTHER;
                this.addValues(cat);
                break;
            case 5:
                return;
            default:
                this.addPurchase();
                break;
        }
        System.out.println("Purchase was added\n");
        this.addPurchase();
    }

    void showValues(Category category) {
        double sum = 0.0;
        if (expenses.isEmpty()) {
            System.out.println("Purchase List is Empty");
            return;
        }

        System.out.println(category);
        for (var item : expenses.get(category)) {
            sum += item.getValue();
            System.out.println(item.getTitle() + ": $" + item.getValue());
        }
        System.out.println("Total Sum : $" + sum );
    }


    void showAllValues() {
        double sum = 0.0;
        try {
            for (HashMap.Entry<Category, HashSet<Purchase>> entry : expenses.entrySet()) {
                System.out.println(entry.getKey().name());

                for (Purchase purc : entry.getValue() ) {

                    System.out.println(purc.getTitle() + ": $" + purc.getValue());
                    sum += purc.getValue();
                }
            }
            System.out.println("Total Sum : $" + sum );

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    static String purchasesToShow () {
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
        Category cat;
        int type = scanner.nextInt();
        switch (type) {
            case 1 :
                cat = Category.FOOD;
                this.showValues(cat);
                System.out.println();
                break;
            case 2:
                cat = Category.CLOTHES;
                this.showValues(cat);
                System.out.println();
                break;
            case 3:
                cat = Category.ENTERTAINMENT;
                this.showValues(cat);
                System.out.println();
                break;
            case 4:
                cat = Category.OTHER;
                this.showValues(cat);
                System.out.println();
                break;
            case 5:
                System.out.println("All");
                this.showAllValues();
                System.out.println();
                break;
            case 6:
                return;
            default:
                this.showPurchases();
                break;

        }
        this.showPurchases();
    }


    String saveToFile() {
        File file = new File(".\\purchases.txt");
        double sum = 0.0;

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }

        if (expenses.isEmpty()) {
            return "Expenses is empty";
        }

        try (FileWriter writer = new FileWriter(file, false)) {

            // HashMap<Integer, HashSet<Purchase>> expenses2 = new HashMap<>(expenses);
            writer.write(this.getIncome()+ "\n");
            for (HashMap.Entry<Category, HashSet<Purchase>> entry : expenses.entrySet()) {
                writer.write(entry.getKey().name() + "\n");

                for (Purchase purc : entry.getValue() ) {

                    writer.write(purc.getTitle() + ": $" + purc.getValue() + "\n");
                    sum += purc.getValue();
                }
            }

            writer.write("Total Sum : $" + sum + "\n");

        } catch (IOException ex) {
            return ex.getMessage();
        }
        return "Purchases were saved";
    }


    String loadFromFile() {
        File file = new File(".\\purchases.txt");

        try (Scanner scan = new Scanner(file)) {
            String s = scan.nextLine();
            income = Double.parseDouble(s.substring(s.lastIndexOf("$" ) + 1));
            System.out.println(s);
            while(scan.hasNext()) {
                System.out.println(scan.nextLine());
            }

        } catch (FileNotFoundException ex) {
            return ex.getMessage();
        }

        return "Purchases were loaded";
    }

    private void updateIncome(double value) { //calculate total dollars on list
        income -= value;
    }

    void setIncome() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter Income");
        income += Double.parseDouble(scanner.nextLine());
        System.out.println("Income was added\n");
    }

    String getIncome()  {
        return "Balance: $" + income;
    }

    void exit() {
        System.out.println("Bye!");
    }
}

enum Category {
    FOOD,
    CLOTHES,
    ENTERTAINMENT,
    OTHER;

}
