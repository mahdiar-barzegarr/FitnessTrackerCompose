package ir.company.fitnessapp.screens

import android.graphics.drawable.Icon
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices.PIXEL_5
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import ir.company.fitnessapp.R
import ir.company.fitnessapp.navigation.Screen
import ir.company.fitnessapp.navigation.Screen.Finish.paramsWithArgs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun WorkoutScreen(navHostController: NavHostController, workout: String, animation: String) {
    var startOrPause by remember { mutableStateOf(false) }
    var time by remember { mutableStateOf(0) }
    var calories by remember { mutableStateOf(0) }

    LaunchedEffect(startOrPause) {
        while (startOrPause) {
            delay(1000)
            time++
            if (time % 60 == 0) {
                calories += when (workout.lowercase()) {
                    "running" -> 8
                    "swimming" -> 12
                    "cycling" -> 9
                    else -> 5
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 75.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(workout, fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif)

        Spacer(Modifier.height(60.dp))
        WaveCircle(isAnimating = startOrPause)
        Spacer(Modifier.height(60.dp))
        RunningManAnimation(isMoving = startOrPause,animation.toInt())
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.Black)
        )
        Spacer(Modifier.height(40.dp))

        AnimatedCircleButton(
            startOrPause = startOrPause,
            onClick = { startOrPause = !startOrPause },
            onLongFinish = {
                startOrPause = false
                navHostController.navigate(
                    Screen.Finish.paramsWithArgs(
                        calories.toString(),
                        formatTime(time),
                        workout
                    ),
                )
            },
            true,
            R.drawable.play
        )
    }
}


fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    return minutes.toString()
}


@Composable
fun WaveCircle(isAnimating: Boolean) {

    val inner = 250.dp
    val lineWidth = 5.dp
    val waveDistance = 20.dp
    val wavesCount = 2
    val duration = 4000

    val density = LocalDensity.current
    val innerPx = with(density) { inner.toPx() }
    val wavePx = with(density) { waveDistance.toPx() }
    val linePx = with(density) { lineWidth.toPx() }

    var progressValue by remember { mutableStateOf(0f) }

    if (isAnimating) {
        val infiniteTransition = rememberInfiniteTransition()
        val progress by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = duration, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
        progressValue = progress
    }

    Box(
        modifier = Modifier.size(inner + waveDistance),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            for (i in 0 until wavesCount) {
                val waveProgress = (progressValue + i.toFloat() / wavesCount) % 1f
                val radius = innerPx / 2 + waveProgress * wavePx
                drawCircle(
                    color = Color(0xffFFB74D),
                    radius = radius,
                    style = Stroke(width = linePx),
                    alpha = 1f - waveProgress,
                    center = Offset(size.width / 2, size.height / 2)
                )
            }
        }

        Box(
            modifier = Modifier
                .size(inner)
                .background(Color(0xffEF6C00), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            TimerText(isAnimating, 0)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    val calories = calorieCalculationPerMin("running", isAnimating).toString()
                    Text(
                        calories,
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text("Calories", color = Color.White, modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

@Composable
fun calorieCalculationPerMin(workout: String, startAndPause: Boolean): Int {

    var calories by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(workout, startAndPause) {
        while (startAndPause) {
            delay(60000)
            calories += when (workout.lowercase()) {
                "running" -> 8
                "swimming" -> 12
                "cycling" -> 9
                else -> 5
            }
        }

    }
    return calories
}

@Composable
fun TimerText(startAndPause: Boolean, timer: Int) {
    var time by remember { mutableStateOf(timer) }

    LaunchedEffect(startAndPause) {
        while (startAndPause) {
            delay(1000)
            time++
        }
    }

    Text(
        text = formatTime(time, {}),
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
}


fun formatTime(seconds: Int, onValueChange: (String) -> Unit): String {
    val minutes = seconds / 60
    val sec = seconds % 60
    val time = "%02d:%02d".format(minutes, sec)
    onValueChange(time)
    return time
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedCircleButton(
    startOrPause: Boolean,
    onClick: () -> Unit,
    onLongFinish: () -> Unit,
    enableLongPress: Boolean = true,
    icon: Int
) {
    var longPressProgress by remember { mutableFloatStateOf(0f) }
    var targetProgress by remember { mutableFloatStateOf(0f) }

    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = 3000, easing = LinearEasing)
    )

    var colorState by remember { mutableStateOf(Color(0xFFF2C12E)) }
    colorState = if (startOrPause) Color(0xFF15E518) else Color(0xFFF2C12E)

    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (isPressed) 0.9f else 1f)

    val coroutineScope = rememberCoroutineScope()

    Box(contentAlignment = Alignment.Center) {
        Surface(
            modifier = Modifier
                .size(60.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .shadow(8.dp, CircleShape)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            isPressed = true
                            coroutineScope.launch {
                                delay(100)
                                isPressed = false
                                onClick()
                            }
                        },
                        onLongPress = {
                            coroutineScope.launch {
                                targetProgress = 1f
                                delay(1500)
                                if (targetProgress >= 1f) {
                                    onLongFinish()
                                }
                            }
                        },
                        onPress = {
                            val startTime = System.currentTimeMillis()
                            tryAwaitRelease()
                            val duration = System.currentTimeMillis() - startTime
                            if (duration < 3000) {
                                targetProgress = 0f
                            }
                        }
                    )
                },
            color = colorState,
            shape = CircleShape
        ) {
            Box(contentAlignment = Alignment.Center) {
                AnimatedContent(
                    transitionSpec = {
                        fadeIn(animationSpec = tween(300)) with
                                fadeOut(animationSpec = tween(300))
                    },
                    targetState = startOrPause
                ) { target ->
                    Icon(
                        painter = painterResource(id = if (target) icon else R.drawable.pause),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        tint = Color.White
                    )
                }
            }
        }

        if (enableLongPress) {
            Canvas(modifier = Modifier.size(60.dp)) {
                drawArc(
                    color = Color.Red,
                    startAngle = -90f,
                    sweepAngle = animatedProgress * 360f,
                    useCenter = false,
                    style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
                )
            }
        }
    }
}



@Composable
fun RunningManAnimation(isMoving: Boolean, animation: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animation))

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isMoving
    )

    var moveRight by remember { mutableStateOf(true) }
    val offsetX = remember { Animatable(0f) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        val screenWidthPx = with(LocalDensity.current) { maxWidth.toPx() }
        val boxWidthPx = with(LocalDensity.current) { 200.dp.toPx() }

        LaunchedEffect(isMoving) {
            while (isMoving) {
                val target = if (moveRight) screenWidthPx - boxWidthPx else 0f
                offsetX.animateTo(
                    targetValue = target,
                    animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
                )
                moveRight = !moveRight
            }
        }

        if (composition != null) {
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier
                    .size(150.dp)
                    .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                    .graphicsLayer {
                        rotationY = if (moveRight) 0f else 180f
                    }
                    .padding(0.dp)
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
fun WorkoutScreenPreview() {

}