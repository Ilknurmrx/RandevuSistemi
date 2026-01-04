package tr.com.randevusistemi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.com.randevusistemi.model.*;
import tr.com.randevusistemi.repository.*;

import java.util.List;

@Service
public class RandevuServisi {

    @Autowired
    private TalepDeposu talepDeposu;

    @Autowired
    private MusaitlikDeposu musaitlikDeposu;

    // Öğretmen için müsaitlik oluştur/güncelle
    public Musaitlik musaitlikGuncelle(Long ogretmenId, Musaitlik yeniMusaitlik) {
        Musaitlik musaitlik = musaitlikDeposu.findByOgretmenId(ogretmenId)
                .orElse(new Musaitlik());
        // Eğer yeni ise öğretmen set et
        if (musaitlik.getOgretmen() == null) {
            // Burada kullanıcıyı bulup set etmemiz lazım, servisler arası çağrı yerine
            // repository kullanılabilir veya parametre olarak entity alınabilir.
            // Basitleştirmek için entity'nin doldurulmuş geldiğini varsayalım.
            musaitlik.setOgretmen(yeniMusaitlik.getOgretmen());
        }

        musaitlik.setPazartesi(yeniMusaitlik.isPazartesi());
        musaitlik.setSali(yeniMusaitlik.isSali());
        musaitlik.setCarsamba(yeniMusaitlik.isCarsamba());
        musaitlik.setPersembe(yeniMusaitlik.isPersembe());
        musaitlik.setCuma(yeniMusaitlik.isCuma());
        musaitlik.setCumartesi(yeniMusaitlik.isCumartesi());
        musaitlik.setPazar(yeniMusaitlik.isPazar());

        return musaitlikDeposu.save(musaitlik);
    }

    // Öğrenci randevu talep eder
    public Talep talepOlustur(Kullanici ogrenci, Kullanici ogretmen) {
        Talep talep = new Talep();
        talep.setOgrenci(ogrenci);
        talep.setOgretmen(ogretmen);
        return talepDeposu.save(talep);
    }

    // Öğretmen talebi görür, onaylar (iletişim kuruldu)
    public Talep talepGuncelle(Long talepId, String yeniDurum) {
        Talep talep = talepDeposu.findById(talepId).orElseThrow(() -> new RuntimeException("Talep bulunamadı"));
        talep.setDurum(yeniDurum);
        return talepDeposu.save(talep);
    }

    public List<Talep> ogrenciTalepleri(Long ogrenciId) {
        return talepDeposu.findByOgrenciId(ogrenciId);
    }

    public List<Talep> ogretmenTalepleri(Long ogretmenId) {
        return talepDeposu.findByOgretmenId(ogretmenId);
    }

    public Musaitlik musaitlikGetir(Long ogretmenId) {
        return musaitlikDeposu.findByOgretmenId(ogretmenId).orElse(new Musaitlik());
    }
}
