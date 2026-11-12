package bank.model;

public class SavingsAccount extends Account implements IInterestBearing {
    private double interestRate = 0.0005; // 0.05% monthly

    public SavingsAccount(String accountNumber, String branch, Customer owner) {
        super(accountNumber, branch, owner);
    }

    @Override
    public double calculateInterest() {
        return getBalance() * interestRate;
    }

    @Override
    public void deposit(double amount) {
        super.deposit(amount);
    }
}