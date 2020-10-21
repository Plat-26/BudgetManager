package budget;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        new Controls().chooseAction();
    }


}

class Purchase {

    double value;
    String title;

    public Purchase() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter purchase name:");
        setTitle(scanner.nextLine());
        System.out.println("Enter its price:");
        setValue(Double.parseDouble(scanner.nextLine()));
    }

    public Purchase(String title, double value) {
        this.title = title;
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
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

class AnalyzePurchase {

    BudgetManager control;

    AnalyzePurchase(BudgetManager control) {
        this.control = control;
    }

    static String chooseSort() {
        return "How do you want to sort?\n" +
                "1) Sort all purchases\n" +
                "2) Sort by type\n" +
                "3) Sort certain type\n" +
                "4) Back\n";
    }

    void analyze(){
        this.sortingMethods();
    }

    private void sortingMethods() {
        System.out.println(chooseSort());
        int method = control.validateInput("[1-4]");
        switch (method) {
            case 1:
                this.sortAllPurchases();
                System.out.println();
                break;
            case 2:
                this.sortByType();
                System.out.println();
                break;
            case 3:
                this.sortCertainType();
                System.out.println();
                break;
            case 4:
                return;
            default:
                this.sortingMethods();
                break;
        }
        sortingMethods();
    }

    void sortAllPurchases() {
        ArrayList<Purchase> allPurchase = new ArrayList<>();

        for (HashMap.Entry<Category, HashSet<Purchase>> entry : control.expenses.entrySet()) {
            allPurchase.addAll(entry.getValue());
        }

        sortList(allPurchase);
    }

    void sortList(ArrayList<Purchase> list) {
        //convert arrayList to array

        Purchase[] arry = list.toArray(new Purchase[0]);
        double sum = 0.0;
        for (int i = 0; i < arry.length - 1; i++) {
            for (int j = 0; j < arry.length - i - 1; j++) {
                if (arry[j].getValue() < arry[j + 1].getValue()) {
                    Purchase temp = arry[j];
                    arry[j] = arry[j + 1];
                    arry[j + 1] = temp;
                }
            }
        }

        for (Purchase p : arry) {
            sum += p.getValue();
            System.out.println(p.getTitle() + " $" + p.getValue());
        }
        System.out.println("Total: $" + sum);
    }

    void sortByType() {
        ArrayList<Purchase> purchaseTypes = new ArrayList<>();

        for (Category type : Category.values()) { //loop through the enum
            double typeSum = 0;
            if (control.expenses.containsKey(type)) {
                for (Purchase p : control.expenses.get(type)) {
                    typeSum += p.getValue();
                }
            }
            purchaseTypes.add(new Purchase(type.name(), typeSum));
        }

        this.sortList(purchaseTypes);
    }

    void sortCertainType() {
        System.out.println("Choose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other"
        );

        int type = control.validateInput("[1-4]");

        switch(type) {
            case 1: sortType(Category.FOOD); break;
            case 2: sortType(Category.CLOTHES); break;
            case 3: sortType(Category.ENTERTAINMENT); break;
            case 4: sortType(Category.OTHER); break;
        }
    }

    void sortType(Category type) {
        if(control.expenses.containsKey(type)) {
            ArrayList<Purchase> purchaseType = new ArrayList<>();

            purchaseType.addAll(control.expenses.get(type));
            sortList(purchaseType);

            return;
        }
        System.out.println("Purchase list is empty");
    }

}

class Expenses {

    BudgetManager manager;

    public Expenses(BudgetManager manager) {
        this.manager = manager;
    }

    Purchase nextPurchase () {
        Purchase purchase = new Purchase();
        manager.inc.updateIncome(purchase.getValue());
        return purchase;
    }

    void addValues(Category category) {
        try {
            manager.expenses.computeIfAbsent(category, k -> new HashSet<>()).add(this.nextPurchase());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    void addPurchase() { //get list input from console
        System.out.println(Purchase.purchasesToAdd());
        Category cat;
        int type = manager.validateInput("[1-5]");
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
        if (manager.expenses.isEmpty()) {
            System.out.println("Purchase List is Empty");
            return;
        }

        System.out.println(category);
        for (var item : manager.expenses.get(category)) {
            sum += item.getValue();
            System.out.println(item.getTitle() + ": $" + item.getValue());
        }
        System.out.println("Total Sum : $" + sum );
    }

    void showAllValues(HashMap<Category, HashSet<Purchase>> data) {
        double sum = 0.0;
        try {
            for (HashMap.Entry<Category, HashSet<Purchase>> entry : data.entrySet()) {
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
        int type = manager.validateInput("[1-6]");
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
                this.showAllValues(manager.expenses);
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
}

class Income {

    double income;

    public Income() {
        this.income = 0;
    }

    void updateIncome(double value) { //calculate total dollars on list
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

}

class BudgetManager {

    HashMap<Category, HashSet<Purchase>> expenses = new HashMap<>();
    Income inc;

    public BudgetManager() {
        this.inc = new Income();
    }

    int validateInput(String txtForRegex) {
        Scanner scanner = new Scanner(System.in);

        String userInput = scanner.nextLine();
        while(!userInput.matches(txtForRegex)) {
            System.out.println("Invalid format");
            userInput = scanner.nextLine();
        }
        return Integer.parseInt(userInput);
    }

    void exit() {
        System.out.println("Bye!");
    }


    static void showMenu() {
        System.out.println("Choose your action:\n" +
                "1) Add income\n" +
                "2) Add purchase\n" +
                "3) Show list of purchases\n" +
                "4) Balance\n" +
                "5) Save\n" +
                "6) Load\n" +
                "7) Analyze (sort)\n" +
                "0) Exit");

    }
}

class FileHandler {

    BudgetManager manager;

    FileHandler(BudgetManager manager) {
        this.manager = manager;
    }

    String saveToFile() {
        File file = new File("purchases.txt");
        double sum = 0.0;

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }

        if (manager.expenses.isEmpty()) {
            return "Expenses is empty";
        }

        try (FileWriter writer = new FileWriter(file, false)) {

            // HashMap<Integer, HashSet<Purchase>> expenses2 = new HashMap<>(expenses);
            writer.write(manager.inc.getIncome()+ "\n");
            for (HashMap.Entry<Category, HashSet<Purchase>> entry : manager.expenses.entrySet()) {
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
        File file = new File("purchases.txt");

        try (Scanner scan = new Scanner(file)) {
            String s = scan.nextLine();
            manager.inc.income = Double.parseDouble(s.substring(s.lastIndexOf("$" ) + 1));
            System.out.println(s);
            while(scan.hasNext()) {
                System.out.println(scan.nextLine());
            }

        } catch (FileNotFoundException ex) {
            return ex.getMessage();
        }

        return "Purchases were loaded";
    }

}

class Controls {

    BudgetManager manager = new BudgetManager();
    Expenses ex = new Expenses(manager);
    FileHandler handler = new FileHandler(manager);
    AnalyzePurchase analyzer = new AnalyzePurchase(manager);

    void chooseAction() {
        manager.showMenu();
        int x = manager.validateInput("[01-7]");
        System.out.println();
        switch (x) {
            case 1:
                manager.inc.setIncome();
                System.out.println();
                chooseAction();
                break;
            case 2:
                ex.addPurchase();
                System.out.println();
                chooseAction();
                break;
            case 3:
                ex.showPurchases();
                System.out.println();
                chooseAction();
                break;
            case 4:
                System.out.println(manager.inc.getIncome());
                System.out.println();
                chooseAction();
                break;
            case 5:
                System.out.println(handler.saveToFile());
                System.out.println();
                chooseAction();
                break;
            case 6:
                System.out.println(handler.loadFromFile());
                System.out.println();
                chooseAction();
                break;
            case 7:
                analyzer.analyze();
                System.out.println();
                chooseAction();
                break;
            case 0:
                manager.exit();
                break;
            default:
                chooseAction();
        }
    }
}

enum Category {
    FOOD,
    CLOTHES,
    ENTERTAINMENT,
    OTHER
}

