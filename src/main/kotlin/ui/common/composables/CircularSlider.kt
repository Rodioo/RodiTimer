package ui.common.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.sp
import ui.common.resources.MAIN_SLIDER_COLOR
import ui.tags.models.Tag
import java.awt.Cursor

private const val CIRCLE_THICKNESS_DIVIDER = 30f

//TODO: maybe refactor in the future so that it takes a tag argument instead of label and primary color
//TODO: this needs to be tested because what would happen if the timer is currently short break or long break
//TODO: potential solutions are either to add the breaks as tags or add some ternary operators
//TODO: add so that you can only click label if the timer is paused/didnt start
@Composable
fun CircularSlider(
    tag: Tag?,
    secondaryColor: Color,
    backgroundColor: Color,
    circleRadius: Dp,
    value: Long,
    maxValue: Long,
    formattedValue: String,
    onClickLabel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    Box(
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { tapOffset ->

                        }
                    )
                }
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
                color = tag?.color ?: MAIN_SLIDER_COLOR,
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
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize().align(Alignment.Center)
        ) {
            Text(
                text = formattedValue,
                style = TextStyle(
                    fontSize = 48.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    letterSpacing = 4.sp,
                ),
                textAlign = TextAlign.Center,
                maxLines = 1,
            )

            Text(
                text = tag?.label ?: "No tag",
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    letterSpacing = 2.sp,
                ),
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier
                    .pointerHoverIcon(
                        PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR))
                    )
                    .clickable {
                        onClickLabel()
                    }
            )
        }
    }
}
