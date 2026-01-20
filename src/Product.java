public class Product {

    private int id;
    private String name;
    private double price;
    private int quantity;

    public Product(int id, String name, double price, int quantity) {
        setId(id);
        setName(name);
        setPrice(price);
        setQuantity(quantity);
    }

    public void setId(int id) {
        if (id > 0) this.id = id;
        else this.id = 0;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty())
            this.name = name;
        else
            this.name = "Unknown";
    }

    public void setPrice(double price) {
        if (price >= 0) this.price = price;
        else this.price = 0;
    }

    public void setQuantity(int quantity) {
        if (quantity >= 0) this.quantity = quantity;
        else this.quantity = 0;
    }

    @Override
    public String toString() {
        return "Product{id=" + id +
                ", name='" + name +
                "', price=" + price +
                ", qty=" + quantity + "}";
    }
}
