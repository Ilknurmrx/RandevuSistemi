package tr.com.randevusistemi.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HataliGirisTest extends BaseSeleniumTest {

    @Test
    public void testHataliGiris() {
        driver.get(baseUrl + "/giris");

        driver.findElement(By.name("kullaniciAdi")).sendKeys("yokboylebiri");
        driver.findElement(By.name("sifre")).sendKeys("yanlis");
        driver.findElement(By.tagName("button")).click();
        waitForElementVisible(By.className("error"));
        assertTrue(driver.findElement(By.className("error")).isDisplayed(), "Hatalı girişte uyarı görünmeli");
    }
}
