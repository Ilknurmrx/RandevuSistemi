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

        // Try to submit
        driver.findElement(By.tagName("button")).click();

        // Should stay on page (either browser validation or backend)
        // Check if we are still on /kayit or if there is an error
        // Note: Browser validation prevents submission, so URL shouldn't change if
        // browser validation works.
        // If backend validation catches it (if we bypass frontend), we also stay on
        // /kayit.

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

        // Should redirect to login
        assertTrue(driver.getCurrentUrl().contains("/giris"), "Başarılı kayıt sonrası giriş sayfasına yönlendirilmeli");
    }
}
