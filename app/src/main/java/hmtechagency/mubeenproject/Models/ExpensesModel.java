package hmtechagency.mubeenproject.Models;

public class ExpensesModel {

    String Expenses,ExpensesName,ExpensesDescription,ExpensesDate,ExpensesTimeStamps;

    public ExpensesModel() {
    }

    public ExpensesModel(String expenses, String expensesName, String expensesDescription, String expensesDate, String expensesTimeStamps) {
        Expenses = expenses;
        ExpensesName = expensesName;
        ExpensesDescription = expensesDescription;
        ExpensesDate = expensesDate;
        ExpensesTimeStamps = expensesTimeStamps;
    }

    public String getExpenses() {
        return Expenses;
    }

    public void setExpenses(String expenses) {
        Expenses = expenses;
    }

    public String getExpensesName() {
        return ExpensesName;
    }

    public void setExpensesName(String expensesName) {
        ExpensesName = expensesName;
    }

    public String getExpensesDescription() {
        return ExpensesDescription;
    }

    public void setExpensesDescription(String expensesDescription) {
        ExpensesDescription = expensesDescription;
    }

    public String getExpensesDate() {
        return ExpensesDate;
    }

    public void setExpensesDate(String expensesDate) {
        ExpensesDate = expensesDate;
    }

    public String getExpensesTimeStamps() {
        return ExpensesTimeStamps;
    }

    public void setExpensesTimeStamps(String expensesTimeStamps) {
        ExpensesTimeStamps = expensesTimeStamps;
    }
}
