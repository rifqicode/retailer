# Retailer Management System

Aplikasi sistem kasir dan manajemen ritel berbasis console sederhana menggunakan bahasa pemrograman **Java**.

---

## Fitur Utama

1. **Manajemen Produk (CRUD)**
   - Tambah produk baru, ubah data produk, hapus produk, dan cari produk berdasarkan nama.
   - Manajemen stok produk secara dinamis.

2. **Pemrosesan Transaksi (Kasir)**
   - Keranjang belanja multi-item.
   - Perhitungan diskon otomatis:
     - Belanja $\ge$ Rp500.000 $\rightarrow$ Diskon 5%
     - Belanja $\ge$ Rp1.000.000 $\rightarrow$ Diskon 10%
   - Metode pembayaran: **Tunai** (validasi uang dan hitung kembalian) & **Transfer Bank**.
   - Otomatis mengurangi stok produk saat transaksi berhasil.
   - Cetak struk/nota belanja berformat rapi di konsol.

3. **Laporan & Analitik**
   - Ringkasan penjualan harian (total transaksi, unit terjual, total omset).
   - Deteksi produk dengan stok menipis (stok < 10 unit).
   - Top 3 produk terlaris berdasarkan kuantitas penjualan.

---

## Struktur Folder

```text
retailer/
├── Main.java                        # Entry point & router menu utama
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

1. **Kompilasi kode:**
   ```bash
   javac Main.java Services/*/*.java
   ```

2. **Jalankan aplikasi:**
   ```bash
   java Main
   ```
