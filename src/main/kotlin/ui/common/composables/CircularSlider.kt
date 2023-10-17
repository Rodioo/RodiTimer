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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.sp

private const val CIRCLE_THICKNESS_DIVIDER = 30f

//TODO: maybe refactor in the future so that it takes a tag argument instead of label and primary color
//TODO: this needs to be tested because what would happen if the timer is currently short break or long break
//TODO: potential solutions are either to add the breaks as tags or add some ternary operators
//TODO: check why on macbook the size is 700x700 and on monitor is 350x350 (more pixels probably check scaling)Exactly
@Composable
fun CircularSlider(
    primaryColor: Color,
    secondaryColor: Color,
    backgroundColor: Color,
    circleRadius: Dp,
    value: Long,
    maxValue: Long,
    formattedValue: String,
    label: String,
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
                radius = circleRadius.toPx(),
                center = circleCenter,
            )

            drawCircle(
                style = Stroke(width = circleThickness / 4f),
                color = secondaryColor,
                radius = circleRadius.toPx(),
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
                    width = circleRadius.toPx() * 2f,
                    height = circleRadius.toPx() * 2f
                ),
                topLeft = Offset(
                    x = (width - circleRadius.toPx() * 2f) / 2f,
                    y = (height - circleRadius.toPx() * 2f) / 2f
                )
            )

            drawText(
                textMeasurer = timerTextMeasurer,
                text = formattedValue,
                style = TextStyle(
                    fontSize = (height / circleRadius.toPx() * 24).sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    letterSpacing = 4.sp,
                ),
                size = Size(
                  width =  circleRadius.toPx() * 2f,
                  height = circleRadius.toPx()
                ),
                maxLines = 1,
                topLeft = Offset(
                    width / 2f - circleRadius.toPx(),
                    height / 2f - circleRadius.toPx() / 2f
                ),
            )

            drawText(
                textMeasurer = topicTextMeasurer,
                text = label,
                style = TextStyle(
                    fontSize = (height / circleRadius.toPx() * 24 / 2).sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    letterSpacing = 2.sp,
                ),
                size = Size(
                    width =  circleRadius.toPx() * 2f,
                    height = circleRadius.toPx()
                ),
                maxLines = 1,
                topLeft = Offset(
                    width / 2f - circleRadius.toPx(),
                    height / 2f + circleRadius.toPx() / 4f
                ),
            )
        }
    }
}
