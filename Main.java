import java.util.Scanner;
import Services.Products.ProductMenu;
import Services.Products.ProductService;
import Services.Reports.ReportMenu;
import Services.Reports.ReportService;
import Services.Transactions.TransactionMenu;
import Services.Transactions.TransactionService;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    // Inisialisasi Services
    private static final ProductService productService = new ProductService();
    private static final TransactionService transactionService = new TransactionService(productService);
    private static final ReportService reportService = new ReportService(transactionService, productService);

    // Inisialisasi Menus (Views)
    private static final ProductMenu productMenu = new ProductMenu(productService, scanner);
    private static final TransactionMenu transactionMenu = new TransactionMenu(transactionService, productService, scanner);
    private static final ReportMenu reportMenu = new ReportMenu(reportService, scanner);

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   SELAMAT DATANG DI RETAILER MANAGEMENT SYSTEM   ");
        System.out.println("==================================================");

        while (true) {
            displayMainMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    productMenu.displayMenu();
                    break;
                case 2:
                    transactionMenu.displayMenu();
                    break;
                case 3:
                    reportMenu.displayMenu();
                    break;
                case 99:
                    System.out.println("\nTerima kasih telah menggunakan sistem ini. Sampai jumpa!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n========== MENU UTAMA ==========");
        System.out.println("1. Manajemen Produk (Product)");
        System.out.println("2. Pemrosesan Transaksi (Kasir)");
        System.out.println("3. Laporan & Analitik (Reports)");
        System.out.println("99. Keluar");
        System.out.println("================================");
    }

    private static int getUserChoice() {
        System.out.print("Pilih opsi menu: ");
        while (true) {
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Konsumsi newline
                return choice;
            } catch (java.util.InputMismatchException e) {
                System.out.println("[!] Input harus berupa angka bulat! Silakan coba lagi.");
                scanner.nextLine();
                System.out.print("Pilih opsi menu: ");
            }
        }
    }
}