package tr.com.randevusistemi.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OgrenciKayitTest extends BaseSeleniumTest {

    @Test
    public void testOgrenciKaydiBasarili() {
        driver.get(baseUrl + "/kayit");

        driver.findElement(By.name("ad")).sendKeys("Selenium");
        driver.findElement(By.name("soyad")).sendKeys("Ogrenci");
        driver.findElement(By.name("kullaniciAdi")).sendKeys("ogrenci" + System.currentTimeMillis());
        driver.findElement(By.name("sifre")).sendKeys("1234");
        driver.findElement(By.name("telefon")).sendKeys("5551112233");
        driver.findElement(By.name("email")).sendKeys("test_ogrenci@test.com");
        // Varsayılan rol zaten öğrenci
        driver.findElement(By.tagName("button")).click();

        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("/giris"));

        assertTrue(driver.getCurrentUrl().contains("/giris"), "Kayıt sonrası girişe yönlendirmeli");
    }
}
