package tr.com.randevusistemi.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tr.com.randevusistemi.model.Kullanici;
import tr.com.randevusistemi.model.OgretmenDetay;
import tr.com.randevusistemi.repository.OgretmenDetayDeposu;
import tr.com.randevusistemi.service.OgretmenServisi;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OgretmenServisiTest {

    @Mock
    private OgretmenDetayDeposu ogretmenDetayDeposu;

    @InjectMocks
    private OgretmenServisi ogretmenServisi;

    @Test
    public void testDetayGetir() {
        OgretmenDetay detay = new OgretmenDetay();
        detay.setBrans("Matematik");

        when(ogretmenDetayDeposu.findByKullaniciId(1L)).thenReturn(Optional.of(detay));

        OgretmenDetay sonuc = ogretmenServisi.detayGetir(1L);

        assertNotNull(sonuc);
        assertEquals("Matematik", sonuc.getBrans());
    }

    @Test
    public void testDetayGuncelle() {
        Kullanici kullanici = new Kullanici();
        kullanici.setId(1L);

        OgretmenDetay yeniDetay = new OgretmenDetay();
        yeniDetay.setBrans("Fizik");

        OgretmenDetay mevcutDetay = new OgretmenDetay();
        mevcutDetay.setKullanici(kullanici);

        when(ogretmenDetayDeposu.findByKullaniciId(1L)).thenReturn(Optional.of(mevcutDetay));
        when(ogretmenDetayDeposu.save(any(OgretmenDetay.class))).thenAnswer(i -> i.getArguments()[0]);

        OgretmenDetay sonuc = ogretmenServisi.detayGuncelle(kullanici, yeniDetay);

        assertEquals("Fizik", sonuc.getBrans());
        verify(ogretmenDetayDeposu, times(1)).save(mevcutDetay);
    }
}
