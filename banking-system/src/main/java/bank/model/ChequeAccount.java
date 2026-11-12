package bank.model;

public class ChequeAccount extends Account implements ITransactable {
    private final String employerName;
    private final String employerAddress;

    public ChequeAccount(String accountNumber, String branch, Customer owner,
                         String employerName, String employerAddress) {
        super(accountNumber, branch, owner);
        if (!owner.isEmployed()) {
            throw new IllegalArgumentException("A Cheque account is only for employed customers.");
        }
        if (employerName == null || employerName.isBlank()
                || employerAddress == null || employerAddress.isBlank()) {
            throw new IllegalArgumentException("Employer details are required.");
        }
        this.employerName = employerName;
        this.employerAddress = employerAddress;
    }

    @Override
    public double calculateInterest() { return 0.0; } // Cheque accounts earn no interest

    @Override
    public void deposit(double amount) {
        super.deposit(amount);
    }

    @Override
    public void withdraw(double amount) {
        debit(amount, "WITHDRAWAL");
    }

    public void creditSalary(double amount, String employer) {
        if (amount <= 0) throw new IllegalArgumentException("Salary must be greater than zero.");
        if (!employer.equalsIgnoreCase(employerName)) {
            throw new IllegalArgumentException("Employer does not match the one on record.");
        }
        credit(amount, "SALARY");
    }

    public String getEmployerName() { return employerName; }
    public String getEmployerAddress() { return employerAddress; }
}