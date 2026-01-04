package tr.com.randevusistemi.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tr.com.randevusistemi.model.Kullanici;
import tr.com.randevusistemi.repository.KullaniciDeposu;
import tr.com.randevusistemi.service.KullaniciServisi;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KullaniciServisiTest {

    @Mock
    private KullaniciDeposu kullaniciDeposu;

    @InjectMocks
    private KullaniciServisi kullaniciServisi;

    @Test
    public void testKayitOl_Basarili() {
        Kullanici kullanici = new Kullanici();
        kullanici.setKullaniciAdi("testuser");
        kullanici.setSifre("1234");
        kullanici.setRol(2);

        when(kullaniciDeposu.existsByKullaniciAdi("testuser")).thenReturn(false);
        when(kullaniciDeposu.save(any(Kullanici.class))).thenReturn(kullanici);

        Kullanici sonuc = kullaniciServisi.kayitOl(kullanici);
        assertNotNull(sonuc);
        assertEquals("testuser", sonuc.getKullaniciAdi());
    }

    @Test
    public void testGirisYap_Basarili() {
        Kullanici kullanici = new Kullanici();
        kullanici.setKullaniciAdi("testuser");
        kullanici.setSifre("1234");

        when(kullaniciDeposu.findByKullaniciAdi("testuser")).thenReturn(Optional.of(kullanici));

        Kullanici giris = kullaniciServisi.girisYap("testuser", "1234");
        assertNotNull(giris);
        assertEquals("testuser", giris.getKullaniciAdi());
    }

    @Test
    public void testGirisYap_Basarisiz() {
        when(kullaniciDeposu.findByKullaniciAdi("yanlisuser")).thenReturn(Optional.empty());

        Kullanici giris = kullaniciServisi.girisYap("yanlisuser", "1234");
        assertNull(giris);
    }
}
