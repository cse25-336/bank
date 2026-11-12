package bank.model;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final String name;
    private final String branchCode;
    private final List<Customer> customers = new ArrayList<>();
    private final List<Account> accounts = new ArrayList<>();
    private int nextCustomerNo = 1;
    private int nextAccountNo = 1;

    public Bank(String name, String branchCode) {
        this.name = name;
        this.branchCode = branchCode;
    }

    public Customer registerCustomer(String firstName, String surname, String address,
                                     String phone, String pin, boolean employed) {
        String id = String.format("CUST%04d", nextCustomerNo++);
        Customer c = new Customer(id, firstName, surname, address, phone, id.toLowerCase(), pin, employed);
        customers.add(c);
        return c;
    }

    // Opens Savings or Investment accounts
    public Account openAccount(Customer customer, String type, double initialDeposit) {
        return openAccount(customer, type, initialDeposit, null, null);
    }

    // Overload: Cheque accounts also need employer details
    public Account openAccount(Customer customer, String type, double initialDeposit,
                               String employerName, String employerAddress) {
        String number = String.format("%s%05d", branchCode, nextAccountNo++);
        Account account;
        switch (type.toUpperCase()) {
            case "SAVINGS" -> {
                account = new SavingsAccount(number, branchCode, customer);
                if (initialDeposit > 0) account.deposit(initialDeposit);
            }
            case "INVESTMENT" ->
                account = new InvestmentAccount(number, branchCode, customer, initialDeposit);
            case "CHEQUE" -> {
                account = new ChequeAccount(number, branchCode, customer, employerName, employerAddress);
                if (initialDeposit > 0) account.deposit(initialDeposit);
            }
            default -> throw new IllegalArgumentException("Unknown account type: " + type);
        }
        customer.addAccount(account);
        accounts.add(account);
        return account;
    }

    public Account findAccount(String accountNumber) {
        for (Account a : accounts) {
            if (a.getAccountNumber().equals(accountNumber)) return a;
        }
        return null;
    }

    public void payMonthlyInterest() {
        for (Account a : accounts) {
            if (a instanceof IInterestBearing) { // polymorphism
                a.payInterest();
            }
        }
    }

    public String getName() { return name; }
    public String getBranchCode() { return branchCode; }
    public List<Customer> getCustomers() { return customers; }
    public List<Account> getAccounts() { return accounts; }
}