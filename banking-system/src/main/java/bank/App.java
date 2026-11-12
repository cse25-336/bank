package bank.model;

import bank.model.*;

public class App {
    public static void main(String[] args) {
        Bank bank = new Bank("BAC Bank", "GBE");

        Customer c = bank.registerCustomer("Thabo", "Molefe", "Gaborone", "71234567", "1234", true);

        Account sav = bank.openAccount(c, "SAVINGS", 2000);
        Account inv = bank.openAccount(c, "INVESTMENT", 1000);
        Account chq = bank.openAccount(c, "CHEQUE", 0, "Acme Ltd", "Plot 1, Gaborone");

        ((ChequeAccount) chq).creditSalary(5000, "Acme Ltd");
        ((ChequeAccount) chq).withdraw(500);
        ((InvestmentAccount) inv).withdraw(200);

        bank.payMonthlyInterest();

        for (Account a : c.getAccounts()) {
            System.out.println(a.getAccountSummary());
        }
        System.out.println("Login OK? " + c.authenticate("1234"));
        c.viewTransactionHistory().forEach(System.out::println);
    }
}