package tr.com.randevusistemi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.com.randevusistemi.model.Kullanici;
import tr.com.randevusistemi.repository.KullaniciDeposu;

import java.util.Optional;

@Service
public class KullaniciServisi {

    @Autowired
    private KullaniciDeposu kullaniciDeposu;

    public Kullanici kayitOl(Kullanici kullanici) {
        if (kullaniciDeposu.existsByKullaniciAdi(kullanici.getKullaniciAdi())) {
            throw new RuntimeException("Kullanıcı adı zaten kullanımda!");
        }
        return kullaniciDeposu.save(kullanici);
    }

    public Kullanici girisYap(String kullaniciAdi, String sifre) {
        Optional<Kullanici> kullanici = kullaniciDeposu.findByKullaniciAdi(kullaniciAdi);
        if (kullanici.isPresent() && kullanici.get().getSifre().equals(sifre)) {
            return kullanici.get();
        }
        return null;
    }

    public Kullanici kullaniciBul(Long id) {
        return kullaniciDeposu.findById(id).orElse(null);
    }

    public Kullanici kullaniciBulByKullaniciAdi(String kullaniciAdi) {
        return kullaniciDeposu.findByKullaniciAdi(kullaniciAdi).orElse(null);
    }
}
