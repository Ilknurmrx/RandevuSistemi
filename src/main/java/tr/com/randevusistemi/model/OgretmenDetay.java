package tr.com.randevusistemi.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "ogretmen_detaylari")
public class OgretmenDetay implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "kullanici_id", referencedColumnName = "id")
    private Kullanici kullanici;

    private String brans;

    @Column(columnDefinition = "TEXT")
    private String hakkinda;

    private boolean onayli; // Mavi Tik

    private String profilFotografiUrl; // Profil Resmi
    private String belgeUrl; // Diploma/Sertifika

    public OgretmenDetay() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public String getBrans() {
        return brans;
    }

    public void setBrans(String brans) {
        this.brans = brans;
    }

    public String getHakkinda() {
        return hakkinda;
    }

    public void setHakkinda(String hakkinda) {
        this.hakkinda = hakkinda;
    }

    public boolean isOnayli() {
        return onayli;
    }

    public void setOnayli(boolean onayli) {
        this.onayli = onayli;
    }

    public String getProfilFotografiUrl() {
        return profilFotografiUrl;
    }

    public void setProfilFotografiUrl(String profilFotografiUrl) {
        this.profilFotografiUrl = profilFotografiUrl;
    }

    public String getBelgeUrl() {
        return belgeUrl;
    }

    public void setBelgeUrl(String belgeUrl) {
        this.belgeUrl = belgeUrl;
    }
}
