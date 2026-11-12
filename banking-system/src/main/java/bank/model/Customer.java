package bank.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {
    private final String customerId;
    private final String username;
    private String hashedPin;
    private final boolean employed;
    private final List<Account> accounts = new ArrayList<>();

    public Customer(String customerId, String firstName, String surname, String address,
                    String phone, String username, String pin, boolean employed) {
        super(firstName, surname, address, phone);
        this.customerId = customerId;
        this.username = username;
        this.hashedPin = hash(pin);
        this.employed = employed;
    }

    public boolean authenticate(String enteredPin) {
        return hashedPin.equals(hash(enteredPin));
    }

    public void changePin(String oldPin, String newPin) {
        if (!authenticate(oldPin)) throw new IllegalArgumentException("Old PIN is incorrect.");
        this.hashedPin = hash(newPin);
    }

    public void addAccount(Account account) { accounts.add(account); }

    public void closeAccount(String accountNumber) {
        accounts.removeIf(a -> a.getAccountNumber().equals(accountNumber));
    }

    public List<Transaction> viewTransactionHistory() {
        List<Transaction> all = new ArrayList<>();
        for (Account a : accounts) all.addAll(a.getTransactions());
        return all;
    }

    private static String hash(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(pin.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getCustomerId() { return customerId; }
    public String getUsername() { return username; }
    public String getHashedPin() { return hashedPin; }
    public boolean isEmployed() { return employed; }
    public List<Account> getAccounts() { return accounts; }

    @Override
    public String toString() { return customerId + " - " + getFullName(); }
}