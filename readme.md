# Panduan Instalasi dan Konfigurasi JavaFX dengan IntelliJ IDEA

## Instalasi JavaFX

1. Unduh JavaFX SDK dari situs resminya: [JavaFX Download](https://openjfx.io/).
2. Ekstrak file zip yang telah diunduh ke lokasi yang diinginkan di komputer Anda.

## Konfigurasi IntelliJ IDEA

1. Buka IntelliJ IDEA dan buka proyek Java yang ingin Anda konfigurasikan dengan JavaFX.
2. Buka menu "File" -> "Project Structure..." atau tekan `Ctrl + Alt + Shift + S` (pada Windows/Linux) atau `Cmd + ;` (pada macOS).
3. Pilih "Libraries" di bawah bagian "Project Settings".
4. Klik tombol "+" dan pilih "Java" untuk menambahkan modul JavaFX SDK yang telah Anda unduh. Pilih direktori tempat Anda mengekstrak JavaFX SDK.
5. Sekarang, pilih proyek di sebelah kiri, lalu pilih tab "Modules".
6. Pilih modul yang Anda ingin tambahkan JavaFX SDK-nya, misalnya, modul "main".
7. Klik tab "Dependencies", lalu klik tombol "+".
8. Pilih "JARs or directories...".
9. Telusuri dan pilih direktori `lib` di dalam direktori JavaFX SDK yang Anda ekstrak sebelumnya.
10. Klik "OK" untuk menambahkan path modul JavaFX SDK ke proyek Anda.

## Konfigurasi Run/Debug

1. Buka menu "Run" -> "Edit Configurations...".
2. Pilih konfigurasi aplikasi Anda di panel sebelah kiri.
3. Di bawah tab "Configuration", tambahkan argumen VM tambahan dengan memilih kotak centang "VM options".
4. Masukkan argumen VM berikut:
