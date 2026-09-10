package Pekan1;

public class Rekening {
	String nomorRekening;
	String namaPemilik;
	double saldo;
	
	public Rekening(String nomor, String nama, double saldoAwal) {
		nomorRekening = nomor;
		namaPemilik = nama;
		saldo = saldoAwal;
		System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo RP" + saldo);
	}
	public void setorTunai(double nominal) {
		if(nominal > 0) {
			saldo += nominal;
			System.out.println("Setor tunai RP" + nominal + "berhasil. Saldo saat ini8 :: Rp" + saldo);
		}else {
			System.out.println("Gagal: nominal setor harus dari 0!");
		}
	}
	
	public void tarikTunai(double nominal) {
        if (nominal < 10000) {
            System.out.println("Transaksi Gagal: Minimal nominal penarikan 10.000");
        } else if (nominal > saldo) {
            System.out.println("Transaksi Gagal: Saldo tidak mencukupi. Saldo Anda: Rp" + saldo);
        } else {
            saldo -= nominal;
            System.out.println("Tarik tunai Rp" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
        }
    }
	public void cekInformasi() {
		System.out.println("--- INFO REKENING ---");
		System.out.println("No. Rekening : " +nomorRekening);
		System.out.println("Nama pemilik : " + namaPemilik);
		System.out.println("Saldo Akhir : Rp " + saldo);
		System.out.println("---------------------");
	}
	
	
	
}
