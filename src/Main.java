import database.PersonDAO;
import database.ProductDAO;
import exception.InvalidDiscountException;
import model.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final PersonDAO personDAO = new PersonDAO();
    private static final ProductDAO productDAO = new ProductDAO();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Choice: ");

            switch (choice) {
                case 1 -> addCustomer();
                case 2 -> addVIP();
                case 3 -> viewAllPeople();
                case 4 -> addProduct();
                case 5 -> viewProducts();

                case 6 -> updatePerson();
                case 7 -> deletePersonSafe();
                case 8 -> searchPeopleByName();
                case 9 -> searchVIPMinDiscount();

                case 10 -> updateProduct();
                case 11 -> deleteProductSafe();
                case 12 -> searchProductByName();
                case 13 -> searchProductByPriceRange();
                case 14 -> searchProductByMinPrice();

                case 0 -> running = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== MENU ===");
        System.out.println("1. Add Customer");
        System.out.println("2. Add VIP Customer");
        System.out.println("3. View All People (Polymorphism)");

        System.out.println("4. Add Product");
        System.out.println("5. View Products");

        System.out.println("6. Update Person");
        System.out.println("7. Delete Person (Safe Delete)");
        System.out.println("8. Search People by Name");
        System.out.println("9. Search VIP by Min Discount");

        System.out.println("10. Update Product");
        System.out.println("11. Delete Product (Safe Delete)");
        System.out.println("12. Search Product by Name");
        System.out.println("13. Search Product by Price Range");
        System.out.println("14. Search Product by Min Price");

        System.out.println("0. Exit");
    }

    // ----- People -----
    private static void addCustomer() {
        String name = readLine("Customer name: ");
        boolean ok = personDAO.insertCustomer(new Customer(0, name));
        System.out.println(ok ? "Customer added." : "Failed.");
    }

    private static void addVIP() {
        String name = readLine("VIP name: ");
        double discount = readDouble("Discount (0..100): ");
        try {
            boolean ok = personDAO.insertVIP(new VIPCustomer(0, name, discount));
            System.out.println(ok ? "VIP added." : "Failed.");
        } catch (InvalidDiscountException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewAllPeople() {
        List<Person> people = personDAO.getAllPeople();
        if (people.isEmpty()) {
            System.out.println("No people found.");
            return;
        }
        people.forEach(System.out::println);
    }

    private static void updatePerson() {
        int id = readInt("Enter person id to update: ");
        Person existing = personDAO.getById(id);
        if (existing == null) {
            System.out.println("Not found.");
            return;
        }
        System.out.println("Current: " + existing);

        String newName = readLine("New name: ");

        if (existing instanceof VIPCustomer) {
            double newDiscount = readDouble("New discount (0..100): ");
            try {
                VIPCustomer v = new VIPCustomer(id, newName, newDiscount);
                boolean ok = personDAO.updateVIP(v);
                System.out.println(ok ? "Updated." : "Failed.");
            } catch (InvalidDiscountException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            Customer c = new Customer(id, newName);
            boolean ok = personDAO.updateCustomer(c);
            System.out.println(ok ? "Updated." : "Failed.");
        }
    }

    private static void deletePersonSafe() {
        int id = readInt("Enter person id to delete: ");
        Person existing = personDAO.getById(id);
        if (existing == null) {
            System.out.println("Not found.");
            return;
        }
        System.out.println("You are about to delete: " + existing);
        String confirm = readLine("Type 'yes' to confirm: ");
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Cancelled.");
            return;
        }
        boolean ok = personDAO.deletePerson(id);
        System.out.println(ok ? "Deleted." : "Failed.");
    }

    private static void searchPeopleByName() {
        String q = readLine("Enter name keyword: ");
        List<Person> res = personDAO.searchByName(q);
        if (res.isEmpty()) System.out.println("No matches.");
        else res.forEach(System.out::println);
    }

    private static void searchVIPMinDiscount() {
        double min = readDouble("Min discount: ");
        List<Person> res = personDAO.searchVIPMinDiscount(min);
        if (res.isEmpty()) System.out.println("No matches.");
        else res.forEach(System.out::println);
    }

    // ----- Products -----
    private static void addProduct() {
        String name = readLine("Product name: ");
        double price = readDouble("Price: ");
        boolean ok = productDAO.insertProduct(new Product(0, name, price));
        System.out.println(ok ? "Product added." : "Failed.");
    }

    private static void viewProducts() {
        List<Product> products = productDAO.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        products.forEach(System.out::println);
    }

    private static void updateProduct() {
        int id = readInt("Enter product id to update: ");
        Product existing = productDAO.getById(id);
        if (existing == null) {
            System.out.println("Not found.");
            return;
        }
        System.out.println("Current: " + existing);

        String newName = readLine("New name: ");
        double newPrice = readDouble("New price: ");
        Product p = new Product(id, newName, newPrice);
        boolean ok = productDAO.updateProduct(p);
        System.out.println(ok ? "Updated." : "Failed.");
    }

    private static void deleteProductSafe() {
        int id = readInt("Enter product id to delete: ");
        Product existing = productDAO.getById(id);
        if (existing == null) {
            System.out.println("Not found.");
            return;
        }
        System.out.println("You are about to delete: " + existing);
        String confirm = readLine("Type 'yes' to confirm: ");
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Cancelled.");
            return;
        }
        boolean ok = productDAO.deleteProduct(id);
        System.out.println(ok ? "Deleted." : "Failed.");
    }

    private static void searchProductByName() {
        String q = readLine("Enter product name keyword: ");
        List<Product> res = productDAO.searchByName(q);
        if (res.isEmpty()) System.out.println("No matches.");
        else res.forEach(System.out::println);
    }

    private static void searchProductByPriceRange() {
        double min = readDouble("Min price: ");
        double max = readDouble("Max price: ");
        List<Product> res = productDAO.searchByPriceRange(min, max);
        if (res.isEmpty()) System.out.println("No matches.");
        else res.forEach(System.out::println);
    }

    private static void searchProductByMinPrice() {
        double min = readDouble("Min price: ");
        List<Product> res = productDAO.searchByMinPrice(min);
        if (res.isEmpty()) System.out.println("No matches.");
        else res.forEach(System.out::println);
    }

    // ----- helpers -----
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(scanner.nextLine().trim());
                return v;
            } catch (Exception e) {
                System.out.println("Enter a valid integer.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = Double.parseDouble(scanner.nextLine().trim());
                return v;
            } catch (Exception e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
