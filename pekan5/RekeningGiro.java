package pekan5;

public class RekeningGiro extends Rekening {
    private double batasOverdraft;

    public RekeningGiro(String nomor, String nama, double saldoAwal, String pinAwal, double batasOverdraft) {
        super(nomor, nama, saldoAwal, pinAwal);
        this.batasOverdraft = batasOverdraft;
    }

    // Implementasi Polimorfisme: Method Overriding tarikTunai
    @Override
    public boolean tarikTunai(double nominal) {
        if (nominal <= 0) {
            System.out.println("Gagal: Nominal tarik Giro harus lebih dari 0!");
            return false;
        } else if (nominal > (saldo + batasOverdraft)) {
            System.out.println("Transaksi Gagal: Melebihi batas overdraft! Maksimal penarikan: Rp" + String.format("%.0f", (saldo + batasOverdraft)));
            return false;
        } else {
            saldo -= nominal; 
            String idTrx = "TRX-G-" + System.currentTimeMillis();
            riwayatTransaksi.add(new Transaksi(idTrx, "Debit Giro", nominal));
            System.out.println("Tarik tunai Giro Rp" + String.format("%.0f", nominal) + " berhasil. Saldo saat ini: Rp" + String.format("%.0f", saldo));
            return true;
        }
    }

    public double getBatasOverdraft() { return batasOverdraft; }
}