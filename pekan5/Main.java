package pekan5;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Rekening> daftarRekening = new ArrayList<>();
        Rekening akunAktif = null;
        boolean isRunning = true;

        System.out.println("=== SISTEM PERBANKAN MINI  ===");

        while (isRunning) {
            System.out.println("\nMenu Utama:");
            System.out.println("1. Buka Rekening Baru ");
            System.out.println("2. Setor Tunai");
            System.out.println("3. Tarik Tunai ");
            System.out.println("4. Cek Informasi Rekening");
            System.out.println("5. Ganti Akun");
            System.out.println("6. Cetak Mutasi ");
            System.out.println("7. Simulasi Akhir Bulan ");
            System.out.println("8. Transfer ke Rekening Lain ");
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

                    System.out.println("Pilih Produk Rekening:");
                    System.out.println("1. Tabungan Umum (Bunga Bulanan)");
                    System.out.println("2. Giro Bisnis (Fasilitas Overdraft)");
                    System.out.println("3. Rekening VIP (Bonus Saldo Rp100.000)");
                    System.out.print("Pilihan Produk (1/2/3): ");
                    int jenisProduk = input.nextInt();
                    input.nextLine();

                    Rekening akunBaru = null;
                    if (jenisProduk == 1) {
                        System.out.print("Masukkan Suku Bunga Bulanan (%): ");
                        double bunga = input.nextDouble();
                        input.nextLine();
                        akunBaru = new RekeningTabungan(no, nama, saldo, pinBaru, bunga);
                    } else if (jenisProduk == 2) {
                        System.out.print("Masukkan Batas Overdraft (Limit Pinjaman): ");
                        double limit = input.nextDouble();
                        input.nextLine();
                        akunBaru = new RekeningGiro(no, nama, saldo, pinBaru, limit);
                    } else if (jenisProduk == 3) {
                        akunBaru = new RekeningVIP(no, nama, saldo, pinBaru);
                    } else {
                        System.out.println("Produk tidak valid! Menggunakan standar Rekening.");
                        akunBaru = new Rekening(no, nama, saldo, pinBaru);
                    }

                    // Upcasting: Menyimpan berbagai subclass ke koleksi Superclass
                    daftarRekening.add(akunBaru);
                    akunAktif = akunBaru;
                    System.out.println("Rekening berhasil dibuka dan diaktifkan!");
                    break;

                case 2:
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum memilih/memiliki rekening!");
                    } else {
                        System.out.print("Masukkan nominal setor: ");
                        double setor = input.nextDouble();
                        input.nextLine();
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
                            input.nextLine();
                            akunAktif.tarikTunai(tarik); // Dynamic Dispatch aktif otomatis jika Giro
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

                case 7:
                    // Simulasi Akhir Bulan (Downcasting dengan instanceof)
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum memiliki rekening!");
                    } else {
                        if (akunAktif instanceof RekeningTabungan) {
                            RekeningTabungan tabungan = (RekeningTabungan) akunAktif;
                            tabungan.tambahBungaAkhirBulan();
                        } else {
                            System.out.println("Gagal: Fitur bunga akhir bulan hanya berlaku untuk Rekening Tabungan.");
                        }
                    }
                    break;

                case 8:
                    // Fitur Transfer Antar Rekening (Modul 5)
                    if (akunAktif == null) {
                        System.out.println("Error: Anda belum memilih/memiliki rekening aktif!");
                    } else {
                        System.out.print("Masukkan Nomor Rekening Tujuan: ");
                        String noTujuan = input.nextLine();

                        Rekening akunTujuan = null;
                        for (Rekening r : daftarRekening) {
                            if (r.getNomorRekening().equalsIgnoreCase(noTujuan)) {
                                akunTujuan = r;
                                break;
                            }
                        }

                        if (akunTujuan == null) {
                            System.out.println("Error: Nomor rekening tujuan tidak ditemukan dalam sistem!");
                        } else if (akunTujuan.getNomorRekening().equalsIgnoreCase(akunAktif.getNomorRekening())) {
                            System.out.println("Error: Tidak dapat melakukan transfer ke rekening sendiri!");
                        } else {
                            System.out.print("Masukkan nominal transfer: ");
                            double nominalTransfer = input.nextDouble();
                            input.nextLine();

                            System.out.print("Masukkan PIN Anda untuk konfirmasi: ");
                            String pinKonfirmasi = input.nextLine();

                            // Memanggil method transfer polimorfik
                            akunAktif.transfer(akunTujuan, nominalTransfer, pinKonfirmasi);
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