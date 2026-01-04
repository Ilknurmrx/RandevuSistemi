package tr.com.randevusistemi.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OgrenciVitrinTest extends BaseSeleniumTest {

    @Test
    public void testOgrenciVitrinKontrolu() {
        String kadi = "vitrinogrenci" + System.currentTimeMillis();

        // Kayıt
        driver.get(baseUrl + "/kayit");
        driver.findElement(By.name("ad")).sendKeys("Vitrin");
        driver.findElement(By.name("soyad")).sendKeys("Bakici");
        driver.findElement(By.name("kullaniciAdi")).sendKeys(kadi);
        driver.findElement(By.name("sifre")).sendKeys("pass");
        driver.findElement(By.name("telefon")).sendKeys("555");
        driver.findElement(By.tagName("button")).click();

        // Giriş
        driver.get(baseUrl + "/giris");
        driver.findElement(By.name("kullaniciAdi")).sendKeys(kadi);
        driver.findElement(By.name("sifre")).sendKeys("pass");
        driver.findElement(By.tagName("button")).click();

        // Vitrinde 'Öğretmen Bul' başlığı var mı?
        boolean baslikVarMi = driver.getPageSource().contains("Öğretmen Bul");
        assertTrue(baslikVarMi, "Öğrenci panelinde öğretmen vitrini olmalı");
    }
}
