# Retailer Management System

Aplikasi sistem kasir dan manajemen ritel berbasis console sederhana menggunakan bahasa pemrograman **Java**. Proyek ini dirancang memenuhi seluruh pedoman implementasi dan topik evaluasi Object-Oriented Programming (OOP).

---

## Struktur Folder

```text
retailer/
├── Main.java                        # Entry point & router menu utama
├── Exceptions/                      # Custom Exceptions
│   ├── ProductNotFoundException.java
│   └── InsufficientStockException.java
├── Services/
│   ├── Products/                    # Modul Produk
│   │   ├── Product.java
│   │   ├── ProductService.java
│   │   └── ProductMenu.java
│   ├── Transactions/                # Modul Transaksi & Kasir
│   │   ├── TransactionItem.java
│   │   ├── Transaction.java
│   │   ├── TransactionService.java
│   │   └── TransactionMenu.java
│   └── Reports/                     # Modul Laporan & Analitik
│       ├── ReportService.java
│       └── ReportMenu.java
├── .gitignore
└── README.md
```

---

## Cara Menjalankan

1. **Kompilasi semua file:**
   ```bash
   javac Exceptions/*.java Services/*/*.java Main.java
   ```

2. **Jalankan aplikasi:**
   ```bash
   java Main
   ```
