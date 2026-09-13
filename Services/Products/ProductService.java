package Services.Products;

import java.util.ArrayList;

public class ProductService {
    private ArrayList<Product> products;

    public ProductService() {
        this.products = new ArrayList<>();
        initSampleData();
    }

    private void initSampleData() {
        addProduct(new Product(101, "Laptop Asus Vivobook", "Elektronik", 8500000, 5));
        addProduct(new Product(102, "Logitech Wireless Mouse", "Aksesoris", 150000, 25));
        addProduct(new Product(103, "Keychron K2 Keyboard", "Aksesoris", 1200000, 8));
        addProduct(new Product(104, "Kopi Arabika 250g", "F&B", 65000, 40));
        addProduct(new Product(105, "Tumbler 500ml", "Lifestyle", 120000, 15));
    }

    public ProductService(ArrayList<Product> products) {
        this.products = (products != null) ? products : new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void createProduct(int id, String name, String category, double price, int stock) {
        Product product = new Product(id, name, category, price, stock);
        addProduct(product);
    }

    public void displayProducts() {
        System.out.println("\nProduct List:");
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        System.out.printf("%-6s %-20s %-15s %-10s %s%n", "ID", "Name", "Category", "Price", "Stock");
        System.out.println("-------------------------------------------------------------");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public Product getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public Product updateProduct(int id, String name, String category, double price, int stock) {
        Product product = getProductById(id);
        if (product != null) {
            product.setName(name);
            product.setCategory(category);
            product.setPrice(price);
            product.setStock(stock);
        } else {
            System.out.println("Product with ID " + id + " not found.");
        }

        return product;
    }

    public void searchProductsByName(String name) {
        System.out.println("Search Results:");
        System.out.printf("%-6s %-20s %-15s %-10s %s%n", "ID", "Name", "Category", "Price", "Stock");
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                System.out.println(product);
            }
        }
    }

    public void deleteProduct(int id) {
        Product product = getProductById(id);
        if (product != null) {
            products.remove(product);
            System.out.println("Product with ID " + id + " has been deleted.");
        } else {
            System.out.println("Product with ID " + id + " not found.");
        }
    }

    public void updateStock(int id, int newStock) {
        Product product = getProductById(id);
        if (product != null) {
            product.setStock(newStock);
            System.out.println("Stock for product with ID " + id + " has been updated to " + newStock + ".");
        } else {
            System.out.println("Product with ID " + id + " not found.");
        }
    }

    public ArrayList<Product> getAllProducts() {
        return products;
    }
}