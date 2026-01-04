package tr.com.randevusistemi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.randevusistemi.model.Sikayet;
import java.util.List;

public interface SikayetDeposu extends JpaRepository<Sikayet, Long> {
    List<Sikayet> findByIncelendiFalse();
}
