package tr.com.randevusistemi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.com.randevusistemi.model.OgretmenDetay;
import tr.com.randevusistemi.model.Sikayet;
import tr.com.randevusistemi.repository.OgretmenDetayDeposu;
import tr.com.randevusistemi.repository.SikayetDeposu;

import java.util.List;

@Service
public class AdminServisi {

    @Autowired
    private OgretmenDetayDeposu ogretmenDetayDeposu;

    @Autowired
    private SikayetDeposu sikayetDeposu;

    // Öğretmen onayı (Mavi tik)
    public void ogretmenOnayla(Long ogretmenDetayId, boolean onay) {
        OgretmenDetay detay = ogretmenDetayDeposu.findById(ogretmenDetayId)
                .orElseThrow(() -> new RuntimeException("Öğretmen detayı bulunamadı"));
        detay.setOnayli(onay);
        ogretmenDetayDeposu.save(detay);
    }

    // Şikayetleri listele
    public List<Sikayet> bekleyenSikayetler() {
        return sikayetDeposu.findByIncelendiFalse();
    }

    // Şikayet incelemesi tamamla
    public void sikayetIncele(Long sikayetId) {
        Sikayet sikayet = sikayetDeposu.findById(sikayetId)
                .orElseThrow(() -> new RuntimeException("Şikayet bulunamadı"));
        sikayet.setIncelendi(true);
        sikayetDeposu.save(sikayet);
    }

    @Autowired
    private tr.com.randevusistemi.repository.KullaniciDeposu kullaniciDeposu;

    // Kullanıcı sil (Ban)
    public void kullaniciSil(Long kullaniciId) {
        // İlişkili kayıtların silinmesi veritabanı Cascade ayarlarıyla veya manuel
        // yapılmalı.
        // Basitlik için sadece kullanıcıyı ve varsa detayını siliyoruz.
        // Spring Data JPA'da orphanRemoval=true veya cascade=ALL varsa otomatik siler.
        // Bizim modelde cascade muhtemelen yok, o yüzden detay varsa silelim.

        ogretmenDetayDeposu.findByKullaniciId(kullaniciId).ifPresent(detay -> {
            ogretmenDetayDeposu.delete(detay);
        });

        kullaniciDeposu.deleteById(kullaniciId);
    }
}
