package tr.com.randevusistemi.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "kullanicilar")
public class Kullanici implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @jakarta.validation.constraints.NotBlank(message = "Ad alanı boş bırakılamaz")
    @Column(nullable = false)
    private String ad;

    @jakarta.validation.constraints.NotBlank(message = "Soyad alanı boş bırakılamaz")
    @Column(nullable = false)
    private String soyad;

    @jakarta.validation.constraints.NotBlank(message = "Kullanıcı adı boş bırakılamaz")
    @Column(unique = true, nullable = false)
    private String kullaniciAdi;

    @jakarta.validation.constraints.NotBlank(message = "Şifre alanı boş bırakılamaz")
    @Column(nullable = false)
    private String sifre;

    @jakarta.validation.constraints.NotBlank(message = "Telefon alanı boş bırakılamaz")
    @jakarta.validation.constraints.Pattern(regexp = "5[0-9]{9}", message = "Telefon numarası 5 ile başlamalı ve 10 haneli olmalıdır")
    @Column(nullable = false)
    private String telefon;

    @jakarta.validation.constraints.NotBlank(message = "E-posta alanı boş bırakılamaz")
    @jakarta.validation.constraints.Email(message = "Geçerli bir e-posta adresi giriniz")
    @Column(nullable = true) // Veritabanı geçişi için nullable yapıldı. Formda zorunlu tutulacak.
    private String email;

    // 0: Admin, 1: Öğretmen, 2: Öğrenci
    @Column(nullable = false)
    private int rol;

    public String getAdSoyad() {
        return ad + " " + soyad;
    }

    public Kullanici() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }
}
