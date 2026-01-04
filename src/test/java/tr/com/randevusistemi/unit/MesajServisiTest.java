package tr.com.randevusistemi.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tr.com.randevusistemi.model.Kullanici;
import tr.com.randevusistemi.model.Mesaj;
import tr.com.randevusistemi.repository.MesajDeposu;
import tr.com.randevusistemi.service.MesajServisi;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MesajServisiTest {

    @Mock
    private MesajDeposu mesajDeposu;

    @InjectMocks
    private MesajServisi mesajServisi;

    @Test
    public void testMesajGonder() {
        Mesaj mesaj = new Mesaj();
        mesaj.setIcerik("Merhaba");

        mesajServisi.mesajGonder(mesaj);

        verify(mesajDeposu, times(1)).save(mesaj);
    }

    @Test
    public void testSohbetGecmisi() {
        Long user1 = 1L;
        Long user2 = 2L;

        Kullanici k1 = new Kullanici();
        k1.setId(user1);
        Kullanici k2 = new Kullanici();
        k2.setId(user2);

        Mesaj m1 = new Mesaj();
        m1.setGonderen(k1);
        m1.setAlici(k2);
        m1.setTarih(LocalDateTime.now().minusHours(2));
        m1.setIcerik("Selam");

        Mesaj m2 = new Mesaj();
        m2.setGonderen(k2);
        m2.setAlici(k1);
        m2.setTarih(LocalDateTime.now().minusHours(1));
        m2.setIcerik("Aleykümselam");

        when(mesajDeposu.findByGonderenId(user1)).thenReturn(Arrays.asList(m1));
        when(mesajDeposu.findByAliciId(user1)).thenReturn(Arrays.asList(m2));

        List<Mesaj> sohbet = mesajServisi.sohbetGecmisi(user1, user2);

        assertEquals(2, sohbet.size());
        assertEquals("Selam", sohbet.get(0).getIcerik()); // Önce m1 (2 saat önce)
        assertEquals("Aleykümselam", sohbet.get(1).getIcerik()); // Sonra m2 (1 saat önce)
    }
}
