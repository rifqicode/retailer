package Services.Transactions;

import Services.Products.Product;
import Services.Products.ProductService;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    private List<Transaction> transactions;
    private ProductService productService;
    private int transactionCounter = 1000;

    public TransactionService(ProductService productService) {
        this.transactions = new ArrayList<>();
        this.productService = productService;
    }

    public String generateTransactionId() {
        transactionCounter++;
        return "TRX-" + transactionCounter;
    }

    public double calculateDiscountRate(double subtotal) {
        if (subtotal >= 1000000) {
            return 0.10; // Diskon 10% jika belanja >= 1 juta
        } else if (subtotal >= 500000) {
            return 0.05; // Diskon 5% jika belanja >= 500 ribu
        }
        return 0.0;
    }

    public Transaction processTransaction(List<TransactionItem> items, String paymentMethod, double amountPaid) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Daftar item belanja tidak boleh kosong.");
        }

        // 1. Validasi stok sebelum diproses
        for (TransactionItem item : items) {
            Product currentProduct = productService.getProductById(item.getProduct().getId());
            if (currentProduct == null) {
                throw new IllegalStateException("Produk '" + item.getProduct().getName() + "' tidak ditemukan di katalog.");
            }
            if (currentProduct.getStock() < item.getQuantity()) {
                throw new IllegalStateException("Stok untuk produk '" + currentProduct.getName() +
                        "' tidak mencukupi (Tersedia: " + currentProduct.getStock() + ", Diminta: " + item.getQuantity() + ").");
            }
        }

        // 2. Hitung subtotal & diskon
        double subtotal = 0;
        for (TransactionItem item : items) {
            subtotal += item.getSubtotal();
        }
        double discountRate = calculateDiscountRate(subtotal);
        double grandTotal = subtotal - (subtotal * discountRate);

        // 3. Validasi pembayaran tunai
        if ("Tunai".equalsIgnoreCase(paymentMethod) && amountPaid < grandTotal) {
            throw new IllegalArgumentException(String.format("Uang pembayaran tidak mencukupi! Tagihan: Rp%.0f, Dibayar: Rp%.0f", grandTotal, amountPaid));
        }

        // 4. Kurangi stok produk secara resmi
        for (TransactionItem item : items) {
            Product currentProduct = productService.getProductById(item.getProduct().getId());
            currentProduct.setStock(currentProduct.getStock() - item.getQuantity());
        }

        // 5. Buat dan simpan transaksi
        String trxId = generateTransactionId();
        Transaction transaction = new Transaction(trxId, items, discountRate, paymentMethod, amountPaid);
        transactions.add(transaction);

        return transaction;
    }

    public List<Transaction> getAllTransactions() {
        return transactions;
    }
}
