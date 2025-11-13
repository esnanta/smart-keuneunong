# Setup Location Picker Feature

## Cara Mendapatkan Google Maps API Key

Untuk menggunakan fitur Location Picker, Anda perlu mendapatkan Google Maps API Key terlebih dahulu:

### Langkah 1: Buat Project di Google Cloud Console
1. Kunjungi [Google Cloud Console](https://console.cloud.google.com/)
2. Login dengan akun Google Anda
3. Buat project baru atau pilih project yang sudah ada

### Langkah 2: Enable APIs
1. Di sidebar, pilih **APIs & Services** > **Library**
2. Cari dan enable API berikut:
   - **Maps SDK for Android**
   - **Places API** (opsional, untuk autocomplete lokasi)

### Langkah 3: Buat API Key
1. Di sidebar, pilih **APIs & Services** > **Credentials**
2. Klik **Create Credentials** > **API Key**
3. Copy API Key yang baru dibuat

### Langkah 4: Restrict API Key (Recommended)
1. Klik pada API Key yang baru dibuat
2. Di bagian **Application restrictions**, pilih **Android apps**
3. Klik **Add an item**
4. Masukkan:
   - **Package name**: `com.smart.keuneunong`
   - **SHA-1 certificate fingerprint**: Dapatkan dengan perintah:
     ```bash
     # Debug certificate
     keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
     
     # Release certificate (jika ada)
     keytool -list -v -keystore /path/to/your/keystore.jks -alias your-alias
     ```
5. Di bagian **API restrictions**, pilih **Restrict key** dan pilih:
   - Maps SDK for Android
   - Places API (jika digunakan)
6. Klik **Save**

### Langkah 5: Tambahkan API Key ke Project
1. Buka file `local.properties` di root project
2. Ganti `YOUR_GOOGLE_MAPS_API_KEY_HERE` dengan API Key Anda:
   ```properties
   MAPS_API_KEY=AIzaSyXxXxXxXxXxXxXxXxXxXxXxXxXxXxXxXxX
   ```
3. **PENTING**: Jangan commit file `local.properties` ke Git!

## Fitur Location Picker

### Fitur yang Tersedia:
1. ✅ **Permission Handling**: Otomatis request izin lokasi (ACCESS_FINE_LOCATION dan ACCESS_COARSE_LOCATION)
2. ✅ **Lokasi Saat Ini**: Tombol FAB untuk mendapatkan lokasi pengguna saat ini menggunakan FusedLocationProviderClient
3. ✅ **Pemilihan Manual**: Tap pada peta untuk memilih lokasi
4. ✅ **Marker**: Menampilkan marker di lokasi yang dipilih
5. ✅ **Konfirmasi Lokasi**: Tombol FAB untuk mengkonfirmasi lokasi yang dipilih
6. ✅ **Info Koordinat**: Card yang menampilkan latitude dan longitude lokasi terpilih
7. ✅ **UI Alternatif**: Tampilan khusus jika izin belum diberikan dengan tombol "Minta Izin"

### Cara Menggunakan:
1. Buka aplikasi
2. Tap icon menu (hamburger) di dashboard
3. Pilih "Lokasi Pengguna"
4. Berikan izin lokasi jika diminta
5. Pilih lokasi dengan dua cara:
   - Tap tombol lokasi (icon target) untuk menggunakan lokasi saat ini
   - Atau tap langsung di peta untuk memilih lokasi manual
6. Setelah puas dengan pilihan, tap tombol centang untuk konfirmasi

### Integrasi dengan Code:
```kotlin
LocationPickerScreen(
    onLocationSelected = { location ->
        // Handle lokasi yang dipilih
        val lat = location.latitude
        val lng = location.longitude
        // Simpan ke database, preferences, atau kirim ke server
    },
    onNavigateBack = {
        // Handle navigasi kembali
    }
)
```

## Troubleshooting

### Peta tidak muncul / menampilkan abu-abu
- Pastikan API Key sudah benar di `local.properties`
- Pastikan **Maps SDK for Android** sudah di-enable di Google Cloud Console
- Pastikan restriction API Key sudah sesuai (package name dan SHA-1)
- Clean dan rebuild project: `./gradlew clean build`

### Tidak bisa mendapatkan lokasi saat ini
- Pastikan izin lokasi sudah diberikan
- Pastikan GPS/Location Services aktif di perangkat
- Coba di lokasi dengan sinyal GPS yang baik (tidak di dalam ruangan tertutup)

### Build error
- Pastikan semua dependencies sudah terinstall
- Sync Gradle: File > Sync Project with Gradle Files
- Invalidate cache: File > Invalidate Caches / Restart

## Dependencies yang Digunakan
```gradle
implementation 'com.google.android.gms:play-services-location:21.2.0'
implementation 'com.google.maps.android:maps-compose:4.3.3'
implementation 'com.google.android.gms:play-services-maps:18.2.0'
```

## Permissions yang Diperlukan
```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

## Struktur File
```
app/src/main/java/com/smart/keuneunong/
└── ui/
    └── location/
        └── LocationPickerScreen.kt  # Main composable untuk location picker
```

