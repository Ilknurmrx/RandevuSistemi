package tr.com.randevusistemi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.com.randevusistemi.model.Mesaj;
import tr.com.randevusistemi.repository.MesajDeposu;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MesajServisi {

    @Autowired
    private MesajDeposu mesajDeposu;

    public void mesajGonder(Mesaj mesaj) {
        mesajDeposu.save(mesaj);
    }

    public List<Mesaj> gelenKutusu(Long kullaniciId) {
        return mesajDeposu.findByAliciId(kullaniciId);
    }

    public List<Mesaj> gidenKutusu(Long kullaniciId) {
        return mesajDeposu.findByGonderenId(kullaniciId);
    }

    /**
     * İki kullanıcı arasındaki tüm geçmişi (giden/gelen) tarih sırasıyla getirir.
     */
    public List<Mesaj> sohbetGecmisi(Long kullanici1Id, Long kullanici2Id) {
        List<Mesaj> gidenler = mesajDeposu.findByGonderenId(kullanici1Id).stream()
                .filter(m -> m.getAlici().getId().equals(kullanici2Id))
                .collect(Collectors.toList());

        List<Mesaj> gelenler = mesajDeposu.findByAliciId(kullanici1Id).stream()
                .filter(m -> m.getGonderen().getId().equals(kullanici2Id))
                .collect(Collectors.toList());

        List<Mesaj> tumSohbet = new ArrayList<>();
        tumSohbet.addAll(gidenler);
        tumSohbet.addAll(gelenler);

        tumSohbet.sort(Comparator.comparing(Mesaj::getTarih));

        return tumSohbet;
    }
}
