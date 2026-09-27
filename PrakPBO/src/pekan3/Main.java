package pekan3;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Rekening> daftarRekening = new ArrayList<>();
        Rekening akunAktif = null;
        boolean isRunning = true;

        System.out.println("=== SISTEM PERBANKAN MINI (ENKAPSULASI) ===");

        while (isRunning) {
            System.out.println("\nMenu Utama:");
            System.out.println("1. Buka Rekening Baru");
            System.out.println("2. Setor Tunai");
            System.out.println("3. Tarik Tunai (Wajib PIN)");
            System.out.println("4. Cek Informasi Rekening");
            System.out.println("5. Ganti Akun");
            System.out.println("6. Cetak Mutasi (Wajib PIN)");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");
            int pilihan = input.nextInt();
            input.nextLine(); // Clear buffer

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan No Rekening: ");
                    String no = input.nextLine();
                    System.out.print("Masukkan Nama Pemilik: ");
                    String nama = input.nextLine();
                    System.out.print("Masukkan Saldo Awal: ");
                    double saldo = input.nextDouble();
                    input.nextLine();
                    System.out.print("Masukkan PIN Baru (6 digit): ");
                    String pinBaru = input.nextLine();

                    Rekening akunBaru = new Rekening(no, nama, saldo, pinBaru);
                    daftarRekening.add(akunBaru);
                    akunAktif = akunBaru;
                    break;

                case 2:
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum memilih/memiliki rekening!");
                    } else {
                        System.out.print("Masukkan nominal setor: ");
                        double setor = input.nextDouble();
                        akunAktif.setorTunai(setor);
                    }
                    break;

                case 3:
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum memilih/memiliki rekening!");
                    } else {
                        System.out.print("Masukkan PIN Anda: ");
                        String pinInput = input.nextLine();
                        if (akunAktif.otentikasi(pinInput)) {
                            System.out.print("Masukkan nominal tarik tunai: ");
                            double tarik = input.nextDouble();
                            akunAktif.tarikTunai(tarik);
                        } else {
                            System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
                        }
                    }
                    break;

                case 4:
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum memiliki rekening!");
                    } else {
                        akunAktif.cekInformasi();
                    }
                    break;

                case 5:
                    if (daftarRekening.isEmpty()) {
                        System.out.println("Belum ada rekening yang terdaftar.");
                    } else {
                        System.out.print("Masukkan Nomor Rekening yang ingin diaktifkan: ");
                        String cariNo = input.nextLine();
                        boolean ditemukan = false;
                        for (Rekening r : daftarRekening) {
                            if (r.getNomorRekening().equalsIgnoreCase(cariNo)) {
                                akunAktif = r;
                                ditemukan = true;
                                System.out.println("Berhasil mengganti ke akun milik: " + r.getNamaPemilik());
                                break;
                            }
                        }
                        if (!ditemukan) {
                            System.out.println("Error: Nomor rekening tidak ditemukan!");
                        }
                    }
                    break;

                case 6:
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum memiliki rekening!");
                    } else {
                        System.out.print("Masukkan PIN Anda: ");
                        String pinInput = input.nextLine();
                        if (akunAktif.otentikasi(pinInput)) {
                            akunAktif.cetakMutasi();
                        } else {
                            System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
                        }
                    }
                    break;

                case 0:
                    isRunning = false;
                    System.out.println("Sistem ditutup. Terima kasih!");
                    break;

                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
        input.close();
    }
}