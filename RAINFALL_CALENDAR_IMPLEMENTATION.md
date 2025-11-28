# Implementasi Fitur Kalender Pelabelan Historis Curah Hujan

## Ringkasan
Fitur ini mengimplementasikan pelabelan historis curah hujan pada kalender dengan mengikuti prinsip **MVVM (Model-View-ViewModel)** dan **Clean Architecture**.

## Arsitektur

### 1. Domain Layer (Lapisan Bisnis Logika)

#### Entity: `RainfallHistory.kt`
**Lokasi**: `/domain/model/RainfallHistory.kt`

```kotlin
data class RainfallHistory(
    val day: Int,
    val month: Int,
    val year: Int,
    val category: RainfallCategory,
    val amount: Double // dalam mm
)

enum class RainfallCategory(val colorHex: String, val label: String) {
    TINGGI("#2D68C4", "Tinggi"),           // True Blue
    SEDANG("#B5C7EB", "Sedang"),           // Misty Blue
    RENDAH("#FFDBBB", "Rendah"),           // Light Orange
    SANGAT_RENDAH("#FF4B33", "Sangat Rendah") // Red Orange
}
```

**Fungsi**: Mendefinisikan struktur data curah hujan dan kategorinya dengan warna sesuai spesifikasi.

#### Repository Interface: `RainfallRepository.kt`
**Lokasi**: `/domain/repository/RainfallRepository.kt`

```kotlin
interface RainfallRepository {
    suspend fun getRainfallHistory(month: Int, year: Int): List<RainfallHistory>
    suspend fun getRainfallForDay(day: Int, month: Int, year: Int): RainfallHistory?
}
```

**Fungsi**: Mendefinisikan kontrak untuk mengakses data curah hujan (abstraksi).

#### Use Case: `GetRainfallHistoryUseCase.kt`
**Lokasi**: `/domain/usecase/GetRainfallHistoryUseCase.kt`

```kotlin
class GetRainfallHistoryUseCase @Inject constructor(
    private val rainfallRepository: RainfallRepository
) {
    suspend operator fun invoke(month: Int, year: Int): List<RainfallHistory> {
        return rainfallRepository.getRainfallHistory(month, year)
    }
}
```

**Fungsi**: Mengenkapsulasi logika bisnis untuk mendapatkan data curah hujan. ViewModel memanggil use case ini, bukan repository secara langsung.

---

### 2. Data Layer (Lapisan Data)

#### Mock Data Source: `MockRainfallDataSource.kt`
**Lokasi**: `/data/source/MockRainfallDataSource.kt`

**Fungsi**: Menghasilkan data mockup curah hujan dengan distribusi kategori yang bervariasi untuk menunjukkan semua warna label.

**Algoritma Distribusi**:
```kotlin
val categoryPattern = listOf(
    RainfallCategory.TINGGI,
    RainfallCategory.SEDANG,
    RainfallCategory.SEDANG,
    RainfallCategory.RENDAH,
    RainfallCategory.RENDAH,
    RainfallCategory.RENDAH,
    RainfallCategory.SANGAT_RENDAH,
    RainfallCategory.TINGGI,
    RainfallCategory.SEDANG,
    RainfallCategory.RENDAH
)
```

Distribusi ini memastikan semua kategori warna ditampilkan di kalender.

#### Repository Implementation: `RainfallRepositoryImpl.kt`
**Lokasi**: `/data/repository/RainfallRepositoryImpl.kt`

**Fungsi**: Implementasi konkret dari `RainfallRepository`. Menggunakan `MockRainfallDataSource` untuk menyediakan data dan menerapkan caching sederhana.

**Fitur**:
- Simulasi network delay (300ms)
- Caching data per bulan
- Error handling

---

### 3. Presentation Layer (MVVM Implementation)

#### Model: `CalendarDayData.kt` (Updated)
**Lokasi**: `/data/model/CalendarDayData.kt`

```kotlin
data class CalendarDayData(
    val day: Int,
    val isToday: Boolean = false,
    val weatherEmoji: String = "☀️",
    val hasSpecialEvent: Boolean = false,
    val rainfallCategory: RainfallCategory? = null  // ← BARU
)
```

**Perubahan**: Menambahkan field `rainfallCategory` untuk menyimpan kategori curah hujan setiap hari.

#### ViewModel: `HomeViewModel.kt` (Updated)
**Lokasi**: `/ui/home/HomeViewModel.kt`

**Injeksi Dependency**:
```kotlin
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository,
    private val getRainfallHistoryUseCase: GetRainfallHistoryUseCase,  // ← BARU
    val repositoryKeuneunong: RepositoryKeuneunong
) : ViewModel()
```

**Logika Baru**:
```kotlin
private fun loadCalendar(month: Int, year: Int) {
    viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isLoading = true)
        
        try {
            // 1. Fetch rainfall data from use case
            val rainfallData = getRainfallHistoryUseCase(month, year)
            
            // 2. Get calendar days with rainfall data
            val calendarDays = calendarRepository.getCalendarDays(month, year, rainfallData)
            
            _uiState.value = _uiState.value.copy(
                currentMonth = month,
                currentYear = year,
                calendarDays = calendarDays,
                isLoading = false,
                error = null
            )
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = e.message
            )
        }
    }
}
```

**Prinsip MVVM**:
- ✅ ViewModel tidak mengandung logika UI
- ✅ ViewModel hanya memanggil Use Case
- ✅ Data diekspos melalui StateFlow (Observable)
- ✅ View hanya mengobservasi state

#### View: `KeuneunongCalendar.kt` (Updated)
**Lokasi**: `/ui/components/KeuneunongCalendar.kt`

**Komponen Utama yang Diupdate**:

1. **CalendarDayCell** - Menampilkan label curah hujan:
```kotlin
// Rainfall category label (colored strip)
if (dayData.rainfallCategory != null) {
    Box(
        modifier = Modifier
            .size(width = 24.dp, height = 6.dp)
            .background(
                color = getRainfallColor(dayData.rainfallCategory),
                shape = RoundedCornerShape(3.dp)
            )
    )
}
```

2. **RainfallLegend** - Komponen legend untuk kategori:
```kotlin
@Composable
fun RainfallLegend() {
    // Menampilkan 4 kategori: Tinggi, Sedang, Rendah, S. Rendah
    // Dengan warna strip yang sesuai
}
```

**Desain Visual**:
- Label berbentuk **strip/bar** (24dp × 6dp)
- Border radius 3dp untuk tampilan smooth
- Posisi: Di bawah nomor tanggal
- Legend ditampilkan di bawah kalender

---

### 4. Dependency Injection

#### Module: `RainfallModule.kt`
**Lokasi**: `/di/RainfallModule.kt`

```kotlin
@Module
@InstallIn(SingletonComponent::class)
abstract class RainfallModule {
    
    @Binds
    @Singleton
    abstract fun bindRainfallRepository(
        rainfallRepositoryImpl: RainfallRepositoryImpl
    ): RainfallRepository
}
```

**Fungsi**: Menyediakan dependency injection untuk `RainfallRepository` menggunakan Hilt/Dagger.

---

## Warna Kategori Curah Hujan

| Kategori | Warna | Hex Code | Rentang (mm) |
|----------|-------|----------|--------------|
| **Tinggi** | True Blue | `#2D68C4` | 100 - 200 |
| **Sedang** | Misty Blue | `#B5C7EB` | 50 - 99.9 |
| **Rendah** | Light Orange | `#FFDBBB` | 10 - 49.9 |
| **Sangat Rendah** | Red Orange | `#FF4B33` | 0 - 9.9 |

Warna-warna ini didefinisikan di:
- `/ui/theme/Color.kt` (sebagai Color composable)
- `/domain/model/RainfallHistory.kt` (sebagai hex string di enum)

---

## Flow Data (Data Flow)

```
User Action (Next/Prev Month)
    ↓
HomeViewModel.onNextMonth() / onPreviousMonth()
    ↓
HomeViewModel.loadCalendar(month, year)
    ↓
GetRainfallHistoryUseCase.invoke(month, year)
    ↓
RainfallRepository.getRainfallHistory(month, year)
    ↓
RainfallRepositoryImpl (with caching)
    ↓
MockRainfallDataSource.generateMockRainfallData()
    ↓
Return List<RainfallHistory>
    ↓
CalendarRepository.getCalendarDays(month, year, rainfallData)
    ↓
Map rainfall data to CalendarDayData
    ↓
Update HomeUiState
    ↓
HomeScreen (View) observes StateFlow
    ↓
CalendarComponent renders
    ↓
CalendarDayCell shows rainfall label
```

---

## Kepatuhan terhadap Prinsip Arsitektur

### ✅ Clean Architecture

1. **Separation of Concerns**: 
   - Domain, Data, dan Presentation terpisah dengan jelas
   - Tidak ada coupling antar layer

2. **Dependency Rule**: 
   - Domain tidak bergantung pada layer lain
   - Data bergantung pada Domain (implements interface)
   - Presentation bergantung pada Domain (menggunakan use case)

3. **Abstraction**:
   - Repository Interface di Domain
   - Implementation di Data
   - Dependency Injection menginjeksi abstraksi

### ✅ MVVM Pattern

1. **Model**: 
   - `RainfallHistory`, `CalendarDayData`
   - Pure data classes tanpa logika

2. **View**: 
   - `CalendarComponent`, `CalendarDayCell`, `RainfallLegend`
   - Hanya menampilkan UI
   - Tidak ada logika bisnis
   - Observes ViewModel state

3. **ViewModel**: 
   - `HomeViewModel`
   - Tidak ada reference ke View
   - Ekspos data via StateFlow
   - Memanggil Use Case untuk logika bisnis

---

## Penggunaan

### Menjalankan Aplikasi

```bash
./gradlew assembleDebug
./gradlew installDebug
```

### Testing

Kalender akan menampilkan:
- ☀️ / ⛅ / 🌧️ / ☁️ - Emoji cuaca
- Nomor tanggal (dengan highlight untuk hari ini)
- **Strip warna** - Label kategori curah hujan ← FITUR BARU
- ✓ - Marker untuk event khusus

### Navigasi

- **← / →** - Tombol navigasi bulan
- **Legend** - Di bawah kalender untuk referensi kategori

---

## File-file yang Dibuat/Dimodifikasi

### Files Baru:
1. `/domain/model/RainfallHistory.kt` - Entity & Enum
2. `/domain/repository/RainfallRepository.kt` - Repository Interface
3. `/domain/usecase/GetRainfallHistoryUseCase.kt` - Use Case
4. `/data/source/MockRainfallDataSource.kt` - Mock Data
5. `/data/repository/RainfallRepositoryImpl.kt` - Repository Implementation
6. `/di/RainfallModule.kt` - Dependency Injection

### Files Modified:
1. `/ui/theme/Color.kt` - Tambah warna kategori
2. `/data/model/CalendarDayData.kt` - Tambah field rainfallCategory
3. `/domain/repository/CalendarRepository.kt` - Tambah parameter rainfallData
4. `/data/repository/CalendarRepositoryImpl.kt` - Integrasi rainfall data
5. `/ui/home/HomeViewModel.kt` - Integrasi use case
6. `/ui/components/KeuneunongCalendar.kt` - UI label & legend

---

## Kesimpulan

Implementasi ini sepenuhnya patuh pada:
- ✅ **MVVM Pattern** - View, ViewModel, Model terpisah jelas
- ✅ **Clean Architecture** - Domain, Data, Presentation layers terdefinisi
- ✅ **SOLID Principles** - Single Responsibility, Dependency Inversion
- ✅ **Dependency Injection** - Menggunakan Hilt/Dagger
- ✅ **Reactive Programming** - StateFlow untuk observable state
- ✅ **Separation of Concerns** - Setiap class punya tanggung jawab spesifik

Data mockup menampilkan distribusi kategori yang bervariasi untuk mendemonstrasikan semua warna pelabelan curah hujan pada kalender.

