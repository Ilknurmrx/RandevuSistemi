package tr.com.randevusistemi.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import tr.com.randevusistemi.repository.KullaniciDeposu;
import tr.com.randevusistemi.repository.TalepDeposu;
import tr.com.randevusistemi.service.KullaniciServisi;
import tr.com.randevusistemi.service.RandevuServisi;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public abstract class BaseIntegrationTest {

    @Autowired
    protected KullaniciServisi kullaniciServisi;

    @Autowired
    protected RandevuServisi randevuServisi;

    @Autowired
    protected KullaniciDeposu kullaniciDeposu;

    @Autowired
    protected TalepDeposu talepDeposu;

    @Test
    public void contextLoads() {
        // Context doğrulama
    }
}
