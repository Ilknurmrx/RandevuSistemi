package tr.com.randevusistemi.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tr.com.randevusistemi.model.*;
import tr.com.randevusistemi.repository.KullaniciDeposu; // Arama için

import tr.com.randevusistemi.repository.OgretmenDetayDeposu; // Detay için
import tr.com.randevusistemi.repository.SikayetDeposu; // Şikayet için
import tr.com.randevusistemi.service.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ogrenci")
public class OgrenciPanelKontrolcusu {

    @Autowired
    private RandevuServisi randevuServisi;

    @Autowired
    private KullaniciDeposu kullaniciDeposu; // Servis üzerinden gitmek daha doğru ama basitlik için repo

    @Autowired
    private OgretmenDetayDeposu ogretmenDetayDeposu;

    @Autowired
    DegerlendirmeServisi degerlendirmeServisi;

    @Autowired
    private SikayetDeposu sikayetDeposu;

    private Kullanici getSessionUser(HttpSession session) {
        Kullanici k = (Kullanici) session.getAttribute("kullanici");
        if (k == null) {
            System.out.println("DEBUG: (Ogrenci) Session 'kullanici' is null. Session ID: " + session.getId());
        } else {
            System.out.println("DEBUG: (Ogrenci) Session 'kullanici' found: " + k.getKullaniciAdi() + " Session ID: "
                    + session.getId());
        }
        return k;
    }

    @GetMapping
    public String dashboard(@RequestParam(required = false) String ara, HttpSession session, Model model) {
        Kullanici kullanici = getSessionUser(session);
        if (kullanici == null || kullanici.getRol() != 2)
            return "redirect:/giris";

        List<Kullanici> tumOgretmenler = kullaniciDeposu.findAll().stream()
                .filter(k -> k.getRol() == 1)
                .filter(k -> {
                    // Sadece onaylı öğretmenleri göster
                    // Detayı yoksa veya onaylı değilse filtrele
                    return ogretmenDetayDeposu.findByKullaniciId(k.getId())
                            .map(OgretmenDetay::isOnayli)
                            .orElse(false);
                })
                .collect(Collectors.toList());

        List<Kullanici> eng = tumOgretmenler;
        if (ara != null && !ara.isEmpty()) {
            String q = ara.toLowerCase();
            eng = tumOgretmenler.stream()
                    .filter(k -> k.getAd().toLowerCase().contains(q) ||
                            k.getSoyad().toLowerCase().contains(q) ||
                            (ogretmenDetayDeposu.findByKullaniciId(k.getId()).isPresent() &&
                                    ogretmenDetayDeposu.findByKullaniciId(k.getId()).get().getBrans() != null &&
                                    ogretmenDetayDeposu.findByKullaniciId(k.getId()).get().getBrans().toLowerCase()
                                            .contains(q)))
                    .collect(Collectors.toList());
        }

        // Randevularım
        List<Talep> taleplerim = randevuServisi.ogrenciTalepleri(kullanici.getId());

        model.addAttribute("kullanici", kullanici);
        model.addAttribute("ogretmenler", eng);
        model.addAttribute("taleplerim", taleplerim);
        model.addAttribute("ara", ara); // Arama kutusunda kalsın

        return "ogrenci_panel";
    }

    @GetMapping("/ogretmen/{id}")
    public String ogretmenDetay(@PathVariable Long id, Model model) {
        Kullanici ogretmen = kullaniciDeposu.findById(id)
                .orElseThrow(() -> new RuntimeException("Öğretmen bulunamadı"));
        OgretmenDetay detay = ogretmenDetayDeposu.findByKullaniciId(id).orElse(new OgretmenDetay());
        List<Degerlendirme> yorumlar = degerlendirmeServisi.ogretmenDegerlendirmeleri(id);
        Musaitlik musaitlik = randevuServisi.musaitlikGetir(id); // Müsaitlik bilgisini çek

        model.addAttribute("ogretmen", ogretmen);
        model.addAttribute("detay", detay);
        model.addAttribute("yorumlar", yorumlar);
        model.addAttribute("musaitlik", musaitlik); // View'a gönder
        return "ogretmen_detay";
    }

    @PostMapping("/talep-olustur")
    public String talepOlustur(@RequestParam Long ogretmenId, HttpSession session) {
        Kullanici ogrenci = getSessionUser(session);
        Kullanici ogretmen = kullaniciDeposu.findById(ogretmenId).orElseThrow();
        randevuServisi.talepOlustur(ogrenci, ogretmen);
        return "redirect:/ogrenci";
    }

    @PostMapping("/degerlendir")
    public String degerlendir(@RequestParam Long talepId, @RequestParam int puan, @RequestParam String yorum,
            HttpSession session) {
        degerlendirmeServisi.yorumYap(talepId, puan, yorum);
        return "redirect:/ogrenci";
    }

    @PostMapping("/sikayet-et")
    public String sikayetEt(@RequestParam Long sikayetEdilenId,
            @RequestParam String konu,
            @RequestParam String aciklama,
            HttpSession session) {
        Kullanici sikayetEden = getSessionUser(session);
        if (sikayetEden == null)
            return "redirect:/giris";

        Kullanici sikayetEdilen = kullaniciDeposu.findById(sikayetEdilenId).orElse(null);
        if (sikayetEdilen != null) {
            Sikayet sikayet = new Sikayet();
            sikayet.setSikayetEden(sikayetEden);
            sikayet.setSikayetEdilen(sikayetEdilen);
            sikayet.setKonu(konu);
            sikayet.setAciklama(aciklama);
            sikayetDeposu.save(sikayet);
        }

        return "redirect:/ogrenci/ogretmen/" + sikayetEdilenId + "?sikayet=basarili";
    }
}
