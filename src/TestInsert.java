import database.ProductDAO;
import model.Product;

public class TestInsert {
    public static void main(String[] args) {

        Product product = new Product(
                "Cola",
                450.0,
                10
        );

        new ProductDAO().insertProduct(product);
    }
}
