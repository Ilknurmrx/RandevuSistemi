pipeline {
    agent any

    environment {
        PATH = "/usr/local/bin:${env.PATH}"
        DB_HOST = 'db'
        DB_NAME = 'randevu_sistemi'
        DB_USER = 'postgres'
        DB_PASS = '12345'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Kodlar çekiliyor...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Proje derleniyor...'
                sh 'chmod +x mvnw'
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Unit Tests') {
            steps {
                echo 'Birim testleri çalıştırılıyor (kullaniciServisiTest)...'
                // Sadece unit test paketini çalıştır
                sh './mvnw test -Dtest=tr.com.randevusistemi.unit.**'
            }
        }
        
        stage('Integration Tests') {
             steps {
                 echo 'Entegrasyon testleri çalıştırılıyor...'
                 sh './mvnw test -Dtest=tr.com.randevusistemi.integration.**'
             }
        }

        stage('Docker Build & Deploy') {
            steps {
                echo 'Docker containerları ayağa kaldırılıyor...'
                sh 'docker compose up -d --build'
                // Uygulamanın ayağa kalkması için kısa bir bekleme
                sleep 10
            }
        }

        // --- SELENIUM TEST SENARYOLARI (7 Adet - Ayrı Stage'ler) ---
        
        stage('Scenario 1: Ana Sayfa') {
            steps {
                sh './mvnw test -Dtest=tr.com.randevusistemi.selenium.AnaSayfaTest'
            }
        }

        stage('Scenario 2: Ogrenci Kayit') {
            steps {
                sh './mvnw test -Dtest=tr.com.randevusistemi.selenium.OgrenciKayitTest'
            }
        }

        stage('Scenario 3: Ogretmen Kayit') {
             steps {
                 sh './mvnw test -Dtest=tr.com.randevusistemi.selenium.OgretmenKayitTest'
             }
        }

        stage('Scenario 4: Hatali Giris') {
             steps {
                 sh './mvnw test -Dtest=tr.com.randevusistemi.selenium.HataliGirisTest'
             }
        }

        stage('Scenario 5: Basarili Giris') {
             steps {
                 sh './mvnw test -Dtest=tr.com.randevusistemi.selenium.BasariliGirisTest'
             }
        }

        stage('Scenario 6: Ogretmen Profil') {
             steps {
                 sh './mvnw test -Dtest=tr.com.randevusistemi.selenium.OgretmenProfilTest'
             }
        }

        stage('Scenario 7: Ogrenci Vitrin') {
             steps {
                 sh './mvnw test -Dtest=tr.com.randevusistemi.selenium.OgrenciVitrinTest'
             }
        }
    }
    
    post {
        always {
             junit 'target/surefire-reports/*.xml'
        }
    }
}
