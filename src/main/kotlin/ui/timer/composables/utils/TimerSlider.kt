package ui.timer.composables.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.material.SliderColors
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.common.resources.TEXT_COLOR
import ui.common.resources.TOP_BAR_BACKGROUND_COLOR

@Composable
fun TimerSlider(
    label: String,
    value: Long,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: (() -> Unit)? = null,
    valueRange: ClosedFloatingPointRange<Float>,
    formatValueFunction: Long.() -> String = Long::toString,
    colors: SliderColors = SliderDefaults.colors(),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {

        Text(
            text = label,
            color = Color.White.copy(alpha = 0.3f),
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value.formatValueFunction(),
            color = TEXT_COLOR,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(TOP_BAR_BACKGROUND_COLOR.copy(0.4f))
                .padding(8.dp)
        )

        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it) },
            onValueChangeFinished = onValueChangeFinished,
            valueRange = valueRange,
            colors = colors
        )
    }
}