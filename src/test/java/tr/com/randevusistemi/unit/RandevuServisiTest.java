package tr.com.randevusistemi.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tr.com.randevusistemi.model.Kullanici;
import tr.com.randevusistemi.model.Musaitlik;
import tr.com.randevusistemi.model.Talep;
import tr.com.randevusistemi.repository.MusaitlikDeposu;
import tr.com.randevusistemi.repository.TalepDeposu;
import tr.com.randevusistemi.service.RandevuServisi;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RandevuServisiTest {

    @Mock
    private TalepDeposu talepDeposu;

    @Mock
    private MusaitlikDeposu musaitlikDeposu;

    @InjectMocks
    private RandevuServisi randevuServisi;

    @Test
    public void testTalepOlustur_Basarili() {
        Kullanici ogrenci = new Kullanici();
        Kullanici ogretmen = new Kullanici();
        Talep talep = new Talep();
        talep.setDurum("BEKLIYOR");

        when(talepDeposu.save(any(Talep.class))).thenReturn(talep);

        Talep sonuc = randevuServisi.talepOlustur(ogrenci, ogretmen);

        assertNotNull(sonuc);
        assertEquals("BEKLIYOR", sonuc.getDurum());
        verify(talepDeposu, times(1)).save(any(Talep.class));
    }

    @Test
    public void testTalepGuncelle_Onayla() {
        Talep talep = new Talep();
        talep.setId(1L);
        talep.setDurum("BEKLIYOR");

        when(talepDeposu.findById(1L)).thenReturn(Optional.of(talep));
        when(talepDeposu.save(any(Talep.class))).thenAnswer(i -> i.getArguments()[0]);

        Talep guncel = randevuServisi.talepGuncelle(1L, "ONAYLANDI");

        assertEquals("ONAYLANDI", guncel.getDurum());
    }

}
