package bank.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Account {
    private final String accountNumber;
    private double balance;
    private final String branch;
    private final Customer owner;
    private final List<Transaction> transactions = new ArrayList<>();

    // An account cannot exist without a customer
    protected Account(String accountNumber, String branch, Customer owner) {
        if (owner == null) {
            throw new IllegalArgumentException("An account must belong to a customer.");
        }
        this.accountNumber = accountNumber;
        this.branch = branch;
        this.owner = owner;
    }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit must be greater than zero.");
        credit(amount, "DEPOSIT");
    }

    // Overloading
    public void deposit(double amount, String description) {
        deposit(amount);
        System.out.println("Deposit of BWP" + amount + " (" + description + ")");
    }

    // Each account type has its own interest rule
    public abstract double calculateInterest();

    // Pays the calculated interest into the account
    public void payInterest() {
        double interest = calculateInterest();
        if (interest > 0) credit(interest, "INTEREST");
    }

    protected void credit(double amount, String type) {
        balance += amount;
        transactions.add(new Transaction(type, amount, balance));
    }

    protected void debit(double amount, String type) {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal must be greater than zero.");
        if (amount > balance) throw new IllegalArgumentException("Insufficient funds.");
        balance -= amount;
        transactions.add(new Transaction(type, amount, balance));
    }

    public double getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }
    public String getBranch() { return branch; }
    public Customer getOwner() { return owner; }
    public List<Transaction> getTransactions() { return Collections.unmodifiableList(transactions); }

    public String getAccountSummary() {
        return getClass().getSimpleName() + " [" + accountNumber + "] Branch: " + branch
             + " | Owner: " + owner.getFullName()
             + " | Balance: BWP" + String.format("%.2f", balance);
    }

    @Override
    public String toString() { return getAccountSummary(); }
}