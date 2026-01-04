package tr.com.randevusistemi.integration;

import org.junit.jupiter.api.Test;
import tr.com.randevusistemi.model.Kullanici;
import tr.com.randevusistemi.model.Talep;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RandevuIslemleriEntegrasyonTest extends BaseIntegrationTest {

    @Test
    public void testRandevuTalebiAkisi() {
        // 1. Öğrenci Oluştur
        Kullanici ogrenci = new Kullanici();
        ogrenci.setAd("Ogrenci");
        ogrenci.setSoyad("Can");
        ogrenci.setKullaniciAdi("ogr_integ");
        ogrenci.setSifre("123");
        ogrenci.setTelefon("5554443322");
        ogrenci.setEmail("ogrenci@test.com");
        ogrenci.setRol(2);
        kullaniciServisi.kayitOl(ogrenci);

        // 2. Öğretmen Oluştur
        Kullanici ogretmen = new Kullanici();
        ogretmen.setAd("Ogretmen");
        ogretmen.setSoyad("Hoca");
        ogretmen.setKullaniciAdi("hoca_integ");
        ogretmen.setSifre("123");
        ogretmen.setTelefon("5551112233");
        ogretmen.setEmail("ogretmen@test.com");
        ogretmen.setRol(1);
        kullaniciServisi.kayitOl(ogretmen);

        // 3. Talep Oluştur
        Talep talep = randevuServisi.talepOlustur(ogrenci, ogretmen);
        assertNotNull(talep.getId());
        assertEquals("BEKLIYOR", talep.getDurum());

        // 4. Öğretmenin talebi görmesi
        List<Talep> ogretmenTalepleri = randevuServisi.ogretmenTalepleri(ogretmen.getId());
        assertEquals(1, ogretmenTalepleri.size());
        assertEquals(ogrenci.getId(), ogretmenTalepleri.get(0).getOgrenci().getId());
    }

    @Test
    public void testTalepOnaylamaEntegrasyonu() {
        // Veri Hazırlığı
        Kullanici ogrenci = new Kullanici();
        ogrenci.setAd("Ali");
        ogrenci.setSoyad("Veli");
        ogrenci.setKullaniciAdi("ali_veli");
        ogrenci.setSifre("123");
        ogrenci.setTelefon("5556667788");
        ogrenci.setEmail("ali@test.com");
        ogrenci.setRol(2);
        kullaniciServisi.kayitOl(ogrenci);

        Kullanici ogretmen = new Kullanici();
        ogretmen.setAd("Ayse");
        ogretmen.setSoyad("Yilmaz");
        ogretmen.setKullaniciAdi("ayse_hoca");
        ogretmen.setSifre("123");
        ogretmen.setTelefon("5559998877");
        ogretmen.setEmail("ayse@test.com");
        ogretmen.setRol(1);
        kullaniciServisi.kayitOl(ogretmen);

        Talep talep = randevuServisi.talepOlustur(ogrenci, ogretmen);

        // İşlem: Öğretmen talebi onaylar
        Talep guncelTalep = randevuServisi.talepGuncelle(talep.getId(), "ONAYLANDI");

        // Kontrol
        assertEquals("ONAYLANDI", guncelTalep.getDurum());

        // DB kontrolü
        Talep dbTalep = talepDeposu.findById(talep.getId()).orElse(null);
        assertNotNull(dbTalep);
        assertEquals("ONAYLANDI", dbTalep.getDurum());
    }
}
