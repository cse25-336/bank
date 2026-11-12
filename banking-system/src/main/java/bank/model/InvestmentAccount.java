package bank.model;

public class InvestmentAccount extends Account implements IInterestBearing, ITransactable {
    private double interestRate = 0.05; // 5% monthly
    public static final double MIN_OPENING_BALANCE = 500.00;

    public InvestmentAccount(String accountNumber, String branch, Customer owner, double initialDeposit) {
        super(accountNumber, branch, owner);
        if (initialDeposit < MIN_OPENING_BALANCE) {
            throw new IllegalArgumentException("Investment account needs an opening deposit of at least BWP500.00.");
        }
        super.deposit(initialDeposit);
    }

    @Override
    public double calculateInterest() {
        return getBalance() * interestRate;
    }

    @Override
    public void deposit(double amount) {
        super.deposit(amount);
    }

    @Override
    public void withdraw(double amount) {
        debit(amount, "WITHDRAWAL");
    }
}