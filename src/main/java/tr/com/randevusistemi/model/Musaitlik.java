package tr.com.randevusistemi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "musaitlik")
public class Musaitlik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ogretmen_id", referencedColumnName = "id")
    private Kullanici ogretmen;

    // Basit takvim: Haftanın 7 günü için müsaitlik durumu (true: Müsait, false:
    // Dolu)
    private boolean pazartesi;
    private boolean sali;
    private boolean carsamba;
    private boolean persembe;
    private boolean cuma;
    private boolean cumartesi;
    private boolean pazar;

    public Musaitlik() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kullanici getOgretmen() {
        return ogretmen;
    }

    public void setOgretmen(Kullanici ogretmen) {
        this.ogretmen = ogretmen;
    }

    public boolean isPazartesi() {
        return pazartesi;
    }

    public void setPazartesi(boolean pazartesi) {
        this.pazartesi = pazartesi;
    }

    public boolean isSali() {
        return sali;
    }

    public void setSali(boolean sali) {
        this.sali = sali;
    }

    public boolean isCarsamba() {
        return carsamba;
    }

    public void setCarsamba(boolean carsamba) {
        this.carsamba = carsamba;
    }

    public boolean isPersembe() {
        return persembe;
    }

    public void setPersembe(boolean persembe) {
        this.persembe = persembe;
    }

    public boolean isCuma() {
        return cuma;
    }

    public void setCuma(boolean cuma) {
        this.cuma = cuma;
    }

    public boolean isCumartesi() {
        return cumartesi;
    }

    public void setCumartesi(boolean cumartesi) {
        this.cumartesi = cumartesi;
    }

    public boolean isPazar() {
        return pazar;
    }

    public void setPazar(boolean pazar) {
        this.pazar = pazar;
    }
}
