package tr.com.randevusistemi.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KayitValidasyonTest extends BaseSeleniumTest {

    @Test
    public void testGecersizTelefon() {
        driver.get(baseUrl + "/kayit");
        driver.findElement(By.name("ad")).sendKeys("Test");
        driver.findElement(By.name("soyad")).sendKeys("User");
        driver.findElement(By.name("kullaniciAdi")).sendKeys("validuser" + System.currentTimeMillis());
        driver.findElement(By.name("sifre")).sendKeys("pass");
        driver.findElement(By.name("email")).sendKeys("valid@test.com");
        driver.findElement(By.name("telefon")).sendKeys("0555123456"); // Invalid: starts with 0

        driver.findElement(By.tagName("button")).click();
        waitForUrl("/kayit");
        boolean stillOnPage = driver.getCurrentUrl().contains("/kayit");
        assertTrue(stillOnPage, "Geçersiz telefon ile kayıt olunmamalı");
    }

    @Test
    public void testBasariliKayit() {
        String kadi = "validuser" + System.currentTimeMillis();
        driver.get(baseUrl + "/kayit");
        driver.findElement(By.name("ad")).sendKeys("Test");
        driver.findElement(By.name("soyad")).sendKeys("User");
        driver.findElement(By.name("kullaniciAdi")).sendKeys(kadi);
        driver.findElement(By.name("sifre")).sendKeys("pass");
        driver.findElement(By.name("email")).sendKeys("test@example.com");
        driver.findElement(By.name("telefon")).sendKeys("5559876543"); // Valid

        driver.findElement(By.tagName("button")).click();
        waitForUrl("/giris");
        assertTrue(driver.getCurrentUrl().contains("/giris"), "Başarılı kayıt sonrası giriş sayfasına yönlendirilmeli");
    }
}
