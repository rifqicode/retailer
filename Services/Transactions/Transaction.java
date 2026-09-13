package Services.Transactions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private String transactionId;
    private LocalDateTime dateTime;
    private List<TransactionItem> items;
    private double subtotal;
    private double discountRate; // e.g. 0.10 for 10%
    private double discountAmount;
    private double grandTotal;
    private String paymentMethod; // "Tunai" or "Transfer"
    private double amountPaid;
    private double change;

    public Transaction(String transactionId, List<TransactionItem> items, double discountRate,
                       String paymentMethod, double amountPaid) {
        this.transactionId = transactionId;
        this.dateTime = LocalDateTime.now();
        this.items = new ArrayList<>(items);
        this.discountRate = discountRate;
        this.paymentMethod = paymentMethod;
        this.amountPaid = amountPaid;

        calculateTotals();
    }

    private void calculateTotals() {
        this.subtotal = 0;
        for (TransactionItem item : items) {
            this.subtotal += item.getSubtotal();
        }
        this.discountAmount = this.subtotal * this.discountRate;
        this.grandTotal = this.subtotal - this.discountAmount;

        if ("Tunai".equalsIgnoreCase(paymentMethod)) {
            this.change = Math.max(0, this.amountPaid - this.grandTotal);
        } else {
            this.change = 0;
        }
    }

    public String getTransactionId() {
        return transactionId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<TransactionItem> getItems() {
        return items;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public double getChange() {
        return change;
    }

    public void printReceipt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("\n==================================================");
        System.out.println("                 NOTA TRANSAKSI                   ");
        System.out.println("                 RETAILER STORE                   ");
        System.out.println("==================================================");
        System.out.println("ID Transaksi : " + transactionId);
        System.out.println("Waktu        : " + dateTime.format(formatter));
        System.out.println("--------------------------------------------------");
        System.out.printf("%-20s %-5s %-12s %s%n", "Item", "Qty", "Harga", "Subtotal");
        System.out.println("--------------------------------------------------");

        for (TransactionItem item : items) {
            System.out.printf("%-20s %3d   Rp%-10.0f Rp%-10.0f%n",
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getPriceAtPurchase(),
                    item.getSubtotal());
        }

        System.out.println("--------------------------------------------------");
        System.out.printf("Subtotal              : Rp%.0f%n", subtotal);
        if (discountAmount > 0) {
            System.out.printf("Diskon (%.0f%%)          : -Rp%.0f%n", (discountRate * 100), discountAmount);
        } else {
            System.out.println("Diskon                : Rp0");
        }
        System.out.printf("Total Tagihan         : Rp%.0f%n", grandTotal);
        System.out.println("Metode Pembayaran     : " + paymentMethod);
        System.out.printf("Jumlah Bayar          : Rp%.0f%n", amountPaid);
        if ("Tunai".equalsIgnoreCase(paymentMethod)) {
            System.out.printf("Kembalian             : Rp%.0f%n", change);
        }
        System.out.println("==================================================");
        System.out.println("          Terima Kasih Telah Berbelanja!          ");
        System.out.println("==================================================\n");
    }
}
