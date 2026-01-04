package tr.com.randevusistemi.integration;

import org.junit.jupiter.api.Test;
import tr.com.randevusistemi.model.Kullanici;

import static org.junit.jupiter.api.Assertions.*;

public class KullaniciEntegrasyonTest extends BaseIntegrationTest {

    @Test
    public void testKullaniciKayitVeVeritabaniKontrolu() {
        Kullanici kullanici = new Kullanici();
        kullanici.setAd("Entegrasyon");
        kullanici.setSoyad("Testi");
        kullanici.setKullaniciAdi("entegrasyon_user");
        kullanici.setSifre("pass");
        kullanici.setTelefon("5551112233");
        kullanici.setEmail("test@test.com");
        kullanici.setRol(2); // Öğrenci

        // Servis üzerinden kayıt
        Kullanici kaydedilen = kullaniciServisi.kayitOl(kullanici);

        // Veritabanından kontrol
        assertNotNull(kaydedilen.getId());
        assertTrue(kullaniciDeposu.existsByKullaniciAdi("entegrasyon_user"));

        Kullanici dbUser = kullaniciDeposu.findByKullaniciAdi("entegrasyon_user").orElse(null);
        assertNotNull(dbUser);
        assertEquals("Entegrasyon", dbUser.getAd());
    }
}
