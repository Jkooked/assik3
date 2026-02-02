import database.ProductDAO;
import model.Product;

public class TestInsert {
    public static void main(String[] args) {

        Product product = new Product(
                0,          // id (0, т.к. SERIAL в БД)
                "Cola",
                450.0
        );

        new ProductDAO().insertProduct(product);
    }
}
