package Services.Products;

import java.util.Scanner;

public class ProductMenu {
    private final ProductService productService;
    private final Scanner scanner;

    public ProductMenu(ProductService productService, Scanner scanner) {
        this.productService = productService;
        this.scanner = scanner;
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n--- Product Management ---");
            System.out.println("11. Add Product");
            System.out.println("12. Display Products");
            System.out.println("13. Update Product");
            System.out.println("14. Search Product by Name");
            System.out.println("15. Delete Product");
            System.out.println("0.  Back to Main Menu");

            int choice = getUserChoice();

            if (choice == 0) {
                break; // Kembali ke Main Menu
            }

            switch (choice) {
                case 11:
                    handleAddProduct();
                    break;
                case 12:
                    productService.displayProducts();
                    break;
                case 13:
                    handleUpdateProduct();
                    break;
                case 14:
                    handleSearchProduct();
                    break;
                case 15:
                    handleDeleteProduct();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleAddProduct() {
        System.out.println("\n[ Add New Product ]");
        System.out.print("Enter Product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Konsumsi newline

        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Product Category: ");
        String category = scanner.nextLine();

        System.out.print("Enter Product Price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter Product Stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // Konsumsi newline

        productService.createProduct(id, name, category, price, stock);
        System.out.println(">> Product successfully added!");
    }

    private void handleUpdateProduct() {
        System.out.println("\n[ Update Product ]");
        System.out.print("Enter Product ID to Update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter New Product Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter New Product Category: ");
        String category = scanner.nextLine();

        System.out.print("Enter New Product Price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter New Product Stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine();

        productService.updateProduct(id, name, category, price, stock);
    }

    private void handleSearchProduct() {
        System.out.println("\n[ Search Product ]");
        System.out.print("Enter Product Name to Search: ");
        String name = scanner.nextLine();
        productService.searchProductsByName(name);
    }

    private void handleDeleteProduct() {
        System.out.println("\n[ Delete Product ]");
        System.out.print("Enter Product ID to Delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        productService.deleteProduct(id);
    }

    private int getUserChoice() {
        System.out.print("Enter your choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number!");
            scanner.next();
            System.out.print("Enter your choice: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Konsumsi newline
        return choice;
    }
}
