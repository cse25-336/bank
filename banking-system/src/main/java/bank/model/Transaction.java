package bank.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transaction {
    private final String transactionId;
    private final LocalDateTime date;
    private final String type;
    private final double amount;
    private final double balanceAfter;

    public Transaction(String type, double amount, double balanceAfter) {
        this.transactionId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.date = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    public String getTransactionId() { return transactionId; }
    public LocalDateTime getDate() { return date; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public double getBalanceAfter() { return balanceAfter; }

    public String getReceipt() {
        return "Receipt " + transactionId + "\n"
             + "Date: " + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\n"
             + "Type: " + type + "\n"
             + "Amount: BWP" + String.format("%.2f", amount) + "\n"
             + "Balance after: BWP" + String.format("%.2f", balanceAfter);
    }

    @Override
    public String toString() {
        return transactionId + " | " + type + " | BWP" + String.format("%.2f", amount)
             + " | Balance: BWP" + String.format("%.2f", balanceAfter);
    }
}