package shop.app;

import java.util.List;
import shop.StudentInfo;
import shop.model.DigitalProduct;
import shop.model.PhysicalProduct;
import shop.model.Product;
import shop.notification.CustomerContact;
import shop.notification.EmailChannel;
import shop.notification.NotificationChannel;
import shop.notification.NotificationService;
import shop.notification.SmsChannel;
import shop.notification.WhatsAppChannel;
import shop.payment.BankTransferPayment;
import shop.payment.CreditCardPayment;
import shop.payment.EWalletPayment;
import shop.payment.PaymentMethod;
import shop.service.CheckoutService;
import shop.shipping.ExpressShipping;
import shop.shipping.ShippingService;

public class TugasApp {
    public static void main(String[] args) {
        System.out.println("Tugas oleh " + StudentInfo.NAME + " (" + StudentInfo.NIM + ")");

        Product coffee = new PhysicalProduct("KOPI-001", "Kopi Arabika Gayo 250g", 85_000, 300);
        Product rendang = new PhysicalProduct("RDG-002", "Rendang Kemasan 250g", 120_000, 400);
        Product ebook = new DigitalProduct("EBK-003", "E-book Resep Kue Nusantara", 50_000, "https://toko-umkm.example/unduh/ebk-003");
        List<Product> cart = List.of(coffee, rendang, ebook);

        CheckoutService checkout = new CheckoutService();
        ShippingService express = new ExpressShipping();

        PaymentMethod wallet = new EWalletPayment("DompetKu", 500_000);
        PaymentMethod transfer = new BankTransferPayment("Bank Nagari");
        CreditCardPayment card = new CreditCardPayment("Siti Rahma", 1_000_000);

        System.out.println("\n== T1: Total dengan biaya admin (Ekspres) ==");
        System.out.println("E-Wallet DompetKu         : Rp" + checkout.grandTotal(cart, express, wallet) + " (admin Rp" + wallet.adminFee(checkout.total(cart, express)) + ")");
        System.out.println("Transfer Bank Nagari      : Rp" + checkout.grandTotal(cart, express, transfer) + " (admin Rp" + transfer.adminFee(checkout.total(cart, express)) + ")");
        System.out.println("Kartu Kredit (Siti Rahma) : Rp" + checkout.grandTotal(cart, express, card) + " (admin Rp" + card.adminFee(checkout.total(cart, express)) + ")");

        System.out.println("Kebijakan kartu kredit   : " + card.policy());
        System.out.println("Kebijakan e-wallet       : " + ((EWalletPayment) wallet).policy());

        System.out.println("\n== T2: Notifikasi status pesanan ==");
        NotificationService service = new NotificationService();
        service.register(new EmailChannel());
        service.register(new SmsChannel(StudentInfo.SMS_MAX_LENGTH));
        service.register(new WhatsAppChannel());
        service.register(new NotificationChannel() {
            @Override
            public String channelName() {
                return "LOG";
            }
            @Override
            public boolean send(CustomerContact contact, String message) {
                System.out.println("[LOG] " + contact.getName() + ": " + message);
                return true;
            }
        });

        CustomerContact siti = new CustomerContact("Siti Rahma", "siti@example.com", "081234567890");
        CustomerContact budi = new CustomerContact("Budi", "", "+6281298765432");

        String longMsg = "Pesanan ORD-0001 sudah dikirim dengan kurir Ekspres. Nomor resi akan muncul di aplikasi dalam 1x24 jam. Terima kasih telah berbelanja produk UMKM Sumatera Barat di toko kami!";

        int sentSiti = service.broadcast(siti, longMsg);
        System.out.println("Terkirim ke Siti lewat " + sentSiti + " kanal\n");

        int sentBudi = service.broadcast(budi, longMsg);
        System.out.println("Terkirim ke Budi lewat " + sentBudi + " kanal");
    }
}