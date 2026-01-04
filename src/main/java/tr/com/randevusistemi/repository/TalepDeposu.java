package tr.com.randevusistemi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.randevusistemi.model.Talep;
import java.util.List;

public interface TalepDeposu extends JpaRepository<Talep, Long> {
    List<Talep> findByOgrenciId(Long ogrenciId);

    List<Talep> findByOgretmenId(Long ogretmenId);
}
