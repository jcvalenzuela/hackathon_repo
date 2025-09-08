package com.example.truckwireframe

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.truckwireframe.ui.theme.TruckWireframeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TruckWireframeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TruckWireframe(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TruckWireframe(modifier: Modifier = Modifier, vehicleInfo: VehicleInfo = VehicleInfo()) {
    var pointerPosition by remember {
        mutableStateOf(arrayOf(0f, 0f))
    }

    Box(
        modifier = modifier
            .aspectRatio(2f)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Vehicle Image
        val res = painterResource(vehicleInfo.vehicleImage)
        Image(
            painter = res,
            contentDescription = "Vehicle Image",
            modifier = Modifier.fillMaxSize(),
            alignment = Alignment.Center
        )

        // Tire Pressure Error Pointer
        SetTirePressureErrorPointerImage(vehicleInfo, pointerPosition[0], pointerPosition[1], true)
    }
}

val Number.composeDp
    get() = Resources.getSystem().displayMetrics.run {
        val dp = widthPixels / density
        Dp(toFloat() * dp)
    }

@Composable
fun SetTirePressureErrorPointerImage(vehicleInfo: VehicleInfo, x: Float, y: Float, isError: Boolean) {
    val res = if (isError) {
        R.drawable.point_error
    } else {
        R.drawable.point_normal
    }

    val painter = painterResource(res)

    //Set animation for the pointer when there is an error
    if (isError) {
        //Blinking animation for the pointer
        val infiniteTransition = rememberInfiniteTransition(label = "pointer")
        val alpha by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 700),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pointer"
        )

        Image(
            painter = painter,
            contentDescription = "Tire Pressure Error Pointer",
            modifier = Modifier
                .size(1000.composeDp / vehicleInfo.senseWidth)
                .offset(x = x.composeDp / vehicleInfo.senseWidth, y = y.composeDp / vehicleInfo.senseWidth)
                .alpha(alpha),
            alignment = Alignment.Center
        )
    } else {
        //Non-blinking stable pointer
        Image(
            painter = painter,
            contentDescription = "Tire Pressure Error Pointer",
            modifier = Modifier
                .size(1000.composeDp / vehicleInfo.senseWidth)
                .offset(x = x.composeDp / vehicleInfo.senseWidth, y = y.composeDp / vehicleInfo.senseWidth),
            alignment = Alignment.Center
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TruckWireframeTheme {
        TruckWireframe()
    }
}

