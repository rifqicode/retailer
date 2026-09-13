package Services.Products;

import Exceptions.ProductNotFoundException;

import java.util.InputMismatchException;
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
            System.out.println("\n--- Manajemen Produk ---");
            System.out.println("11. Tambah Produk");
            System.out.println("12. Tampilkan Semua Produk");
            System.out.println("13. Perbarui Produk");
            System.out.println("14. Cari Produk berdasarkan Nama");
            System.out.println("15. Hapus Produk");
            System.out.println("0.  Kembali ke Menu Utama");

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
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    private void handleAddProduct() {
        System.out.println("\n[ Tambah Produk Baru ]");
        System.out.print("Masukkan ID Produk: ");
        int id = getIntInput();

        System.out.print("Masukkan Nama Produk: ");
        String name = scanner.nextLine();

        System.out.print("Masukkan Kategori Produk: ");
        String category = scanner.nextLine();

        System.out.print("Masukkan Harga Produk: Rp");
        double price = getDoubleInput();

        System.out.print("Masukkan Stok Awal: ");
        int stock = getIntInput();

        productService.createProduct(id, name, category, price, stock);
        System.out.println(">> Produk '" + name + "' berhasil ditambahkan!");
    }

    private void handleUpdateProduct() {
        System.out.println("\n[ Perbarui Data Produk ]");
        System.out.print("Masukkan ID Produk yang ingin diperbarui: ");
        int id = getIntInput();

        try {
            // Cek dulu apakah produk ada (akan melempar ProductNotFoundException jika tidak ada)
            Product existing = productService.getProductById(id);
            System.out.println("Produk ditemukan: " + existing.getName());

            System.out.print("Masukkan Nama Baru: ");
            String name = scanner.nextLine();

            System.out.print("Masukkan Kategori Baru: ");
            String category = scanner.nextLine();

            System.out.print("Masukkan Harga Baru: Rp");
            double price = getDoubleInput();

            System.out.print("Masukkan Jumlah Stok Baru: ");
            int stock = getIntInput();

            productService.updateProduct(id, name, category, price, stock);
        } catch (ProductNotFoundException e) {
            System.out.println("[!] Gagal memperbarui: " + e.getMessage());
        }
    }

    private void handleSearchProduct() {
        System.out.println("\n[ Cari Produk ]");
        System.out.print("Masukkan Nama Produk yang dicari: ");
        String name = scanner.nextLine();
        productService.searchProductsByName(name);
    }

    private void handleDeleteProduct() {
        System.out.println("\n[ Hapus Produk ]");
        System.out.print("Masukkan ID Produk yang ingin dihapus: ");
        int id = getIntInput();

        try {
            productService.deleteProduct(id);
        } catch (ProductNotFoundException e) {
            System.out.println("[!] Gagal menghapus: " + e.getMessage());
        }
    }

    private int getUserChoice() {
        System.out.print("Pilih opsi: ");
        return getIntInput();
    }

    private int getIntInput() {
        while (true) {
            try {
                int val = scanner.nextInt();
                scanner.nextLine(); // Konsumsi newline
                return val;
            } catch (InputMismatchException e) {
                System.out.println("[!] Input harus berupa angka bulat! Silakan coba lagi.");
                scanner.nextLine(); // Bersihkan buffer yang error
                System.out.print("Input angka: ");
            }
        }
    }

    private double getDoubleInput() {
        while (true) {
            try {
                double val = scanner.nextDouble();
                scanner.nextLine(); // Konsumsi newline
                return val;
            } catch (InputMismatchException e) {
                System.out.println("[!] Input harus berupa angka nominal yang valid! Silakan coba lagi.");
                scanner.nextLine(); // Bersihkan buffer yang error
                System.out.print("Input angka: ");
            }
        }
    }
}
