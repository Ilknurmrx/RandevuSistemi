package tr.com.randevusistemi.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tr.com.randevusistemi.model.*;
import tr.com.randevusistemi.service.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/ogretmen")
public class OgretmenPanelKontrolcusu {

    @Autowired
    private OgretmenServisi ogretmenServisi;

    @Autowired
    private RandevuServisi randevuServisi;

    @Autowired
    private DegerlendirmeServisi degerlendirmeServisi;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    private Kullanici getSessionUser(HttpSession session) {
        Kullanici k = (Kullanici) session.getAttribute("kullanici");
        if (k == null) {
            System.out.println("DEBUG: Session 'kullanici' is null. Session ID: " + session.getId());
        } else {
            System.out.println(
                    "DEBUG: Session 'kullanici' found: " + k.getKullaniciAdi() + " Session ID: " + session.getId());
        }
        return k;
    }

    @GetMapping
    public String dashboard(HttpSession session, Model model) {
        Kullanici kullanici = getSessionUser(session);
        if (kullanici == null || kullanici.getRol() != 1)
            return "redirect:/giris";

        OgretmenDetay detay = ogretmenServisi.detayGetir(kullanici.getId());
        List<Talep> talepler = randevuServisi.ogretmenTalepleri(kullanici.getId());
        List<Degerlendirme> yorumlar = degerlendirmeServisi.ogretmenDegerlendirmeleri(kullanici.getId());

        // Müsaitlik durumunu getir (Yoksa oluşturur veya null döner servise göre, biz
        // repodan bakalım veya servise soralım)
        // Serviste get metodu yok, ekleyelim ya da repodan çekelim. Repodan çekelim
        // şimdilik hızlı çözüm.
        // Ama repo private değilse servise bir metod eklemek en doğrusu.
        // Şimdilik servise erişimim var, servise 'musaitlikGetir' eklemek lazım veya
        // direkt repodan.
        // Randevu servisinde musaitlikGuncelle var ama get yok.
        // En temizi burada Musaitlik nesnesini view'a göndermek.

        // Hızlı çözüm: Controller içinde MusaitlikDeposu kullanmayalım, servise
        // yazalım.
        Musaitlik musaitlik = randevuServisi.musaitlikGetir(kullanici.getId());

        model.addAttribute("kullanici", kullanici);
        model.addAttribute("detay", detay);
        model.addAttribute("talepler", talepler);
        model.addAttribute("yorumlar", yorumlar);
        model.addAttribute("musaitlik", musaitlik);
        return "ogretmen_panel";
    }

    @GetMapping("/profil")
    public String profilDuzenleSayfasi(HttpSession session, Model model) {
        Kullanici kullanici = getSessionUser(session);
        if (kullanici == null)
            return "redirect:/giris";

        OgretmenDetay detay = ogretmenServisi.detayGetir(kullanici.getId());
        if (detay == null)
            detay = new OgretmenDetay();

        model.addAttribute("detay", detay);
        model.addAttribute("kullanici", kullanici); // Kullanıcıyı da modele ekle
        return "ogretmen_profil";
    }

    @Autowired
    private tr.com.randevusistemi.repository.KullaniciDeposu kullaniciDeposu;

    @PostMapping("/profil")
    public String profilGuncelle(@ModelAttribute OgretmenDetay detay,
            @RequestParam("belge") MultipartFile belge,
            @RequestParam("telefon") String telefon,
            @RequestParam("email") String email,
            HttpSession session) {
        Kullanici kullanici = getSessionUser(session);
        if (kullanici == null)
            return "redirect:/giris";

        // ZORUNLU ALAN KONTROLÜ (Telefon, Email, Branş, CV)
        if (telefon.isEmpty() || email.isEmpty() || detay.getBrans().isEmpty() || detay.getHakkinda().isEmpty()) {
            return "redirect:/ogretmen/profil?error=bosalanlar"; // Hata ile dön
        }

        // Kullanıcı iletişim bilgilerini güncelle
        kullanici.setTelefon(telefon);
        kullanici.setEmail(email);
        kullaniciDeposu.save(kullanici);
        session.setAttribute("kullanici", kullanici); // Session güncelle

        // Basit dosya yükleme
        if (!belge.isEmpty()) {
            try {
                Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, belge.getOriginalFilename());
                if (!Files.exists(Paths.get(UPLOAD_DIRECTORY))) {
                    Files.createDirectories(Paths.get(UPLOAD_DIRECTORY));
                }
                Files.write(fileNameAndPath, belge.getBytes());
                detay.setBelgeUrl(belge.getOriginalFilename()); // Sadece dosya adını tutuyoruz
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Mevcut belge URL'sini korumak gerekebilir
            OgretmenDetay mevcut = ogretmenServisi.detayGetir(kullanici.getId());
            if (mevcut != null) {
                detay.setBelgeUrl(mevcut.getBelgeUrl());
            }

            // Belge ne yeni yüklendi ne de eskisi var -> HATA
            if (mevcut == null || mevcut.getBelgeUrl() == null || mevcut.getBelgeUrl().isEmpty()) {
                return "redirect:/ogretmen/profil?error=belgeyok";
            }
        }

        // Profili güncelleyince tekrar onay gereksin (İsterse)
        // Kullanıcı "admin onaylarsa öğrenci görebilecek" dediği için, güncelleme
        // sonrası onayı kaldırıyoruz.
        // Böylece admin ekranına tekrar "Onay Bekleyen" olarak düşer.
        detay.setOnayli(false);

        ogretmenServisi.detayGuncelle(kullanici, detay);
        return "redirect:/ogretmen";
    }

    @GetMapping("/musaitlik")
    public String musaitlikSayfasi(HttpSession session, Model model) {
        getSessionUser(session);
        // ... Müsaitlik getirme ve gösterme
        return "ogretmen_musaitlik"; // Basitlik için henüz implemente etmedim html'ini
    }

    @PostMapping("/musaitlik")
    public String musaitlikGuncelle(@ModelAttribute Musaitlik musaitlik, HttpSession session) {
        Kullanici kullanici = getSessionUser(session);
        musaitlik.setOgretmen(kullanici);
        randevuServisi.musaitlikGuncelle(kullanici.getId(), musaitlik);
        return "redirect:/ogretmen";
    }

    @GetMapping("/talep/{id}/{durum}")
    public String talepDurumGuncelle(@PathVariable Long id, @PathVariable String durum, HttpSession session) {
        // Güvenlik kontrolü eklenmeli (bu talep bu öğretmene mi ait?)
        randevuServisi.talepGuncelle(id, durum);
        return "redirect:/ogretmen";
    }
}
