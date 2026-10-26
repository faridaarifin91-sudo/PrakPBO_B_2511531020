package pekan4;

public class RekeningVIP extends Rekening {
    // Bonus otomatis untuk nasabah VIP
    private static final double BONUS_VIP = 100000.0;

    // Constructor Subclass RekeningVIP
    public RekeningVIP(String nomor, String nama, double saldoAwal, String pinAwal) {
        // Memanggil konstruktor superclass dengan menambahkan bonus ke saldo awal
        super(nomor, nama, saldoAwal + BONUS_VIP, pinAwal);
        
        // Mencatat transaksi bonus otomatis ke riwayatTransaksi (protected)
        String idTrx = "TRX-BONUS-" + System.currentTimeMillis();
        riwayatTransaksi.add(new Transaksi(idTrx, "Bonus VIP", BONUS_VIP));
        
        System.out.println("Selamat! Anda mendapatkan bonus pembukaan akun VIP sebesar Rp" + BONUS_VIP);
        System.out.println("Total saldo awal + bonus: Rp" + saldo);
    }
}
