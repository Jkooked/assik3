import database.ProductDAO;
import model.Product;

public class TestInsert {
    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
        dao.insertProduct(new Product(0, "Cola", 450.0, 10));
    }
}
