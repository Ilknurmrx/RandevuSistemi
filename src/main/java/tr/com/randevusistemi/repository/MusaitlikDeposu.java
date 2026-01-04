package tr.com.randevusistemi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.randevusistemi.model.Musaitlik;
import java.util.Optional;

public interface MusaitlikDeposu extends JpaRepository<Musaitlik, Long> {
    Optional<Musaitlik> findByOgretmenId(Long ogretmenId);
}
