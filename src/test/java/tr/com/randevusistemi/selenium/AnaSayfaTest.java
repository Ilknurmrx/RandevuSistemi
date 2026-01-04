package tr.com.randevusistemi.selenium;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnaSayfaTest extends BaseSeleniumTest {

    @Test
    public void testAnaSayfaYonlendirmesi() {
        driver.get(baseUrl + "/");
        assertTrue(driver.getCurrentUrl().contains("/giris"), "Ana sayfa girişe yönlendirmeli");
    }
}
