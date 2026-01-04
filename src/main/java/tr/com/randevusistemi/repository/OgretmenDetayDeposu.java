package tr.com.randevusistemi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.randevusistemi.model.OgretmenDetay;
import java.util.Optional;

public interface OgretmenDetayDeposu extends JpaRepository<OgretmenDetay, Long> {
    Optional<OgretmenDetay> findByKullaniciId(Long kullaniciId);
}
