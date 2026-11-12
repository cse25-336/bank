package bank.model;

public interface ITransactable {
    void deposit(double amount);
    void withdraw(double amount);
}