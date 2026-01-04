package tr.com.randevusistemi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.com.randevusistemi.model.Kullanici;
import tr.com.randevusistemi.model.OgretmenDetay;
import tr.com.randevusistemi.repository.OgretmenDetayDeposu;

@Service
public class OgretmenServisi {

    @Autowired
    private OgretmenDetayDeposu ogretmenDetayDeposu;

    public OgretmenDetay detayGetir(Long kullaniciId) {
        return ogretmenDetayDeposu.findByKullaniciId(kullaniciId).orElse(null);
    }

    public OgretmenDetay detayGuncelle(Kullanici kullanici, OgretmenDetay yeniDetay) {
        OgretmenDetay detay = ogretmenDetayDeposu.findByKullaniciId(kullanici.getId())
                .orElse(new OgretmenDetay());

        if (detay.getKullanici() == null) {
            detay.setKullanici(kullanici);
        }

        detay.setBrans(yeniDetay.getBrans());
        detay.setHakkinda(yeniDetay.getHakkinda());
        detay.setProfilFotografiUrl(yeniDetay.getProfilFotografiUrl());
        detay.setBelgeUrl(yeniDetay.getBelgeUrl());

        // Önemli: Profil güncellendiği anda onay sıfırlanır (Admin tekrar bakmalı)
        detay.setOnayli(false);

        return ogretmenDetayDeposu.save(detay);
    }

    public void belgeYukle(Long kullaniciId, String belgeUrl) {
        OgretmenDetay detay = ogretmenDetayDeposu.findByKullaniciId(kullaniciId)
                .orElseThrow(() -> new RuntimeException("Profil bulunamadı"));
        detay.setBelgeUrl(belgeUrl);
        ogretmenDetayDeposu.save(detay);
    }
}
