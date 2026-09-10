package Pekan1;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main (String [] args) {
		Scanner input = new Scanner (System.in) ;
		ArrayList<Rekening> daftarRekening = new ArrayList<>();
        Rekening akunAktif = null; // Menunjuk ke akun yang sedang terpilih

        boolean isRunning = true;	
			
			System.out.println("=== SISTEM PERBANKAN MINI ===");
			
			while (isRunning) {
				System.out.println("\nMenu Utama:");
				System.out.println("1. Buka Rekening Baru");
				System.out.println("2. Setor Tunai:");
				System.out.println("3. Tarik Tunai");
				System.out.println("4. Cek Informasi Rekening");
				System.out.println("0. Keluar");
				System.out.println("Pilih menu: ");
				
				int pilihan = input.nextInt();
				input.nextLine(); //Membersihkan buffer enter
				
				switch (pilihan) {
					case 1:
						System.out.println("Masukkan No Rekening:");
						String no = input.nextLine();
						System.out.println("Masukkan Nama Pemilik:");
						String nama = input.nextLine();
						System.out.println("Masukkan Saldo Awal:");
						double saldo = input.nextDouble();
						
						//Instansiasi Object / Menjalankan Constructor
						akunAktif = new Rekening(no, nama, saldo);
						break;
						
						case 2:
							if (akunAktif == null) {
								System.out.println("Error: Mohon maaf, Anda belum memiliki noor rekening!");
							}else {
								System.out.println("Masukkan nominal setor: ");
								double setor = input.nextDouble();
								akunAktif.setorTunai(setor); // Memanggil Behavior / method
							}
							break;
							
						case 3:
							if (akunAktif == null) {
		                        System.out.println("Error: Mohon maaf, Anda belum memilih/memiliki rekening!");
		                    } else {
		                        System.out.print("Masukkan nominal tarik tunai: ");
		                        double tarik = input.nextDouble();
		                        akunAktif.tarikTunai(tarik);
		                    }
		                    break;
							
						case 4:
							if (akunAktif == null) {
							System.out.println("Error: Anda belum membuka rekening!");
							} else {
								akunAktif.cekInformasi();
							}
							break;
						case 5:
							if (daftarRekening.isEmpty()) {
		                        System.out.println("Belum ada rekening yang terdaftar di sistem.");
		                    } else {
		                        System.out.print("Masukkan Nomor Rekening yang ingin diaktifkan: ");
		                        String cariNo = input.nextLine();
		                        boolean ditemukan = false;

		                        for (Rekening r : daftarRekening) {
		                            if (r.nomorRekening.equalsIgnoreCase(cariNo)) {
		                                akunAktif = r;
		                                ditemukan = true;
		                                System.out.println("Berhasil mengganti ke akun milik: " + r.namaPemilik);
		                                break;
		                            }
		                        }

		                        if (!ditemukan) {
		                            System.out.println("Error: Nomor rekening tidak ditemukan!");
		                        }
		                    }
		                    break;
							
						case 0:
							isRunning = false;
							System.out.println("Sistem ditutup. Terimakasih!");
							break;
							
							default:
								System.out.println("Pilihan tidak Valid");
							
			}
			}
			input.close();
	}
}
