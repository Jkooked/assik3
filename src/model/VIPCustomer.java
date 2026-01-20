package model;

import exception.InvalidDiscountException;

public class VIPCustomer extends Person implements Discountable {

    private double discount;

    public VIPCustomer(int id, String name, double discount) throws InvalidDiscountException {
        super(id, name);
        setDiscount(discount);
    }

    @Override
    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) throws InvalidDiscountException {
        if (discount < 0 || discount > 100)
            throw new InvalidDiscountException("Discount must be between 0 and 100");
        this.discount = discount;
    }

    @Override
    public void showInfo() {
        System.out.println("VIP Customer: " + name + ", discount = " + discount + "%");
    }
}
