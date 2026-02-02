package model;

public class Customer extends Person {
    public Customer(int id, String name) {
        super(id, name);
    }

    @Override
    public String getType() { return "CUSTOMER"; }
}
