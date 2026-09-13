package Services.Reports;

import java.util.Scanner;

public class ReportMenu {
    private final ReportService reportService;
    private final Scanner scanner;

    public ReportMenu(ReportService reportService, Scanner scanner) {
        this.reportService = reportService;
        this.scanner = scanner;
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n--- Laporan & Analitik ---");
            System.out.println("1. Ringkasan Penjualan Harian");
            System.out.println("2. Produk dengan Stok Menipis (< 10 unit)");
            System.out.println("3. Top 3 Produk Terlaris");
            System.out.println("0. Kembali ke Menu Utama");

            int choice = getUserChoice();

            if (choice == 0) {
                break;
            }

            switch (choice) {
                case 1:
                    reportService.displayDailySalesSummary();
                    break;
                case 2:
                    reportService.displayLowStockProducts(10);
                    break;
                case 3:
                    reportService.displayTopSellingProducts(3);
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    private int getUserChoice() {
        System.out.print("Pilih menu: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Harap masukkan angka yang valid!");
            scanner.next();
            System.out.print("Pilihan: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Bersihkan newline
        return choice;
    }
}
