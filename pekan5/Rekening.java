package pekan5;

import java.util.ArrayList;

public class Rekening {
    private String nomorRekening;
    private String namaPemilik;
    private String pin;
    
    protected double saldo;
    protected ArrayList<Transaksi> riwayatTransaksi;

    public Rekening(String nomor, String nama, double saldoAwal, String pinAwal) {
        this.nomorRekening = nomor;
        this.namaPemilik = nama;
        this.saldo = saldoAwal;
        
        if (pinAwal.length() == 6) {
            this.pin = pinAwal;
        } else {
            System.out.println("Peringatan: PIN harus 6 digit! Menggunakan PIN default 123456");
            this.pin = "123456";
        }
        
        this.riwayatTransaksi = new ArrayList<>();
        String idTrx = "TRX-INIT-" + System.currentTimeMillis();
        riwayatTransaksi.add(new Transaksi(idTrx, "Setor Awal", saldoAwal));
    }

    public boolean otentikasi(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public void setorTunai(double nominal) {
        if (nominal <= 0) {
            System.out.println("Gagal: Nominal setor harus lebih dari 0!");
            return;
        }
        saldo += nominal;
        String idTrx = "TRX-S-" + System.currentTimeMillis();
        riwayatTransaksi.add(new Transaksi(idTrx, "Kredit", nominal));
        System.out.println("Setor tunai Rp" + String.format("%.0f", nominal) + " berhasil. Saldo saat ini: Rp" + String.format("%.0f", saldo));
    }

    public boolean tarikTunai(double nominal) {
        if (nominal <= 0) {
            System.out.println("Gagal: Nominal tarik harus lebih dari 0!");
            return false;
        } else if (nominal < 10000) {
            System.out.println("Transaksi Gagal: Minimal nominal penarikan adalah Rp10.000");
            return false;
        } else if (nominal > saldo) {
            System.out.println("Transaksi Gagal: Saldo tidak mencukupi. Saldo Anda: Rp" + String.format("%.0f", saldo));
            return false;
        } else {
            saldo -= nominal;
            String idTrx = "TRX-T-" + System.currentTimeMillis();
            riwayatTransaksi.add(new Transaksi(idTrx, "Debit", nominal));
            System.out.println("Tarik tunai Rp" + String.format("%.0f", nominal) + " berhasil. Sisa saldo: Rp" + String.format("%.0f", saldo));
            return true;
        }
    }

    // Tugas Modul 5: Method Transfer Polimorfik
    public void transfer(Rekening tujuan, double nominal, String pinInput) {
        if (!otentikasi(pinInput)) {
            System.out.println("Transfer Gagal: PIN yang Anda masukkan salah!");
            return;
        }
        if (tujuan == null) {
            System.out.println("Transfer Gagal: Rekening tujuan tidak ditemukan!");
            return;
        }

        // Dynamic Dispatch: Memanggil tarikTunai (bisa milik Rekening biasa atau RekeningGiro)
        boolean statusTarik = this.tarikTunai(nominal);

        if (statusTarik) {
            tujuan.setorTunai(nominal);
            System.out.println("Transfer sebesar Rp" + String.format("%.0f", nominal) + " ke rekening " + tujuan.getNomorRekening() + " berhasil.");
        } else {
            System.out.println("Transfer Gagal: Proses penarikan dana tidak dapat diproses.");
        }
    }

    public void cekInformasi() {
        System.out.println("\n--- INFORMASI REKENING ---");
        System.out.println("No Rekening   : " + nomorRekening);
        System.out.println("Nama Pemilik  : " + namaPemilik);
        System.out.println("Saldo Terkini : Rp" + String.format("%.0f", saldo));
    }

    public void cetakMutasi() {
        System.out.println("\n--- RIWAYAT MUTASI REKENING (" + nomorRekening + ") ---");
        if (riwayatTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi.");
        } else {
            for (Transaksi t : riwayatTransaksi) {
                t.cetakDetail();
            }
        }
        System.out.println("--------------------------------------------");
    }

    public String getNomorRekening() { return nomorRekening; }
    public String getNamaPemilik() { return namaPemilik; }
    public double getSaldo() { return saldo; }
}