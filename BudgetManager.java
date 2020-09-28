package budget;
import  java.util.Scanner;



public class BudgetManager {
    public static void main(String[] args) {
        new MoneyCount().getSum();
    }
}

class MoneyCount {

    public double sum;

    public void getSum() {
       System.out.println(getInput());
       System.out.println( "Total: $" + sum);
    }

    private String getInput() { //get list input from console
        Scanner scanner = new Scanner(System.in);
        StringBuilder budgetList = new StringBuilder();
        String line;
        while(scanner.hasNext()) {
            line = scanner.nextLine();
            budgetList.append(line + "\n");
            calculateSum(line);
        }
        return budgetList.toString();
    }

    private void calculateSum(String line) { //calculate total dollars on list
        sum += Double.parseDouble(line.substring(line.indexOf("$") + 1));
    }

}
