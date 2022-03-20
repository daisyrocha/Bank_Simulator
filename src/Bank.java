/**
 * Title: Project 1 - Bank
 * Abstract: This program simulates a Bank with customers, account, and transaction information for a bank.
 * Author: Daisy Rocha-Montanez
 * Date: 10/09/2021
 */



import org.w3c.dom.ls.LSOutput;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bank {
    ArrayList<Account> accounts = new ArrayList<>(30);
    ArrayList<Customer> customers = new ArrayList<>(30);
    ArrayList<Transaction> transactions = new ArrayList<>(100);

    private String bankName;
    private int numOfCustomers;
    private int numOfAccounts;

    public Bank(String name) {
        this.bankName = name;
    }

    public String getBankName() {
        return this.bankName;
    }

    public int getNumCustomers() {
        return this.numOfCustomers;
    }

    public int getNumAccounts() {
        return this.numOfAccounts;
    }


    public void readData(String file) {
        Scanner inputStream = null;

        try {
            inputStream = new Scanner(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            System.out.println("File not found or could not be opened.");
            System.exit(0);
        }

        // number of Customers
        int numCustomers = inputStream.nextInt();
        this.numOfCustomers += numCustomers;
        // swallow newline character
        inputStream.nextLine();

        // This for loop will create Customer objects and will add them to the ArrayList of type Customer
        for (int i = 0; i < numCustomers; i++) {
            String entireLine = inputStream.nextLine();
            // Splitting the entire line by comma and adding it to an array
            String segment[] = entireLine.split(",");
            Customer customerFromFile = new Customer(segment[0], segment[1], Integer.parseInt(segment[2]), segment[3]);
            // check if the customerFromFile's ssn is an existing customer
            boolean found = false;
            for (Customer c : customers) {
                if (customerFromFile.getSsn().equals(c.getSsn())) {
                    System.out.println("Customer already in system");
                    customerFromFile = null;    // I do this to free the space of memory I allocated above.
                    found = true;
                }
            }
            // if customer is not already in the system, add customer to our customers arraylist
            if (!found) {
                customers.add(customerFromFile);
            }
        }


        int numAccounts = inputStream.nextInt();
        int addedAccounts = 0;

        // swallow newline character;
        inputStream.nextLine();

        for (int i = 0; i < numAccounts; i++) {
            String entireLine = inputStream.nextLine();
            // Splitting the line by comma and adding it to an array
            String seg[] = entireLine.split(",");
            Account accountFromFile = new Account(seg[0], Integer.parseInt(seg[1]), Integer.parseInt(seg[2]), Double.parseDouble(seg[3]));
            int cs = Integer.parseInt(seg[2]);

            boolean add = false;
            // loop through the customers arraylist
            for (Customer c : customers) {
                // check if the ssn belongs to a customer
                if (accountFromFile.getAccountSsn().equals(c.getSsn())) {
                    // System.out.println("*** YES THIS IS A VALID CUSTOMER ***");
                    add = true;
                }

                // this makes sure we don't have duplicate account numbers.
                // if account number already exists, we don't add this new account to our arraylist
                for (Account a : accounts) {
                    if (accountFromFile.getAccountNumber() == a.getAccountNumber()) {
                        add = false;
                    }
                }
            }

            if (add) {
                accounts.add(accountFromFile);
                addedAccounts++;
            }
        }
        this.numOfAccounts = addedAccounts;
    }


    public void bankInfo() {
        System.out.println("Bank Name: " + this.getBankName());
        System.out.println("Number of Customers: " + this.getNumCustomers());

        for (Customer c : customers) {
            System.out.println("\t" + c.getCustomerName() + ": " + c.getSsn());
        }

        System.out.println("Number of Accounts: " + this.getNumAccounts());
        double totalBalance = 0.0;
        for (Account a : accounts) {
            System.out.printf("\t" + a.getAccountNumber() + ": $%.2f \n", a.getBalance());
            totalBalance += a.getBalance();
        }

        System.out.printf("Total Balance: $%.2f\n", totalBalance);
    }


    public void accountInfo(int accNum) {
        boolean found = false;
        for (Account a : accounts) {
            // if accNum exists
            // if accNum is equal to an element in the accounts arraylist
            if (a.getAccountNumber() == accNum) {
                found = true;
                System.out.println("-Number: " + a.getAccountNumber());
                if (a.getAccountType() == 1) {
                    System.out.println("-Checking");
                } else {
                    System.out.println("Savings");
                }
                System.out.printf("-Balance: $%.2f\n", a.getBalance());
                for (Customer c : customers) {
                    if (a.getAccountSsn().equals(c.getSsn())) {
                        System.out.println("-Customer: " + c.getCustomerName());
                    }
                }
            }
        }
        if (!found) {
            System.out.println("Account (" + accNum + ") does not exist.");
        }
    }


    public void deposit(int acc, double amount) {
        for (Account a : accounts) {
            if (a.getAccountNumber() == acc) {
                if (amount > 0) {
                    a.setDepositBalance(amount);
                }
            }
        }

        Transaction deposit = new Transaction(acc, "Deposit", amount);
        transactions.add(deposit);
    }


    public void withdraw(int acc, double amount) {
        for (Account a : accounts) {
            if (a.getAccountNumber() == acc) {
                a.setWithdrawBalance(amount);
            }
        }

        Transaction withdraw = new Transaction(acc, "Withdraw", amount);
        transactions.add(withdraw);
    }


    public boolean closeAccount(int acc) {
        int index = -1;
        for (Account a : accounts) {
            index++;
            if (a.getAccountNumber() == acc) {
                Transaction close = new Transaction(acc, "Account closed");
                transactions.add(close);
                accounts.remove(index);
                this.numOfAccounts--;
                return true;
            }
        }
        return false;
    }

    public void transaction(int acc) {
        boolean found = false;
        for(Transaction t : transactions) {
            if(t.getAccountNumber() == acc) {
                found = true;
                if(!t.getTransactionType().equals("Account closed")) {
                    System.out.printf("-Account Number: " + t.getAccountNumber() + ", " + t.getTransactionType() + " ($%.2f), " + t.getFormattedTime() + "\n", t.getAmount());
                }

                if(t.getTransactionType().equals("Account closed")) {
                    System.out.println("-Account Number: " + t.getAccountNumber() + ", " + t.getTransactionType() + ", " + t.getFormattedTime());
                }
            }
        }

        if(!found) {
            System.out.println("-No transactions for account " + acc);
        }
    }




    public void newCustomer(String name, String address, int zip, String social) {
        Customer newCustomer = new Customer(name, address, zip, social);
        boolean existing = false;
        for(Customer c : customers) {
            if(c.getSsn().equals(social)) {
                existing = true;
                System.out.println(name + " is NOT added - Duplicated SSN.");
            }
        }

        if(!existing) {
            customers.add(newCustomer);
            System.out.println(name + " is added.");
            this.numOfCustomers++;
        }
    }

    public void newAccount(String social, int accNum, int accType, double amount) {
        Account newAccount = new Account(social, accNum, accType, amount);
        boolean cont = true;
        boolean existingCustomer = false;
        String name = "";
        String ssn = "";
        int aType;

        // we check if the SSN belongs to one of our customers
        for(Customer c : customers) {
            if(newAccount.getAccountSsn().equals(c.getSsn())) {
                name = c.getCustomerName();
                ssn = c.getSsn();
                existingCustomer = true;
                // because it is a customer, we know that we can create an account
            }
        }

        for(Account a : accounts) {
            if(newAccount.getAccountSsn().equals(a.getAccountSsn())) {
                if(newAccount.getAccountType() == a.getAccountType()) {
                    if(a.getAccountType() == 1) {
                        System.out.println("Account creation failed -" + name + " (" + ssn + ") already has a checking account");
                        cont = false;
                    } else {
                        System.out.println("Account creation failed -" + name + " (" + ssn + ") already has a savings account");
                        cont = false;
                    }
                }
            }

            if(newAccount.getAccountNumber() == a.getAccountNumber()) {
                System.out.println("Account creating failed - Account " + a.getAccountNumber() + " already exists");
                cont = false;
            }
        }

        if(existingCustomer && cont) {
            accounts.add(newAccount);
            this.numOfAccounts++;
            System.out.println("Account creation - Number: " + newAccount.getAccountNumber() + ", Customer: " + name);
        }

        if(!existingCustomer) {
            System.out.println("Account creation failed -" + social + " is not a customer");
        }
    }

    void customerInfoWithSSN(int lastFour) {
        boolean found = false;
        boolean customerAccount = false;
        String customerSsn = "";
        for(Customer c : customers) {
            if(String.valueOf(lastFour).equals(c.getSsn().substring(7,11))) {
                found = true;
                customerSsn = c.getSsn();
                System.out.println("Name: " + c.getCustomerName());
                System.out.println(c.getAddress() + ", " + c.getZipCode());
                System.out.println("SSN: " + c.getSsn());

                for(Account a : accounts) {
                    if(a.getAccountSsn().equals(customerSsn)) {
                        if(a.getAccountType() == 1) {
                            customerAccount = true;
                            System.out.printf("Checking (" + a.getAccountNumber() + "), $%.2f\n", a.getBalance());
                        }

                        if (a.getAccountType() == 2) {
                            customerAccount = true;
                            System.out.printf("Savings (" + a.getAccountNumber() + "), $%.2f\n", a.getBalance());
                        }
                    }
                }
            }
        }

        if(!found) {
            System.out.println("No customer with " + lastFour);
        } else if(!customerAccount) {
            System.out.println("No account");
        }
    }

    public void removeCustomer(String ssn) {
        int customerIndex = -1;
        int accountIndex = -1;
        boolean exists = false;             // this will tell us if customer exists or not
        boolean remove = false;

        // checking if the ssn belongs to one of our customers
        for(Customer c : customers) {
            customerIndex++;
            if(c.getSsn().equals(ssn)) {
                exists = true;
                System.out.println("Customer removed - SSN: " + ssn + ", Customer: " + c.getCustomerName());
            }
        }

        for(Account a : accounts) {
            accountIndex++;
            if(a.getAccountSsn().equals(ssn)) {
                exists = true;
                System.out.printf(" Account closed -Number: " + a.getAccountNumber() + ", Balance: %.2f\n", a.getBalance());
                Transaction removedCustomer = new Transaction(a.getAccountNumber(), "Account closed");
                transactions.add(removedCustomer);
            }
        }


        if(exists) {
            customers.remove(customerIndex);
            this.numOfCustomers--;
            accounts.remove(accountIndex);
            this.numOfAccounts--;
        }

        if(!exists) {
            System.out.println("Customer remove failed. SSN does not exist.");
        }

    }

}
