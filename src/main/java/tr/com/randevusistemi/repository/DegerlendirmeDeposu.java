package tr.com.randevusistemi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.randevusistemi.model.Degerlendirme;
import java.util.List;

public interface DegerlendirmeDeposu extends JpaRepository<Degerlendirme, Long> {
    List<Degerlendirme> findByOgretmenId(Long ogretmenId);
}
