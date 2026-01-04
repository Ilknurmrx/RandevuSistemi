package tr.com.randevusistemi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnaSayfaKontrolcusu {

    @GetMapping("/")
    public String anaSayfa() {
        return "redirect:/giris";
    }
}
