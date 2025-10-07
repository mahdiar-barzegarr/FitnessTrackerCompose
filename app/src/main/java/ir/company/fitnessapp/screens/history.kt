package ir.company.fitnessapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PIXEL_5
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ir.company.fitnessapp.R
import ir.company.fitnessapp.data.loadHistoryList
import ir.company.fitnessapp.navigation.Screen
import java.sql.Date

@Composable
fun HistoryScreen(
    navHostController: NavHostController,
) {
    val context = LocalContext.current
    var historyList by remember { mutableStateOf(loadHistoryList(context)) }

    LaunchedEffect(key1 = context) {
        historyList = loadHistoryList(context)
    }

    Scaffold(
        topBar = {
            MyTopBar(
                title = "Activity History",
                onBackClick = { navHostController.navigate(Screen.Home.route) },
                onActionClick = { }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn(
                Modifier.fillMaxSize().padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(historyList) { item ->
                    val icon = when(item.activityName.lowercase().trim()) {
                        "running" -> R.drawable.run_
                        "swimming" -> R.drawable.outline_water_24
                        "cycling" -> R.drawable.outline_directions_bike_24
                        "walking" -> R.drawable.outline_directions_walk_24
                        "mountaineering" -> R.drawable.outline_nordic_walking_24
                        else -> R.drawable.run_
                    }

                    ItemHistory(
                        workout = item.activityName,
                        date = item.date,
                        time = item.time,
                        duration = item.duration,
                        calories = item.caloriesBurned,
                        icon = icon
                    )
                }
            }
        }
    }


}


@Composable
fun ItemHistory(workout: String, date: String, time: String, duration: String, calories: String,icon: Int) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        ),
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFFFA300), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }

            Column(
                modifier = Modifier.padding(start = 10.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(workout, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("$date - $time", fontSize = 12.sp, color = Color.DarkGray)
            }

            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text("$duration min", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("$calories cal", fontSize = 12.sp, color = Color.DarkGray)
            }


        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    onActionClick: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(
                title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            onBackClick?.let {
                IconButton(onClick = it) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        },
        modifier = Modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFEEE6E6)
        )
    )
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Workout Screen Preview",
    device = PIXEL_5
)
@Composable
fun HistoryScreenPreview() {
}