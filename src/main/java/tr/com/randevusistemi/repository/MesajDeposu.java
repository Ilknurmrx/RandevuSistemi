package tr.com.randevusistemi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.randevusistemi.model.Mesaj;
import java.util.List;

public interface MesajDeposu extends JpaRepository<Mesaj, Long> {
    List<Mesaj> findByAliciId(Long aliciId);

    List<Mesaj> findByGonderenId(Long gonderenId);

    // İki kişi arasındaki konuşmayı getirmek için
    // (Giden ve gelen mesajları tarih sırasına göre)
    // Spring Data JPA metod isimlendirmesiyle complex query
    // Ancak basitlik için controller veya service katmanında birleştirebiliriz.
}
