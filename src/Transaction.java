import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//import java.util.*;

public class Transaction {

    private int accountNumber;
    private String transactionType;    // this could also be a string
    private LocalDateTime time;
    private String formattedTime;
    private double amount;
//    private int numOfTransactions = 0;

    /** This can go anywhere we want... given by Avner

     **/
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss");
//    String formatDateTime = now.format(formatter);
//    System.out.println(formatDateTime);

    public Transaction(int accNum, String tranType, double amnt) {
        this.accountNumber = accNum;
        this.transactionType = tranType;
        this.time = now;
        this.formattedTime = now.format(formatter);
        this.amount = amnt;
//        this.numOfTransactions++;
    }

    public Transaction(int acc, String tranType) {
        this.accountNumber = acc;
        this.transactionType = tranType;
        this.time = now;
        this.formattedTime = now.format(formatter);
//        this.numOfTransactions++;
    }

    public int getAccountNumber() {
        return this.accountNumber;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public String getFormattedTime() {
        return this.formattedTime;
    }

    public double getAmount() {
        return this.amount;
    }

//    public int getNumOfTransactions() {
////        return this.numOfTransactions;
//    }

}
