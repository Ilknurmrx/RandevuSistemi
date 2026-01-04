package tr.com.randevusistemi.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tr.com.randevusistemi.model.Kullanici;
import tr.com.randevusistemi.service.KullaniciServisi;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class GirisKayitKontrolcusu {

    @Autowired
    private KullaniciServisi kullaniciServisi;

    @GetMapping("/giris")
    public String girisSayfasi() {
        return "giris";
    }

    @PostMapping("/giris")
    public String girisYap(@RequestParam String kullaniciAdi, @RequestParam String sifre, HttpSession session,
            Model model) {
        Kullanici kullanici = kullaniciServisi.girisYap(kullaniciAdi, sifre);
        if (kullanici != null) {
            session.setAttribute("kullanici", kullanici);
            if (kullanici.getRol() == 0) {
                return "redirect:/admin";
            } else if (kullanici.getRol() == 1) {
                return "redirect:/ogretmen";
            } else {
                return "redirect:/ogrenci";
            }
        }
        model.addAttribute("hata", "Geçersiz kullanıcı adı veya şifre!");
        return "giris";
    }

    @GetMapping("/kayit")
    public String kayitSayfasi(Model model) {
        model.addAttribute("kullanici", new Kullanici());
        return "kayit";
    }

    @PostMapping("/kayit")
    public String kayitOl(@Valid @ModelAttribute("kullanici") Kullanici kullanici, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "kayit";
        }
        try {
            kullaniciServisi.kayitOl(kullanici);
            return "redirect:/giris";
        } catch (Exception e) {
            model.addAttribute("hata", e.getMessage());
            return "kayit";
        }
    }

    @GetMapping("/cikis")
    public String cikisYap(HttpSession session) {
        session.invalidate();
        return "redirect:/giris";
    }
}
