package com.mjramirez.openweatherapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Thunderstorm
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import com.mjramirez.openweatherapp.data.api.WeatherResponse
import com.mjramirez.openweatherapp.data.db.WeatherEntry
import com.mjramirez.openweatherapp.data.repository.WeatherRepository
import com.mjramirez.openweatherapp.ui.presenter.WeatherContract
import com.mjramirez.openweatherapp.ui.presenter.WeatherPresenter
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onSignOut: () -> Unit) {
    // Get LocalContext.current directly in the Composable scope
    val context = LocalContext.current
    val repo = remember { WeatherRepository(context) }
    val history by repo.history().collectAsState(initial = emptyList())

    var current by remember { mutableStateOf<WeatherResponse?>(null) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    val presenter = remember {
        WeatherPresenter(context, object : WeatherContract.View {
            override fun showLoading(show: Boolean) { loading = show }
            override fun showCurrentWeather(data: WeatherResponse?) { current = data }
            override fun showError(message: String) { error = message }
        })
    }

    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch { presenter.loadCurrentWeather() }
    }

    var tabIndex by remember { mutableIntStateOf(0) }
    Column(Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("WeatherApp") }, actions = {
            TextButton(onClick = onSignOut) { Text("Sign out") }
        })
        TabRow(selectedTabIndex = tabIndex) {
            Tab(selected = tabIndex==0, onClick = { tabIndex = 0 }, text = { Text("Current") })
            Tab(selected = tabIndex==1, onClick = { tabIndex = 1 }, text = { Text("History") })
        }
        when (tabIndex) {
            0 -> CurrentWeatherTab(current, loading, error)
            1 -> HistoryTab(history)
        }
    }
}

@Composable
fun CurrentWeatherTab(current: WeatherResponse?, loading: Boolean, error: String?) {
    Column(Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        if (loading) {
            CircularProgressIndicator()
        } else if (error != null) {
            Text("Error: $error", color = MaterialTheme.colorScheme.error)
        } else if (current != null) {
            Text("${current.name}, ${current.sys?.country ?: ""}", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))
            Text("${current.main?.temp?.toInt() ?: 0}°C", style = MaterialTheme.typography.displaySmall)
            Spacer(Modifier.height(8.dp))

            val icon = remember(current) { weatherIconFor(current) }
            Icon(icon, contentDescription = "Weather", modifier = Modifier.size(72.dp))

            Spacer(Modifier.height(16.dp))
            val sunrise = current.sys?.sunrise?.let { unixToTime(it) } ?: "-"
            val sunset = current.sys?.sunset?.let { unixToTime(it) } ?: "-"
            Text("Sunrise: $sunrise • Sunset: $sunset")
        } else {
            Text("No data yet")
        }
    }
}

@Composable
fun HistoryTab(history: List<WeatherEntry>) {
    if (history.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No history yet")
        }
    } else {
        LazyColumn(Modifier.fillMaxSize().padding(8.dp)) {
            items(history) { item ->
                ElevatedCard(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                    Column(Modifier.padding(16.dp)) {
                        Text("${item.city}, ${item.country}", style = MaterialTheme.typography.titleMedium)
                        Text(SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(item.timestamp)))
                        Text("Temp: ${item.tempC.toInt()}°C • ${item.condition}")
                    }
                }
            }
        }
    }
}

private fun unixToTime(unix: Long): String {
    val date = Date(unix * 1000)
    val fmt = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return fmt.format(date)
}

private fun weatherIconFor(current: WeatherResponse): ImageVector {
    val cond = current.weather.firstOrNull()?.main ?: "Clear"
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val isAfter6pm = hour >= 18
    return when {
        cond.contains("Rain", true) -> Icons.Filled.Umbrella
        cond.contains("Drizzle", true) -> Icons.Filled.Umbrella
        cond.contains("Thunderstorm", true) -> Icons.Filled.Thunderstorm
        cond.contains("Clear", true) && isAfter6pm -> Icons.Filled.NightsStay
        cond.contains("Clear", true) -> Icons.Filled.WbSunny
        else -> if (isAfter6pm) Icons.Filled.NightsStay else Icons.Filled.WbSunny
    }
}

