package tr.com.randevusistemi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "degerlendirmeler")
public class Degerlendirme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "talep_id")
    private Talep talep; // Hangi talep üzerinden değerlendirme yapıldığı

    @ManyToOne
    @JoinColumn(name = "ogretmen_id")
    private Kullanici ogretmen; // Değerlendirilen öğretmen

    @ManyToOne
    @JoinColumn(name = "ogrenci_id")
    private Kullanici ogrenci; // Değerlendiren öğrenci

    private int puan; // 1-5 arası

    @Column(columnDefinition = "TEXT")
    private String yorum;

    private LocalDateTime tarih;

    @PrePersist
    protected void onCreate() {
        tarih = LocalDateTime.now();
    }

    public Degerlendirme() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Talep getTalep() {
        return talep;
    }

    public void setTalep(Talep talep) {
        this.talep = talep;
    }

    public Kullanici getOgretmen() {
        return ogretmen;
    }

    public void setOgretmen(Kullanici ogretmen) {
        this.ogretmen = ogretmen;
    }

    public Kullanici getOgrenci() {
        return ogrenci;
    }

    public void setOgrenci(Kullanici ogrenci) {
        this.ogrenci = ogrenci;
    }

    public int getPuan() {
        return puan;
    }

    public void setPuan(int puan) {
        this.puan = puan;
    }

    public String getYorum() {
        return yorum;
    }

    public void setYorum(String yorum) {
        this.yorum = yorum;
    }

    public LocalDateTime getTarih() {
        return tarih;
    }

    public void setTarih(LocalDateTime tarih) {
        this.tarih = tarih;
    }
}
