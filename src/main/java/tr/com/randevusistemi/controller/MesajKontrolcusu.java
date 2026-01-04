package tr.com.randevusistemi.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tr.com.randevusistemi.model.Kullanici;
import tr.com.randevusistemi.model.Mesaj;
import tr.com.randevusistemi.service.KullaniciServisi;
import tr.com.randevusistemi.service.MesajServisi;

import java.util.List;

@Controller
public class MesajKontrolcusu {

    @Autowired
    private MesajServisi mesajServisi;

    @Autowired
    private KullaniciServisi kullaniciServisi;

    @GetMapping("/mesajlar")
    public String mesajlariListele(HttpSession session, Model model) {
        Kullanici kullanici = (Kullanici) session.getAttribute("kullanici");
        if (kullanici == null) {
            return "redirect:/giris";
        }

        List<Mesaj> gelenMesajlar = mesajServisi.gelenKutusu(kullanici.getId());
        model.addAttribute("gelenMesajlar", gelenMesajlar);

        List<Mesaj> gidenMesajlar = mesajServisi.gidenKutusu(kullanici.getId());
        model.addAttribute("gidenMesajlar", gidenMesajlar);

        String geriLink = "/";
        if (kullanici.getRol() == 0) {
            geriLink = "/admin";
        } else if (kullanici.getRol() == 1) {
            geriLink = "/ogretmen";
        } else if (kullanici.getRol() == 2) {
            geriLink = "/ogrenci";
        }
        model.addAttribute("geriLink", geriLink);

        return "mesajlar";
    }

    @PostMapping("/mesaj-gonder")
    public String mesajGonder(@RequestParam String aliciKullaniciAdi, @RequestParam String icerik, HttpSession session,
            Model model) {
        Kullanici gonderen = (Kullanici) session.getAttribute("kullanici");
        if (gonderen == null) {
            return "redirect:/giris";
        }

        try {
            Kullanici alici = kullaniciServisi.kullaniciBulByKullaniciAdi(aliciKullaniciAdi);
            if (alici == null) {
                throw new RuntimeException("Kullanıcı bulunamadı: " + aliciKullaniciAdi);
            }

            if (alici.getId().equals(gonderen.getId())) {
                throw new RuntimeException("Kendinize mesaj gönderemezsiniz.");
            }

            Mesaj mesaj = new Mesaj();
            mesaj.setGonderen(gonderen);
            mesaj.setAlici(alici);
            mesaj.setIcerik(icerik);

            mesajServisi.mesajGonder(mesaj);

            return "redirect:/mesajlar";
        } catch (Exception e) {
            model.addAttribute("hata", "Mesaj gönderilemedi: " + e.getMessage());
            // Hata olduğunda listeyi tekrar doldurmamız lazım
            List<Mesaj> gelenMesajlar = mesajServisi.gelenKutusu(gonderen.getId());
            model.addAttribute("gelenMesajlar", gelenMesajlar);
            List<Mesaj> gidenMesajlar = mesajServisi.gidenKutusu(gonderen.getId());
            model.addAttribute("gidenMesajlar", gidenMesajlar);

            String geriLink = "/";
            if (gonderen.getRol() == 0) {
                geriLink = "/admin";
            } else if (gonderen.getRol() == 1) {
                geriLink = "/ogretmen";
            } else if (gonderen.getRol() == 2) {
                geriLink = "/ogrenci";
            }
            model.addAttribute("geriLink", geriLink);

            return "mesajlar";
        }
    }
}
