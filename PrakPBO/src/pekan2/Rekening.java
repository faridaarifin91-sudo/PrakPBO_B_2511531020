package pekan2;

import java.util.ArrayList;

public class Rekening {
    String nomorRekening;
    String namaPemilik;
    double saldo;

    // Implementasi Asosiasi (1-to-many)
    ArrayList<Transaksi> riwayatTransaksi;

    // Constructor (Menyesuaikan Modul 1 & Modul 2)
    public Rekening(String nomor, String nama, double saldoAwal) {
        this.nomorRekening = nomor;
        this.namaPemilik = nama;
        this.saldo = saldoAwal;
        // Wajib menginisialisasi ArrayList di dalam constructor agar tidak NullPointerException
        this.riwayatTransaksi = new ArrayList<>();
        System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo Rp" + saldo);
    }

    // Method Getter nomorRekening (dari Modul 2)
    public String getNomorRekening() {
        return nomorRekening;
    }

    // Method Setor Tunai (Perekaman Otomatis Transaksi Kredit)
    public void setorTunai(double nominal) {
        if (nominal > 0) {
            saldo += nominal;
            
            // Merekam riwayat transaksi
            String idTrx = "TRX-S-" + System.currentTimeMillis();
            Transaksi trxBaru = new Transaksi(idTrx, "Kredit", nominal);
            riwayatTransaksi.add(trxBaru);

            System.out.println("Setor tunai Rp" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
        } else {
            System.out.println("Gagal: Nominal setor harus lebih dari 0!");
        }
    }

    // TUGAS 1: Integrasi Rekam Jejak Penarikan (Tarik Tunai - Debit)
    public void tarikTunai(double nominal) {
        if (nominal >= 10000) {
            if (saldo >= nominal) {
                saldo -= nominal;

                // Merekam riwayat transaksi penarikan
                String idTrx = "TRX-T-" + System.currentTimeMillis();
                Transaksi trxBaru = new Transaksi(idTrx, "Debit", nominal);
                riwayatTransaksi.add(trxBaru);

                System.out.println("Tarik tunai Rp" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
            } else {
                System.out.println("Transaksi Gagal: Saldo tidak mencukupi. Saldo Anda: Rp" + saldo);
            }
        } else {
            System.out.println("Transaksi Gagal: Minimal nominal penarikan 10.000");
        }
    }

    // Method Cek Informasi Rekening (Sesuai Modul 1)
    public void cekInformasi() {
        System.out.println("================ INFO REKENING ================");
        System.out.println("No. Rekening : " + nomorRekening);
        System.out.println("Nama Pemilik : " + namaPemilik);
        System.out.println("Saldo Akhir  : Rp" + saldo);
        System.out.println("===============================================");
    }

    // TUGAS 2: Fitur Cetak Mutasi Rekening
    public void cetakMutasi() {
        System.out.println("\n================ MUTASI REKENING ================");
        System.out.println("No. Rekening : " + nomorRekening);
        System.out.println("Nama Pemilik : " + namaPemilik);
        System.out.println("-------------------------------------------------");

        if (riwayatTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi pada rekening ini.");
        } else {
            // Perulangan for-each untuk menelusuri riwayat transaksi
            for (Transaksi t : riwayatTransaksi) {
                t.cetakDetail();
            }
        }
        System.out.println("-------------------------------------------------");
    }
}