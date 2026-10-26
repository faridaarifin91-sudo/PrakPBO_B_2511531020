package pekan5;

public class RekeningVIP extends Rekening {
    private static final double BONUS_VIP = 100000.0;

    public RekeningVIP(String nomor, String nama, double saldoAwal, String pinAwal) {
        super(nomor, nama, saldoAwal + BONUS_VIP, pinAwal);
        
        String idTrx = "TRX-BONUS-" + System.currentTimeMillis();
        riwayatTransaksi.add(new Transaksi(idTrx, "Bonus VIP", BONUS_VIP));

        System.out.println("Selamat! Anda mendapatkan bonus pembukaan akun VIP sebesar Rp" + String.format("%.0f", BONUS_VIP));
        System.out.println("Total saldo awal + bonus: Rp" + String.format("%.0f", saldo));
    }
}