package tr.com.randevusistemi.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OgretmenProfilTest extends BaseSeleniumTest {

    @Test
    public void testOgretmenProfilDuzenle() {
        String kadi = "profilhoca" + System.currentTimeMillis();

        // Kayıt
        driver.get(baseUrl + "/kayit");
        driver.findElement(By.name("ad")).sendKeys("Profil");
        driver.findElement(By.name("soyad")).sendKeys("Hoca");
        driver.findElement(By.name("kullaniciAdi")).sendKeys(kadi);
        driver.findElement(By.name("sifre")).sendKeys("pass");
        driver.findElement(By.name("telefon")).sendKeys("5559876543");
        driver.findElement(By.name("email")).sendKeys("hoca_profil@test.com");
        driver.findElement(By.name("rol")).sendKeys("Öğretmenim");
        driver.findElement(By.tagName("button")).click();

        // Giriş
        driver.get(baseUrl + "/giris");
        driver.findElement(By.name("kullaniciAdi")).sendKeys(kadi);
        driver.findElement(By.name("sifre")).sendKeys("pass");
        driver.findElement(By.tagName("button")).click();

        // Profil sayfasına git
        driver.findElement(By.linkText("Profil Düzenle")).click();
        assertTrue(driver.getCurrentUrl().contains("/ogretmen/profil"), "Profil sayfasına gidilmeli");

        // Bir şeyler güncelle
        driver.findElement(By.name("brans")).sendKeys("Matematik");
        driver.findElement(By.tagName("button")).click();

        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("/ogretmen"));

        assertTrue(driver.getCurrentUrl().contains("/ogretmen"), "Profil kaydı sonrası panele dönmeli");
    }
}
