package tr.com.randevusistemi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "talepler")
public class Talep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ogrenci_id")
    private Kullanici ogrenci;

    @ManyToOne
    @JoinColumn(name = "ogretmen_id")
    private Kullanici ogretmen;

    // Durumlar: BEKLIYOR, GORULDU, ILETISIM_KURULDU, TAMAMLANDI, REDDEDILDI
    @Column(nullable = false)
    private String durum;

    private LocalDateTime olusturulmaTarihi;
    private LocalDateTime guncellenmeTarihi;

    @PrePersist
    protected void onCreate() {
        olusturulmaTarihi = LocalDateTime.now();
        durum = "BEKLIYOR";
    }

    @PreUpdate
    protected void onUpdate() {
        guncellenmeTarihi = LocalDateTime.now();
    }

    public Talep() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kullanici getOgrenci() {
        return ogrenci;
    }

    public void setOgrenci(Kullanici ogrenci) {
        this.ogrenci = ogrenci;
    }

    public Kullanici getOgretmen() {
        return ogretmen;
    }

    public void setOgretmen(Kullanici ogretmen) {
        this.ogretmen = ogretmen;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    public LocalDateTime getOlusturulmaTarihi() {
        return olusturulmaTarihi;
    }

    public void setOlusturulmaTarihi(LocalDateTime olusturulmaTarihi) {
        this.olusturulmaTarihi = olusturulmaTarihi;
    }

    public LocalDateTime getGuncellenmeTarihi() {
        return guncellenmeTarihi;
    }

    public void setGuncellenmeTarihi(LocalDateTime guncellenmeTarihi) {
        this.guncellenmeTarihi = guncellenmeTarihi;
    }
}
