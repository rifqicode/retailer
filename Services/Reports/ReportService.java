package Services.Reports;

import Services.Products.Product;
import Services.Products.ProductService;
import Services.Transactions.Transaction;
import Services.Transactions.TransactionItem;
import Services.Transactions.TransactionService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReportService {
    private final TransactionService transactionService;
    private final ProductService productService;

    public ReportService(TransactionService transactionService, ProductService productService) {
        this.transactionService = transactionService;
        this.productService = productService;
    }

    public void displayDailySalesSummary() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        List<Transaction> allTransactions = transactionService.getAllTransactions();
        List<Transaction> todayTransactions = new ArrayList<>();
        double totalRevenue = 0;
        int totalItemsSold = 0;

        for (Transaction trx : allTransactions) {
            if (trx.getDateTime().toLocalDate().isEqual(today)) {
                todayTransactions.add(trx);
                totalRevenue += trx.getGrandTotal();
                for (TransactionItem item : trx.getItems()) {
                    totalItemsSold += item.getQuantity();
                }
            }
        }

        System.out.println("\n==========================================");
        System.out.println("       LAPORAN PENJUALAN HARIAN           ");
        System.out.println("       Tanggal: " + today.format(formatter));
        System.out.println("==========================================");
        System.out.printf("Total Transaksi Sukses : %d transaksi%n", todayTransactions.size());
        System.out.printf("Total Unit Terjual     : %d unit%n", totalItemsSold);
        System.out.printf("Total Pendapatan Bersih: Rp%.0f%n", totalRevenue);
        if (!todayTransactions.isEmpty()) {
            double avgRevenue = totalRevenue / todayTransactions.size();
            System.out.printf("Rata-rata per Transaksi: Rp%.0f%n", avgRevenue);
        }
        System.out.println("==========================================");
    }

    public void displayLowStockProducts(int threshold) {
        List<Product> products = productService.getAllProducts();
        List<Product> lowStockList = new ArrayList<>();

        for (Product product : products) {
            if (product.getStock() < threshold) {
                lowStockList.add(product);
            }
        }

        System.out.println("\n==========================================");
        System.out.printf("      PRODUK STOK MENIPIS (< %d Unit)     %n", threshold);
        System.out.println("==========================================");

        if (lowStockList.isEmpty()) {
            System.out.printf(">> Aman! Tidak ada produk dengan stok di bawah %d unit.%n", threshold);
            return;
        }

        System.out.printf("%-6s %-20s %-15s %-10s %s%n", "ID", "Name", "Category", "Price", "Stock");
        System.out.println("-------------------------------------------------------------");
        for (Product p : lowStockList) {
            System.out.println(p);
        }
        System.out.println("-------------------------------------------------------------");
        System.out.printf("Total produk yang perlu restock: %d produk%n", lowStockList.size());
    }

    public void displayTopSellingProducts(int limit) {
        List<Transaction> transactions = transactionService.getAllTransactions();

        System.out.println("\n==========================================");
        System.out.printf("         TOP %d PRODUK TERLARIS           %n", limit);
        System.out.println("==========================================");

        if (transactions.isEmpty()) {
            System.out.println("Belum ada transaksi penjualan yang tercatat.");
            return;
        }

        // Map productId -> total qty sold
        Map<Integer, Integer> salesMap = new HashMap<>();
        Map<Integer, String> productNameMap = new HashMap<>();

        for (Transaction trx : transactions) {
            for (TransactionItem item : trx.getItems()) {
                int id = item.getProduct().getId();
                salesMap.put(id, salesMap.getOrDefault(id, 0) + item.getQuantity());
                productNameMap.put(id, item.getProduct().getName());
            }
        }

        // Urutkan berdasarkan total qty terjual terbanyak (descending)
        List<Map.Entry<Integer, Integer>> sortedSales = new ArrayList<>(salesMap.entrySet());
        sortedSales.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        System.out.printf("%-4s %-6s %-25s %s%n", "No", "ID", "Nama Produk", "Unit Terjual");
        System.out.println("--------------------------------------------------");

        int rank = 1;
        for (Map.Entry<Integer, Integer> entry : sortedSales) {
            if (rank > limit) break;
            int prodId = entry.getKey();
            int qtySold = entry.getValue();
            String prodName = productNameMap.get(prodId);

            System.out.printf("%-4d %-6d %-25s %d unit%n", rank, prodId, prodName, qtySold);
            rank++;
        }
        System.out.println("--------------------------------------------------");
    }
}
