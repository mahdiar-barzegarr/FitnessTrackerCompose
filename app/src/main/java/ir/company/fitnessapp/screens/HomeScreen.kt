package ir.company.fitnessapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import ir.company.fitnessapp.navigation.Screen
import ir.company.fitnessapp.navigation.Screen.Finish.paramsWithArgs

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val items: List<Triple<String, Int, Int>> = listOf(
        Triple("Running", R.drawable.run_, R.raw.boy_running),
        Triple("Swimming", R.drawable.outline_water_24, R.raw.swimming),
        Triple("Cycling", R.drawable.outline_directions_bike_24, R.raw.cycling),
        Triple("Walking", R.drawable.outline_directions_walk_24, R.raw.cycling),
        Triple("Mountaineering", R.drawable.outline_nordic_walking_24, R.raw.cycling),
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),

        ) {
        items(items.toList()) { (workOut, iconRes,animation) ->
            SimpleItemHistory(iconRes, workOut,navHostController,animation)

        }

    }
}

@Composable
fun SimpleItemHistory(iconRes: Int, text: String,navHostController: NavHostController,animation: Int) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        ),
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFFFFA300), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(iconRes),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                }
                Text(
                    text,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier,
                    textAlign = TextAlign.Center
                )
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFF62D34D), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        {
                            navHostController.navigate(
                                Screen.Workout.paramsWithArgs(
                                    text,
                                    animation.toString()
                                )
                            )
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.play),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp),
                            tint = Color.White
                        )
                    }
                }


            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Workout Screen Preview",
    device = PIXEL_5
)
@Composable
fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}