package tr.com.randevusistemi.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sikayetler")
public class Sikayet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sikayet_eden_id")
    private Kullanici sikayetEden;

    @ManyToOne
    @JoinColumn(name = "sikayet_edilen_id")
    private Kullanici sikayetEdilen;

    private String konu; // "Rahatsız etti", "Gelmedi", "Sahte profil"

    @Column(columnDefinition = "TEXT")
    private String aciklama;

    private boolean incelendi; // Admin inceledi mi?

    private LocalDateTime tarih;

    @PrePersist
    protected void onCreate() {
        tarih = LocalDateTime.now();
        incelendi = false;
    }

    public Sikayet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kullanici getSikayetEden() {
        return sikayetEden;
    }

    public void setSikayetEden(Kullanici sikayetEden) {
        this.sikayetEden = sikayetEden;
    }

    public Kullanici getSikayetEdilen() {
        return sikayetEdilen;
    }

    public void setSikayetEdilen(Kullanici sikayetEdilen) {
        this.sikayetEdilen = sikayetEdilen;
    }

    public String getKonu() {
        return konu;
    }

    public void setKonu(String konu) {
        this.konu = konu;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public boolean isIncelendi() {
        return incelendi;
    }

    public void setIncelendi(boolean incelendi) {
        this.incelendi = incelendi;
    }

    public LocalDateTime getTarih() {
        return tarih;
    }

    public void setTarih(LocalDateTime tarih) {
        this.tarih = tarih;
    }
}
