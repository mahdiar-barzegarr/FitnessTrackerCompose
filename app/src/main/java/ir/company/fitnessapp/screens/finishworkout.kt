package ir.company.fitnessapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices.PIXEL_5
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ir.company.fitnessapp.R
import ir.company.fitnessapp.data.HistoryItem
import ir.company.fitnessapp.data.saveHistoryItem
import ir.company.fitnessapp.navigation.Screen
import ir.company.fitnessapp.navigation.Screen.Finish.paramsWithArgs
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun FinishWorkOutScreen(navHostController: NavHostController,arg0: String, arg1: String,workout: String) {

    val context = LocalContext.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "Good Job",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color.White
            )
            Spacer(Modifier.height(60.dp))

            Text(
                arg0,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color.White,
                modifier = Modifier.padding(top = 0.dp)
            )

            Text(
                "Calories",
                fontSize = 12.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.White
            )
            Spacer(Modifier.height(60.dp))

            Text(
                "IN",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color.White
            )
            Spacer(Modifier.height(60.dp))

            Text(
                arg1,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color.White
            )

            Text(
                "mins",
                fontSize = 12.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.White
            )

            Spacer(Modifier.height(135.dp))

            AnimatedCircleButton(
                true,
                {
                    val currentTime = System.currentTimeMillis()
                    val formattedDate = SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault()).format(Date(currentTime))
                    val formattedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(currentTime))

                    saveHistoryItem(
                        context,
                        HistoryItem(
                            activityName = workout,
                            date = formattedDate,
                            time = formattedTime,
                            duration = arg1,
                            caloriesBurned = arg0
                        )
                    )

                    navHostController.navigate(Screen.History.route)
                },
                {},
                false,
                R.drawable.check_,
            )


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
fun FinishWorkoutScreenPreview() {

}