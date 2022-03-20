public class Customer {
    // each customer can have a max of 2 accounts
    private String name;
    private String address;
    private int zipCode;
    private String ssn;

    public Customer(String inName, String inAddress, int inZip, String inSsn) {
        this.name = inName;
        this.address = inAddress;
        this.zipCode = inZip;
        this.ssn = inSsn;
    }

    public String getCustomerName() {
        return this.name;
    }

    public String getSsn() {
        return this.ssn;
    }

    public String getAddress() {
        return this.address;
    }

    public int getZipCode() {
        return this.zipCode;
    }

}
