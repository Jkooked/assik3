import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<Person> people = new ArrayList<>();
    private static ArrayList<Product> products = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean running = true;

        while (running) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Add Customer");
            System.out.println("2. Add VIP Customer");
            System.out.println("3. View All People (Polymorphism)");
            System.out.println("4. Add Product");
            System.out.println("5. View Products");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addCustomer();
                case 2 -> addVIPCustomer();
                case 3 -> demonstratePolymorphism();
                case 4 -> addProduct();
                case 5 -> viewProducts();
                case 0 -> {
                    running = false;
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static void addCustomer() {
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Total purchases: ");
        double total = scanner.nextDouble();

        people.add(new Customer(id, name, total));
        System.out.println("Customer added!");
    }

    private static void addVIPCustomer() {
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Discount: ");
        double discount = scanner.nextDouble();

        people.add(new VIPCustomer(id, name, discount));
        System.out.println("VIP Customer added!");
    }

    private static void demonstratePolymorphism() {
        for (Person p : people) {
            p.showInfo(); // POLYMORPHISM

            if (p instanceof VIPCustomer) {
                VIPCustomer vip = (VIPCustomer) p; // CASTING
                System.out.println(" -> VIP discount: " + vip.getDiscount() + "%");
            }
        }
    }

    private static void addProduct() {
        System.out.print("Product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Price: ");
        double price = scanner.nextDouble();

        System.out.print("Quantity: ");
        int qty = scanner.nextInt();

        products.add(new Product(id, name, price, qty));
        System.out.println("Product added!");
    }

    private static void viewProducts() {
        for (Product p : products) {
            System.out.println(p);
        }
    }
}
