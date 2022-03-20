public class Account {
    //each customer can have a max of 2 accounts
    private boolean checking = false;   // 1
    private boolean savings = false;     // 2

    private int accountType;
    private int accountNumber;
    private String accountSsn;
    private double balance;

    public Account(String ssn, int accountNum, int accType, double bal) {
        this.accountSsn = ssn;
        this.accountNumber = accountNum;
        this.accountType = accType;
        this.balance = bal;
//        if(accType == 1) {
//            this.checking = true;
//        }
//
//        if(accType == 2) {
//            this.savings = true;
//        }
    }

    public int getAccountType() {
        return this.accountType;
    }

    public int getAccountNumber() {
        return this.accountNumber;
    }

    public String getAccountSsn() {
        return this.accountSsn;
    }

    public boolean getChecking() {
        return this.checking;
    }

    public boolean getSavings() {
        return this.savings;
    }

    public double getBalance(){
        return this.balance;
    }

    public void setChecking(boolean active) {
        this.checking = active;
    }

    public void setSavings(boolean active) {
        this.savings = active;
    }

    public void setDepositBalance(double add) {
        this.balance += add;
    }

    public void setWithdrawBalance(double subtract) {
        this.balance -= subtract;
    }

}
