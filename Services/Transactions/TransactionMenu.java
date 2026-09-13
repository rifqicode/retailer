package Services.Transactions;

import Services.Products.Product;
import Services.Products.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionMenu {
    private final TransactionService transactionService;
    private final ProductService productService;
    private final Scanner scanner;

    public TransactionMenu(TransactionService transactionService, ProductService productService, Scanner scanner) {
        this.transactionService = transactionService;
        this.productService = productService;
        this.scanner = scanner;
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n--- Pemrosesan Transaksi ---");
            System.out.println("1. Buat Transaksi Baru (Kasir)");
            System.out.println("2. Lihat Riwayat Transaksi");
            System.out.println("0. Kembali ke Menu Utama");

            int choice = getUserChoice();

            if (choice == 0) {
                break;
            }

            switch (choice) {
                case 1:
                    handleNewTransaction();
                    break;
                case 2:
                    handleViewHistory();
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    private void handleNewTransaction() {
        List<Product> availableProducts = productService.getAllProducts();
        if (availableProducts == null || availableProducts.isEmpty()) {
            System.out.println("\n[!] Belum ada produk di katalog. Tambahkan produk terlebih dahulu di Product Management.");
            return;
        }

        System.out.println("\n==========================================");
        System.out.println("       TRANSAKSI PENJUALAN BARU           ");
        System.out.println("==========================================");

        List<TransactionItem> cart = new ArrayList<>();

        while (true) {
            // Tampilkan katalog produk
            productService.displayProducts();

            System.out.print("\nMasukkan ID Produk yang dibeli (ketik 0 untuk selesai memilih item): ");
            int productId = getIntInput();
            if (productId == 0) {
                break;
            }

            Product selectedProduct = productService.getProductById(productId);
            if (selectedProduct == null) {
                System.out.println("[!] Produk dengan ID " + productId + " tidak ditemukan!");
                continue;
            }

            if (selectedProduct.getStock() <= 0) {
                System.out.println("[!] Stok produk ini sudah habis!");
                continue;
            }

            // Cek jumlah yang sudah ada di cart
            int qtyInCart = 0;
            TransactionItem existingItem = null;
            for (TransactionItem item : cart) {
                if (item.getProduct().getId() == productId) {
                    qtyInCart = item.getQuantity();
                    existingItem = item;
                    break;
                }
            }

            int maxAvailable = selectedProduct.getStock() - qtyInCart;
            if (maxAvailable <= 0) {
                System.out.println("[!] Anda sudah memasukkan semua stok yang tersedia ke keranjang!");
                continue;
            }

            System.out.printf("Masukkan jumlah unit untuk '%s' (Tersedia: %d): ", selectedProduct.getName(), maxAvailable);
            int qty = getIntInput();

            if (qty <= 0) {
                System.out.println("[!] Jumlah unit harus lebih dari 0!");
                continue;
            }

            if (qty > maxAvailable) {
                System.out.printf("[!] Stok tidak cukup! Maksimal yang bisa ditambahkan: %d%n", maxAvailable);
                continue;
            }

            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + qty);
            } else {
                cart.add(new TransactionItem(selectedProduct, qty));
            }

            System.out.printf(">> %d unit '%s' berhasil dimasukkan ke keranjang.%n", qty, selectedProduct.getName());

            // Tampilkan ringkasan sementara keranjang
            displayCartPreview(cart);

            System.out.print("\nTambah item lain? (1 = Ya, 2 = Selesai & Lanjut Bayar, 0 = Batalkan Transaksi): ");
            int nextStep = getIntInput();
            if (nextStep == 2) {
                break;
            } else if (nextStep == 0) {
                System.out.println(">> Transaksi dibatalkan.");
                return;
            }
        }

        if (cart.isEmpty()) {
            System.out.println(">> Keranjang kosong. Transaksi dibatalkan.");
            return;
        }

        // Lanjut ke Proses Pembayaran
        handleCheckout(cart);
    }

    private void displayCartPreview(List<TransactionItem> cart) {
        System.out.println("\n--- Keranjang Belanja Saat Ini ---");
        System.out.printf("%-20s %-5s %-12s %s%n", "Item", "Qty", "Harga", "Subtotal");
        System.out.println("--------------------------------------------------");
        double subtotal = 0;
        for (TransactionItem item : cart) {
            System.out.printf("%-20s %3d   Rp%-10.0f Rp%-10.0f%n",
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getPriceAtPurchase(),
                    item.getSubtotal());
            subtotal += item.getSubtotal();
        }
        System.out.println("--------------------------------------------------");
        System.out.printf("Subtotal Sementara    : Rp%.0f%n", subtotal);
    }

    private void handleCheckout(List<TransactionItem> cart) {
        double subtotal = 0;
        for (TransactionItem item : cart) {
            subtotal += item.getSubtotal();
        }

        double discountRate = transactionService.calculateDiscountRate(subtotal);
        double discountAmount = subtotal * discountRate;
        double grandTotal = subtotal - discountAmount;

        System.out.println("\n==========================================");
        System.out.println("           RINGKASAN PEMBAYARAN           ");
        System.out.println("==========================================");
        System.out.printf("Subtotal              : Rp%.0f%n", subtotal);
        if (discountAmount > 0) {
            System.out.printf("Diskon (%.0f%%)          : -Rp%.0f (Promo Belanja)%n", (discountRate * 100), discountAmount);
        } else {
            System.out.println("Diskon                : Rp0 (Belanja min. Rp500.000 untuk diskon 5%, Rp1.000.000 untuk 10%)");
        }
        System.out.printf("TOTAL TAGIHAN         : Rp%.0f%n", grandTotal);
        System.out.println("==========================================");

        System.out.println("Pilih Metode Pembayaran:");
        System.out.println("1. Tunai (Cash)");
        System.out.println("2. Transfer Bank");
        System.out.println("0. Batalkan Transaksi");

        int payChoice = getIntInput();
        if (payChoice == 0) {
            System.out.println(">> Transaksi dibatalkan.");
            return;
        }

        String paymentMethod;
        double amountPaid;

        if (payChoice == 1) {
            paymentMethod = "Tunai";
            while (true) {
                System.out.printf("Masukkan nominal uang tunai (Min. Rp%.0f): Rp", grandTotal);
                amountPaid = getDoubleInput();

                if (amountPaid < grandTotal) {
                    System.out.printf("[!] Uang kurang Rp%.0f! Silakan bayar sesuai atau lebih dari total tagihan.%n", (grandTotal - amountPaid));
                    System.out.print("Coba lagi? (1 = Ya, 0 = Batalkan Transaksi): ");
                    int retry = getIntInput();
                    if (retry == 0) {
                        System.out.println(">> Transaksi dibatalkan.");
                        return;
                    }
                } else {
                    break;
                }
            }
        } else if (payChoice == 2) {
            paymentMethod = "Transfer";
            amountPaid = grandTotal;
            System.out.println("\n--- Instruksi Transfer Bank ---");
            System.out.println("Bank BCA      : 888-091-2345");
            System.out.println("Atas Nama     : Retailer Store Official");
            System.out.printf("Jumlah Bayar  : Rp%.0f%n", grandTotal);
            System.out.println("-------------------------------");
            System.out.print("Konfirmasi bahwa transfer sudah diterima? (1 = Ya / Selesai, 0 = Batal): ");
            int confirm = getIntInput();
            if (confirm != 1) {
                System.out.println(">> Transaksi dibatalkan.");
                return;
            }
        } else {
            System.out.println("[!] Pilihan pembayaran tidak valid. Transaksi dibatalkan.");
            return;
        }

        // Proses di TransactionService
        try {
            Transaction trx = transactionService.processTransaction(cart, paymentMethod, amountPaid);
            System.out.println("\n>> Transaksi BERHASIL diproses!");
            trx.printReceipt();
        } catch (Exception e) {
            System.out.println("[ERROR] Gagal memproses transaksi: " + e.getMessage());
        }
    }

    private void handleViewHistory() {
        List<Transaction> list = transactionService.getAllTransactions();
        System.out.println("\n==========================================");
        System.out.println("          RIWAYAT SEMUA TRANSAKSI         ");
        System.out.println("==========================================");

        if (list.isEmpty()) {
            System.out.println("Belum ada transaksi yang tercatat.");
            return;
        }

        for (Transaction trx : list) {
            trx.printReceipt();
        }
    }

    private int getUserChoice() {
        System.out.print("Pilih menu: ");
        return getIntInput();
    }

    private int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Harap masukkan angka yang valid!");
            scanner.next();
            System.out.print("Pilihan: ");
        }
        int val = scanner.nextInt();
        scanner.nextLine(); // Bersihkan newline
        return val;
    }

    private double getDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.println("Harap masukkan angka nominal yang valid!");
            scanner.next();
            System.out.print("Nominal: ");
        }
        double val = scanner.nextDouble();
        scanner.nextLine(); // Bersihkan newline
        return val;
    }
}
