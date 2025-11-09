# Panduan Implementasi Smart Keuneunong - Jetpack Compose dengan MVVM

## 📋 Daftar Isi
1. [Struktur Proyek](#struktur-proyek)
2. [Mapping Komponen Web ke Compose](#mapping-komponen)
3. [Tema Material Design 3](#tema-material-design-3)
4. [Arsitektur MVVM](#arsitektur-mvvm)
5. [Implementasi Screen](#implementasi-screen)
6. [Integrasi API](#integrasi-api)
7. [Navigasi](#navigasi)

---

## 1. Struktur Proyek

Struktur folder untuk proyek Android Anda:

```
app/src/main/java/com/aceh/smartkeuneunong/
│
├── data/
│   ├── model/
│   │   ├── WeatherData.kt
│   │   ├── KeuneunongPhase.kt
│   │   ├── TideData.kt
│   │   └── Recommendation.kt
│   ├── repository/
│   │   ├── WeatherRepository.kt
│   │   ├── KeuneunongRepository.kt
│   │   └── TideRepository.kt
│   └── remote/
│       ├── BMKGApiService.kt
│       └── dto/
│
├── di/
│   └── AppModule.kt
│
├── ui/
│   ├── theme/
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   └── Type.kt
│   ├── components/
│   │   ├── WeatherCard.kt
│   │   ├── KeuneunongCalendar.kt
│   │   └── TideInfo.kt
│   ├── screens/
│   │   ├── splash/
│   │   │   ├── SplashScreen.kt
│   │   │   └── SplashViewModel.kt
│   │   ├── dashboard/
│   │   │   ├── DashboardScreen.kt
│   │   │   └── DashboardViewModel.kt
│   │   ├── weather/
│   │   │   ├── WeatherDetailScreen.kt
│   │   │   └── WeatherViewModel.kt
│   │   ├── recommendations/
│   │   │   ├── RecommendationsScreen.kt
│   │   │   └── RecommendationsViewModel.kt
│   │   └── notifications/
│   │       ├── NotificationsScreen.kt
│   │       └── NotificationsViewModel.kt
│   └── navigation/
│       └── NavGraph.kt
│
├── utils/
│   ├── DateUtils.kt
│   ├── HijriConverter.kt
│   └── KeuneunongCalculator.kt
│
└── MainActivity.kt
```

---

## 2. Mapping Komponen Web ke Compose

### Mapping Tailwind CSS ke Compose Modifier

| Tailwind Class | Jetpack Compose Equivalent |
|----------------|---------------------------|
| `rounded-[28px]` | `Modifier.clip(RoundedCornerShape(28.dp))` |
| `shadow-lg` | `Modifier.shadow(8.dp, RoundedCornerShape(28.dp))` |
| `p-4` | `Modifier.padding(16.dp)` |
| `bg-blue-500` | `Modifier.background(Blue500)` |
| `flex items-center` | `Row(verticalAlignment = Alignment.CenterVertically)` |
| `grid grid-cols-2` | `LazyVerticalGrid(GridCells.Fixed(2))` |
| `gap-3` | `Arrangement.spacedBy(12.dp)` |
| `text-sm` | `style = MaterialTheme.typography.bodySmall` |
| `font-semibold` | `fontWeight = FontWeight.SemiBold` |

### Mapping Warna dari Tailwind

```kotlin
// ui/theme/Color.kt    DONE
val Blue50 = Color(0xFFF0F9FF)
val Blue100 = Color(0xFFE0F2FE)
val Blue400 = Color(0xFF60A5FA)
val Blue500 = Color(0xFF3B82F6)
val Blue600 = Color(0xFF2563EB)

val Green50 = Color(0xFFF0FDF4)
val Green100 = Color(0xFFDCFCE7)
val Green500 = Color(0xFF22C55E)
val Green600 = Color(0xFF16A34A)

val Gray50 = Color(0xFFF9FAFB)
val Gray100 = Color(0xFFF3F4F6)
val Gray500 = Color(0xFF6B7280)
val Gray700 = Color(0xFF374151)
val Gray900 = Color(0xFF111827)

val Cyan400 = Color(0xFF22D3EE)
val Cyan500 = Color(0xFF06B6D4)
```

---

## 3. Tema Material Design 3

### Theme.kt

```kotlin
// ui/theme/Theme.kt        DONE
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Blue500,
    onPrimary = Color.White,
    primaryContainer = Blue100,
    onPrimaryContainer = Blue900,
    
    secondary = Green500,
    onSecondary = Color.White,
    secondaryContainer = Green100,
    onSecondaryContainer = Green900,
    
    background = Gray50,
    onBackground = Gray900,
    
    surface = Color.White,
    onSurface = Gray900,
    surfaceVariant = Gray100,
    onSurfaceVariant = Gray500,
)

@Composable
fun SmartKeuneunongTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
```

### Typography.kt

```kotlin
// ui/theme/Type.kt         DONE
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontSize = 57.sp,
        lineHeight = 64.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
)
```

---

## 4. Arsitektur MVVM

### Model Layer

```kotlin
// data/model/WeatherData.kt
data class WeatherData(
    val temperature: Int,
    val condition: String,
    val humidity: Int,
    val windSpeed: Int,
    val weatherIcon: String,
    val feelsLike: Int,
    val location: String,
    val date: Long
)

// data/model/KeuneunongPhase.kt
data class KeuneunongPhase(
    val id: Int,
    val name: String,
    val icon: String,
    val description: String,
    val startDate: Long,
    val endDate: Long,
    val activities: List<String>
)

// data/model/TideData.kt
data class TideData(
    val time: String,
    val type: TideType,
    val height: Double
)

enum class TideType {
    HIGH, LOW
}

// data/model/Recommendation.kt
data class Recommendation(
    val id: Int,
    val category: RecommendationCategory,
    val title: String,
    val description: String,
    val priority: Priority,
    val details: List<String>,
    val dateRange: String
)

enum class RecommendationCategory {
    FARMING, FISHING, TRADITION
}

enum class Priority {
    HIGH, MEDIUM, LOW
}
```

### Repository Layer

```kotlin
// data/repository/WeatherRepository.kt
class WeatherRepository(
    private val bmkgApiService: BMKGApiService
) {
    suspend fun getCurrentWeather(location: String): Result<WeatherData> {
        return try {
            val response = bmkgApiService.getCurrentWeather(location)
            Result.success(response.toWeatherData())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getHourlyForecast(location: String): Result<List<WeatherData>> {
        return try {
            val response = bmkgApiService.getHourlyForecast(location)
            Result.success(response.map { it.toWeatherData() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// data/repository/KeuneunongRepository.kt
class KeuneunongRepository(
    private val keuneunongCalculator: KeuneunongCalculator
) {
    fun getCurrentPhase(date: Long): KeuneunongPhase {
        return keuneunongCalculator.calculatePhase(date)
    }
    
    fun getMonthlyPhases(year: Int, month: Int): List<KeuneunongPhase> {
        return keuneunongCalculator.calculateMonthlyPhases(year, month)
    }
}
```

### ViewModel Layer

```kotlin
// ui/screens/dashboard/DashboardViewModel.kt
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val keuneunongRepository: KeuneunongRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    
    init {
        loadDashboardData()
    }
    
    private fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            val weatherResult = weatherRepository.getCurrentWeather("Banda Aceh")
            val currentPhase = keuneunongRepository.getCurrentPhase(System.currentTimeMillis())
            val monthlyPhases = keuneunongRepository.getMonthlyPhases(2025, 11)
            
            _uiState.update {
                it.copy(
                    isLoading = false,
                    currentWeather = weatherResult.getOrNull(),
                    currentPhase = currentPhase,
                    monthlyPhases = monthlyPhases,
                    error = weatherResult.exceptionOrNull()?.message
                )
            }
        }
    }
    
    fun onMonthChange(delta: Int) {
        // Handle month navigation
    }
}

data class DashboardUiState(
    val isLoading: Boolean = false,
    val currentWeather: WeatherData? = null,
    val currentPhase: KeuneunongPhase? = null,
    val monthlyPhases: List<KeuneunongPhase> = emptyList(),
    val selectedDate: Long = System.currentTimeMillis(),
    val error: String? = null
)
```

---

## 5. Implementasi Screen

### SplashScreen.kt

```kotlin
// ui/screens/splash/SplashScreen.kt        DONE
@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }
    
    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500)
        )
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 600)
        )
        delay(3000)
        onSplashFinished()
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Blue400, Blue300, Blue200)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo with animation
            Box(
                modifier = Modifier
                    .size(128.dp)
                    .scale(scale.value)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .shadow(16.dp, RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = Blue500
                )
                // Add moon emoji overlay
                Text(
                    text = "🌙",
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // App name
            Text(
                text = "Smart Keuneunong",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                modifier = Modifier.alpha(alpha.value)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Kalender Cerdas Tradisional Aceh",
                style = MaterialTheme.typography.bodyLarge,
                color = Blue100,
                modifier = Modifier.alpha(alpha.value)
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Loading indicator
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.alpha(alpha.value)
            )
        }
    }
}
```

### DashboardScreen.kt

```kotlin
// ui/screens/dashboard/DashboardScreen.kt
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray50)
    ) {
        // Header
        DashboardHeader()
        
        // Content
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Current Weather Card
            item {
                CurrentWeatherCard(
                    weather = uiState.currentWeather,
                    keuneunongPhase = uiState.currentPhase
                )
            }
            
            // Calendar
            item {
                KeuneunongCalendar(
                    currentMonth = 10,
                    currentYear = 2025,
                    onMonthChange = viewModel::onMonthChange,
                    phases = uiState.monthlyPhases
                )
            }
            
            // Keuneunong Phases Legend
            item {
                KeuneunongPhasesCard(phases = uiState.monthlyPhases)
            }
            
            // Integration Info
            item {
                IntegrationInfoCard()
            }
        }
    }
    
    // FAB
    FloatingActionButton(
        onClick = { /* Add event */ },
        modifier = Modifier.padding(16.dp),
        containerColor = Blue500,
        shape = RoundedCornerShape(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Tambah Event",
            tint = Color.White
        )
    }
}

@Composable
fun CurrentWeatherCard(
    weather: WeatherData?,
    keuneunongPhase: KeuneunongPhase?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(28.dp)),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = Blue500
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = "${weather?.temperature ?: "--"}°",
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = weather?.condition ?: "Loading...",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    Text(
                        text = "Terasa seperti ${weather?.feelsLike}°",
                        style = MaterialTheme.typography.bodySmall,
                        color = Blue100
                    )
                }
                
                Text(
                    text = weather?.weatherIcon ?: "⛅",
                    fontSize = 60.sp
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Keuneunong Phase
            keuneunongPhase?.let { phase ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.2f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = phase.icon,
                            fontSize = 28.sp,
                            modifier = Modifier.padding(end = 12.dp)
                        )
                        Column {
                            Text(
                                text = "Fase: ${phase.name}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                            Text(
                                text = phase.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = Blue100
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            HorizontalDivider(color = Color.White.copy(alpha = 0.2f))
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Weather details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                WeatherDetailItem(
                    icon = Icons.Default.WaterDrop,
                    label = "Kelembaban",
                    value = "${weather?.humidity ?: "--"}%"
                )
                WeatherDetailItem(
                    icon = Icons.Default.Air,
                    label = "Angin",
                    value = "${weather?.windSpeed ?: "--"} km/h"
                )
            }
        }
    }
}

@Composable
fun WeatherDetailItem(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(18.dp)
        )
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = Blue100
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
```

### KeuneunongCalendar.kt (Komponen)

```kotlin
// ui/components/KeuneunongCalendar.kt
@Composable
fun KeuneunongCalendar(
    currentMonth: Int,
    currentYear: Int,
    onMonthChange: (Int) -> Unit,
    phases: List<KeuneunongPhase>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(28.dp)),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "${getMonthName(currentMonth)} $currentYear",
                        style = MaterialTheme.typography.titleLarge,
                        color = Gray900
                    )
                    Text(
                        text = "Jumadil Awal 1447 H",
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray500
                    )
                }
                
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(onClick = { onMonthChange(-1) }) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = "Bulan sebelumnya"
                        )
                    }
                    IconButton(onClick = { onMonthChange(1) }) {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Bulan berikutnya"
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Days of week
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("Min", "Sen", "Sel", "Rab", "Kam", "Jum", "Sab").forEach { day ->
                    Text(
                        text = day,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = Gray500,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Calendar grid
            CalendarGrid(
                daysInMonth = 30,
                startDayOfWeek = 6,
                currentDay = 8,
                phases = phases
            )
        }
    }
}

@Composable
fun CalendarGrid(
    daysInMonth: Int,
    startDayOfWeek: Int,
    currentDay: Int,
    phases: List<KeuneunongPhase>
) {
    val totalCells = ((daysInMonth + startDayOfWeek - 1) / 7 + 1) * 7
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.height(300.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(totalCells) { index ->
            val day = index - startDayOfWeek + 1
            if (day in 1..daysInMonth) {
                CalendarDay(
                    day = day,
                    isToday = day == currentDay,
                    hasPhase = phases.any { it.startDate == day.toLong() },
                    weatherIcon = getWeatherIconForDay(day)
                )
            } else {
                Spacer(modifier = Modifier.aspectRatio(1f))
            }
        }
    }
}

@Composable
fun CalendarDay(
    day: Int,
    isToday: Boolean,
    hasPhase: Boolean,
    weatherIcon: String
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(
                when {
                    isToday -> Blue500
                    hasPhase -> Green50
                    else -> Color.Transparent
                }
            )
            .clickable { /* Handle day click */ },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = day.toString(),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = if (isToday) FontWeight.Bold else FontWeight.Medium,
                color = if (isToday) Color.White else Gray700
            )
            Text(
                text = weatherIcon,
                fontSize = 14.sp
            )
        }
    }
}
```

---

## 6. Integrasi API

### Retrofit Setup

```kotlin
// data/remote/BMKGApiService.kt
interface BMKGApiService {
    @GET("weather/current")
    suspend fun getCurrentWeather(
        @Query("location") location: String
    ): WeatherResponse
    
    @GET("weather/forecast/hourly")
    suspend fun getHourlyForecast(
        @Query("location") location: String,
        @Query("hours") hours: Int = 10
    ): List<WeatherResponse>
    
    @GET("tide")
    suspend fun getTideData(
        @Query("location") location: String,
        @Query("date") date: String
    ): TideResponse
}

// di/AppModule.kt
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.bmkg.go.id/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    @Provides
    @Singleton
    fun provideBMKGApiService(retrofit: Retrofit): BMKGApiService {
        return retrofit.create(BMKGApiService::class.java)
    }
    
    @Provides
    @Singleton
    fun provideWeatherRepository(
        apiService: BMKGApiService
    ): WeatherRepository {
        return WeatherRepository(apiService)
    }
}
```

---

## 7. Navigasi

### NavGraph.kt

```kotlin
// ui/navigation/NavGraph.kt
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Dashboard : Screen("dashboard")
    object Weather : Screen("weather")
    object Recommendations : Screen("recommendations")
    object Notifications : Screen("notifications")
}

@Composable
fun SmartKeuneunongNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Dashboard.route) {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") { DashboardScreen() }
            composable("weather") { WeatherDetailScreen() }
            composable("recommendations") { RecommendationsScreen() }
            composable("notifications") { NotificationsScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("home", "Kalender", Icons.Default.Home),
        BottomNavItem("weather", "Cuaca", Icons.Default.Cloud),
        BottomNavItem("recommendations", "Rekomendasi", Icons.Default.Lightbulb),
        BottomNavItem("notifications", "Notifikasi", Icons.Default.Notifications)
    )
    
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Blue600,
                    selectedTextColor = Blue600,
                    indicatorColor = Blue100,
                    unselectedIconColor = Gray500,
                    unselectedTextColor = Gray500
                )
            )
        }
    }
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)
```

### MainActivity.kt

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartKeuneunongTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SmartKeuneunongNavGraph()
                }
            }
        }
    }
}
```

---

## 📦 Dependencies (build.gradle.kts)

```kotlin
dependencies {
    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    
    // Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")
    
    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    
    // Hilt
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-compiler:2.50")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    
    // Coil for image loading
    implementation("io.coil-kt:coil-compose:2.5.0")
    
    // Datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
}
```

---

## 🎯 Checklist Implementasi

- [ ] Setup project dengan dependencies
- [ ] Implementasi tema Material Design 3
- [ ] Buat model data untuk Weather, Keuneunong, Tide
- [ ] Implementasi Repository layer
- [ ] Setup Retrofit untuk BMKG API
- [ ] Buat ViewModel untuk setiap screen
- [ ] Implementasi SplashScreen dengan animasi
- [ ] Implementasi DashboardScreen
- [ ] Implementasi KeuneunongCalendar component
- [ ] Implementasi WeatherDetailScreen
- [ ] Implementasi RecommendationsScreen
- [ ] Implementasi NotificationsScreen
- [ ] Setup Navigation Graph
- [ ] Implementasi Bottom Navigation
- [ ] Testing dan debugging
- [ ] Optimasi performa

---

## 💡 Tips Praktis

1. **Mulai dari komponen kecil**: Buat komponen reusable seperti WeatherCard, CalendarDay terlebih dahulu
2. **Gunakan Preview**: Manfaatkan `@Preview` annotation untuk melihat UI tanpa run aplikasi
3. **State Management**: Gunakan `remember` dan `rememberSaveable` untuk state lokal
4. **Testing**: Buat mock data untuk testing sebelum integrasi API
5. **Performance**: Gunakan `LazyColumn` dan `LazyVerticalGrid` untuk list yang panjang
6. **Accessibility**: Tambahkan contentDescription untuk semua Icon dan Image

---

## 📚 Referensi

- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [MVVM Architecture](https://developer.android.com/topic/architecture)
- [Hilt Dependency Injection](https://developer.android.com/training/dependency-injection/hilt-android)

