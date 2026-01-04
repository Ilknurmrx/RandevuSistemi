package tr.com.randevusistemi.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OgretmenKayitTest extends BaseSeleniumTest {

    @Test
    public void testOgretmenKaydiBasarili() {
        driver.get(baseUrl + "/kayit");

        driver.findElement(By.name("ad")).sendKeys("Selenium");
        driver.findElement(By.name("soyad")).sendKeys("Ogretmen");
        driver.findElement(By.name("kullaniciAdi")).sendKeys("hoca" + System.currentTimeMillis());
        driver.findElement(By.name("sifre")).sendKeys("1234");
        driver.findElement(By.name("telefon")).sendKeys("5559998877");
        driver.findElement(By.name("rol")).sendKeys("Öğretmenim");

        driver.findElement(By.tagName("button")).click();

        assertTrue(driver.getCurrentUrl().contains("/giris"), "Öğretmen kaydı sonrası girişe yönlendirmeli");
    }
}
