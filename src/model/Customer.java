package model;

public class Customer extends Person {

    private double totalPurchases;

    public Customer(int id, String name, double totalPurchases) {
        super(id, name);
        setTotalPurchases(totalPurchases);
    }

    public double getTotalPurchases() { return totalPurchases; }

    public void setTotalPurchases(double totalPurchases) {
        if (totalPurchases < 0)
            throw new IllegalArgumentException("Total purchases cannot be negative");
        this.totalPurchases = totalPurchases;
    }

    @Override
    public void showInfo() {
        System.out.println("Customer: " + name + ", purchases = " + totalPurchases);
    }
}
