
package menu;

import exception.InvalidDiscountException;
import model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuManager implements Menu {

    private final ArrayList<Person> people = new ArrayList<>();
    private final ArrayList<Product> products = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void displayMenu() {
        System.out.println("\n=== MENU ===");
        System.out.println("1. Add Customer");
        System.out.println("2. Add VIP Customer");
        System.out.println("3. View All People (Polymorphism)");
        System.out.println("4. Add Product");
        System.out.println("5. View Products");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
    }

    @Override
    public void run() {
        boolean running = true;

        while (running) {
            displayMenu();

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> addCustomer();
                    case 2 -> addVIPCustomer();
                    case 3 -> viewPeople();
                    case 4 -> addProduct();
                    case 5 -> viewProducts();
                    case 0 -> {
                        running = false;
                        System.out.println("Goodbye!");
                    }
                    default -> System.out.println("Invalid choice");
                }

            } catch (NumberFormatException e) { // required :contentReference[oaicite:6]{index=6}
                System.out.println("Input error: please enter a number.");
            }
        }
    }

    private void addCustomer() {
        try {
            System.out.print("ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Total purchases: ");
            double total = Double.parseDouble(scanner.nextLine());

            people.add(new Customer(id, name, total));
            System.out.println("Customer added!");
        } catch (NumberFormatException e) {
            System.out.println("Input error: numbers only.");
        } catch (IllegalArgumentException e) { // required :contentReference[oaicite:7]{index=7}
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private void addVIPCustomer() {
        try {
            System.out.print("ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Discount (0-100): ");
            double discount = Double.parseDouble(scanner.nextLine());

            people.add(new VIPCustomer(id, name, discount));
            System.out.println("VIP Customer added!");
        } catch (NumberFormatException e) {
            System.out.println("Input error: numbers only.");
        } catch (InvalidDiscountException e) { // custom exception :contentReference[oaicite:8]{index=8}
            System.out.println("Discount error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private void viewPeople() {
        if (people.isEmpty()) {
            System.out.println("No people yet.");
            return;
        }

        for (Person p : people) {
            p.showInfo(); // polymorphism

            // instanceof + casting demo (может спросить преподаватель)
            if (p instanceof VIPCustomer) {
                VIPCustomer vip = (VIPCustomer) p;
                System.out.println(" -> VIP discount: " + vip.getDiscount() + "%");
            }
        }
    }

    private void addProduct() {
        try {
            System.out.print("Product ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Price: ");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.print("Quantity: ");
            int qty = Integer.parseInt(scanner.nextLine());

            products.add(new Product(id, name, price, qty));
            System.out.println("Product added!");
        } catch (NumberFormatException e) {
            System.out.println("Input error: numbers only.");
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private void viewProducts() {
        if (products.isEmpty()) {
            System.out.println("No products yet.");
            return;
        }
        for (Product p : products) {
            System.out.println(p);
        }
    }
}
