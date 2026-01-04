package tr.com.randevusistemi.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tr.com.randevusistemi.model.Kullanici;
import tr.com.randevusistemi.model.OgretmenDetay;
import tr.com.randevusistemi.model.Sikayet;
import tr.com.randevusistemi.repository.OgretmenDetayDeposu; // Onay bekleyenleri bulmak için
import tr.com.randevusistemi.service.AdminServisi;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminPanelKontrolcusu {

    @Autowired
    private AdminServisi adminServisi;

    @Autowired
    private OgretmenDetayDeposu ogretmenDetayDeposu;

    private Kullanici getSessionUser(HttpSession session) {
        Kullanici k = (Kullanici) session.getAttribute("kullanici");
        if (k == null) {
            System.out.println("DEBUG: (Admin) Session 'kullanici' is null. Session ID: " + session.getId());
        } else {
            System.out.println("DEBUG: (Admin) Session 'kullanici' found: " + k.getKullaniciAdi() + " Session ID: "
                    + session.getId());
        }
        return k;
    }

    @GetMapping
    public String dashboard(HttpSession session, Model model) {
        Kullanici kullanici = getSessionUser(session);
        if (kullanici == null || kullanici.getRol() != 0)
            return "redirect:/giris";

        // Onaylı olmayan her öğretmeni göster
        List<OgretmenDetay> onayBekleyenler = ogretmenDetayDeposu.findAll().stream()
                .filter(d -> !d.isOnayli())
                .collect(Collectors.toList());

        List<Sikayet> sikayetler = adminServisi.bekleyenSikayetler();

        model.addAttribute("onayBekleyenler", onayBekleyenler);
        model.addAttribute("sikayetler", sikayetler);
        return "admin_panel";
    }

    @GetMapping("/ogretmen/{id}/onayla")
    public String ogretmenOnayla(@PathVariable Long id) {
        // id burada OgretmenDetay id'si olmalı
        adminServisi.ogretmenOnayla(id, true);
        return "redirect:/admin";
    }

    @GetMapping("/sikayet/{id}/incele")
    public String sikayetIncele(@PathVariable Long id) {
        adminServisi.sikayetIncele(id);
        return "redirect:/admin";
    }

    @GetMapping("/kullanici/{id}/sil")
    public String kullaniciSil(@PathVariable Long id) {
        adminServisi.kullaniciSil(id);
        return "redirect:/admin";
    }
}
