package timer.composables.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.material.SliderColors
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TimerSlider(
    label: String,
    value: Long,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    formatValueFunction: Long.() -> String = Long::toString,
    sliderColors: SliderColors = SliderDefaults.colors(),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp)
    ) {

        Text(
            text = label,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = value.formatValueFunction(),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it) },
            valueRange = valueRange,
            colors = sliderColors
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}