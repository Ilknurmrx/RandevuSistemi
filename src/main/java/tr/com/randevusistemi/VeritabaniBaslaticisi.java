package tr.com.randevusistemi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tr.com.randevusistemi.model.Kullanici;
import tr.com.randevusistemi.repository.KullaniciDeposu;

import java.util.Optional;

@Component
public class VeritabaniBaslaticisi implements CommandLineRunner {

    @Autowired
    private KullaniciDeposu kullaniciDeposu;

    @Override
    public void run(String... args) throws Exception {
        // Spring Session kendi tablolarını otomatik oluşturacak
        // Manuel DROP/CREATE yapmıyoruz çünkü her restart'ta session'ları silmiş
        // oluruz!

        Optional<Kullanici> adminOpt = kullaniciDeposu.findByKullaniciAdi("admin");
        if (adminOpt.isEmpty()) {
            Kullanici admin = new Kullanici();
            admin.setAd("Admin");
            admin.setSoyad("Yonetici");
            admin.setKullaniciAdi("admin");
            admin.setSifre("12345");
            admin.setTelefon("5555555555");
            admin.setEmail("admin@randevu.com"); // Varsayılan email
            admin.setRol(0); // 0: Admin
            kullaniciDeposu.save(admin);
            System.out.println("Admin kullanicisi olusturuldu: admin / 12345");
        } else {
            // Admin varsa şifresini 12345 olarak güncelle (Giriş sorununu çözmek için)
            Kullanici admin = adminOpt.get();
            admin.setSifre("12345");
            kullaniciDeposu.save(admin);
            System.out.println("Admin kullanicisi guncellendi: admin / 12345");
        }
    }
}
