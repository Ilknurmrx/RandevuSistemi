package tr.com.randevusistemi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.com.randevusistemi.model.Degerlendirme;
import tr.com.randevusistemi.model.Talep;
import tr.com.randevusistemi.repository.DegerlendirmeDeposu;
import tr.com.randevusistemi.repository.TalepDeposu;

import java.util.List;

@Service
public class DegerlendirmeServisi {

    @Autowired
    private DegerlendirmeDeposu degerlendirmeDeposu;

    @Autowired
    private TalepDeposu talepDeposu;

    public Degerlendirme yorumYap(Long talepId, int puan, String yorum) {
        Talep talep = talepDeposu.findById(talepId).orElseThrow(() -> new RuntimeException("Talep bulunamadı"));

        if (!"TAMAMLANDI".equals(talep.getDurum())) {
            throw new RuntimeException("Sadece tamamlanmış derslere yorum yapılabilir.");
        }

        Degerlendirme degerlendirme = new Degerlendirme();
        degerlendirme.setTalep(talep);
        degerlendirme.setOgrenci(talep.getOgrenci());
        degerlendirme.setOgretmen(talep.getOgretmen());
        degerlendirme.setPuan(puan);
        degerlendirme.setYorum(yorum);

        return degerlendirmeDeposu.save(degerlendirme);
    }

    public List<Degerlendirme> ogretmenDegerlendirmeleri(Long ogretmenId) {
        return degerlendirmeDeposu.findByOgretmenId(ogretmenId);
    }
}
