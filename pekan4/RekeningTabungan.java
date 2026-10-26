package pekan4;

public class RekeningTabungan extends Rekening {
    // Atribut spesifik produk tabungan
    private double sukuBunga;

    // Constructor Subclass
    public RekeningTabungan(String nomor, String nama, double saldoAwal, String pinAwal, double sukuBunga) {
        // super() memanggil konstruktor superclass (WAJIB pada baris pertama)
        super(nomor, nama, saldoAwal, pinAwal);
        this.sukuBunga = sukuBunga;
    }


    public void tambahBungaAkhirBulan() {
    	//menghitung bunga
    	//menghitung bisa mengakses saldo secara lansung dari class rekening
        double nominalBunga = saldo * (sukuBunga / 100);
        saldo += nominalBunga; // Mengakses atribut protected 'saldo' secara langsung

        // Mencatat transaksi 
        String idTrx = "TRX-B-" + System.currentTimeMillis();
        riwayatTransaksi.add(new Transaksi(idTrx, "Bunga", nominalBunga));

        System.out.println("Bunga " + sukuBunga + "% berhasil ditambahkan: Rp" + nominalBunga);
        System.out.println("Saldo saat ini: Rp" + saldo);
    }
}