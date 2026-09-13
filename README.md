# Retailer Management System

Aplikasi sistem kasir dan manajemen ritel berbasis console sederhana menggunakan bahasa pemrograman **Java**. Proyek ini dirancang memenuhi seluruh pedoman implementasi dan topik evaluasi Object-Oriented Programming (OOP).

---

## Pemetaan Rubrik & Fitur Utama

1. **Lingkungan Java & I/O**
   - Menggunakan `Scanner` untuk pembacaan input konsol dan `System.out.println` / `printf` untuk output berformat.
   - Variabel bertipe dasar (`int`, `double`, `String`) dengan pesan instruksi input yang jelas.

2. **Kelas dan Objek**
   - **`Product`**: Model entitas barang lengkap dengan constructor, encapsulation (getter/setter), dan method `toString()`.
   - **`Transaction` & `TransactionItem`**: Model transaksi penjualan, item belanja, perhitungan diskon, dan pencetakan nota/struk belanja.

3. **Pernyataan Kendali (Seleksi & Perulangan)**
   - Perulangan `while(true)` untuk siklus menu utama dan sub-menu interaktif.
   - Percabangan `switch-case` dan `if-else` untuk pemilihan modul dan logika bisnis.
   - Perulangan `for` / enhanced-for digunakan untuk pencarian produk, penghitungan keranjang, serta agregasi data analitik.

4. **Array dan Koleksi**
   - Menggunakan `ArrayList<Product>` untuk katalog produk dan `ArrayList<Transaction>` untuk riwayat transaksi.
   - Menggunakan operasi koleksi standar seperti `.add()`, `.remove()`, `.isEmpty()`, `.size()`.

5. **Penanganan Eksepsi (Exception Handling)**
   - Blok `try-catch (InputMismatchException e)` pada setiap pembacaan input angka (`int` & `double`) untuk mencegah program crash akibat input non-numerik.
   - **Custom Exceptions**:
     - `ProductNotFoundException`: Dilempar saat produk dengan ID tertentu tidak ditemukan saat pencarian, update, hapus, atau transaksi.
     - `InsufficientStockException`: Dilempar saat stok produk di gudang tidak mencukupi permintaan transaksi.

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
