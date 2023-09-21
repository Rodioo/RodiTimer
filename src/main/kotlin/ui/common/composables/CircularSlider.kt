package ui.common.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

private const val CIRCLE_THICKNESS_DIVIDER = 30f

@OptIn(ExperimentalTextApi::class)
@Composable
fun CircularSlider(
    primaryColor: Color,
    secondaryColor: Color,
    backgroundColor: Color,
    circleRadius: Float,
    value: Long,
    maxValue: Long,
    formattedValue: String,
    modifier: Modifier = Modifier,
) {
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    val timerTextMeasurer = rememberTextMeasurer()
    val topicTextMeasurer = rememberTextMeasurer()

    Box(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val width = size.width
            val height = size.height
            val circleThickness = width / CIRCLE_THICKNESS_DIVIDER
            circleCenter = Offset(
                x = width / 2f,
                y = height / 2f
            )

            drawCircle(
                color = backgroundColor,
                radius = circleRadius,
                center = circleCenter,
            )

            drawCircle(
                style = Stroke(width = circleThickness / 4f),
                color = secondaryColor,
                radius = circleRadius,
                center = circleCenter
            )

            drawArc(
                color = primaryColor,
                startAngle = 270f,
                sweepAngle = (360f / maxValue) * value.toFloat(),
                style = Stroke(
                    width = circleThickness,
                    cap = StrokeCap.Round
                ),
                useCenter = false,
                size = Size(
                    width = circleRadius * 2f,
                    height = circleRadius * 2f
                ),
                topLeft = Offset(
                    x = (width - circleRadius * 2f) / 2f,
                    y = (height - circleRadius * 2f) / 2f
                )
            )

            drawText(
                textMeasurer = timerTextMeasurer,
                text = formattedValue,
                style = TextStyle(
                    fontSize = (circleRadius / 3.5f).sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    letterSpacing = 4.sp,
                ),
                size = Size(
                  width =  circleRadius * 2f,
                  height = circleRadius
                ),
                maxLines = 1,
                topLeft = Offset(
                    width / 2f - circleRadius,
                    height / 2f - circleRadius / 2f
                ),
            )

            drawText(
                textMeasurer = topicTextMeasurer,
                text = "Entertainment",
                style = TextStyle(
                    fontSize = (circleRadius / 7f).sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    letterSpacing = 2.sp,
                ),
                size = Size(
                    width =  circleRadius * 2f,
                    height = circleRadius
                ),
                maxLines = 1,
                topLeft = Offset(
                    width / 2f - circleRadius,
                    height / 2f + circleRadius / 4f
                ),
            )
        }
    }
}
