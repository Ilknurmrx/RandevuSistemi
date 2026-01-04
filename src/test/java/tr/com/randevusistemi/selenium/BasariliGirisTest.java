package tr.com.randevusistemi.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasariliGirisTest extends BaseSeleniumTest {

    @Test
    public void testBasariliGiris() {
        // Testin bağımsız olması için önce kayıt oluyoruz
        String kadi = "girisuser" + System.currentTimeMillis();

        driver.get(baseUrl + "/kayit");
        driver.findElement(By.name("ad")).sendKeys("Test");
        driver.findElement(By.name("soyad")).sendKeys("User");
        driver.findElement(By.name("kullaniciAdi")).sendKeys(kadi);
        driver.findElement(By.name("sifre")).sendKeys("pass");
        driver.findElement(By.name("email")).sendKeys("test@example.com");
        driver.findElement(By.name("telefon")).sendKeys("5551234567");
        driver.findElement(By.tagName("button")).click();

        // Şimdi giriş yapıyoruz
        driver.get(baseUrl + "/giris");
        driver.findElement(By.name("kullaniciAdi")).sendKeys(kadi);
        driver.findElement(By.name("sifre")).sendKeys("pass");
        driver.findElement(By.tagName("button")).click();

        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.or(
                        org.openqa.selenium.support.ui.ExpectedConditions.urlContains("/ogrenci"),
                        org.openqa.selenium.support.ui.ExpectedConditions.urlContains("/ogretmen")));

        // Panele girmeli
        assertTrue(driver.getCurrentUrl().contains("/ogrenci") || driver.getCurrentUrl().contains("/ogretmen"),
                "Giriş başarılı olmalı");
    }
}
