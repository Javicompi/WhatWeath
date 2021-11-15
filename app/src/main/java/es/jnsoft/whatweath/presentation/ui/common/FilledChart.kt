package es.jnsoft.whatweath.presentation.ui.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun FilledChart(
    entries: List<ChartDataEntry>,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colors.onSurface
) {
    if (entries.isEmpty()) return
    Canvas(modifier = modifier) {
        val numberOfEntries = entries.size
        val maxEntryValue = entries.maxByOrNull { it.entryValue }?.entryValue ?: 0.0
        val lineDistance = size.width / numberOfEntries
        val canvasHeight = size.height
        val textPaint = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            textSize = 12.sp.toPx()
            textAlign = android.graphics.Paint.Align.CENTER
            color = contentColor.hashCode()
        }
        var currentXOffset = 0f
        var currentYOffset = calculateYCoordinate(
            maxEntryValue,
            entries[0].entryValue,
            canvasHeight
        )
        val path = Path()
        path.moveTo(
            x = currentXOffset,
            y = currentYOffset
        )
        path.lineTo(
            x = lineDistance / 2,
            y = currentYOffset
        )
        currentXOffset += lineDistance / 2
        entries.forEach { entry ->
            val newYOffset = calculateYCoordinate(
                maxEntryValue,
                entry.entryValue,
                canvasHeight
            )
            path.cubicTo(
                x1 = currentXOffset - (lineDistance * 0.5f),
                y1 = currentYOffset,
                x2 = currentXOffset - (lineDistance * 0.5f),
                y2 = newYOffset,
                x3 = currentXOffset,
                y3 = newYOffset
            )
            drawContext.canvas.nativeCanvas.drawText(
                entry.entryTitle,
                currentXOffset,
                newYOffset - (size.height * 0.15f),
                textPaint
            )
            currentXOffset += lineDistance
            currentYOffset = newYOffset
        }
        path.lineTo(
            x = currentXOffset + (lineDistance / 2),
            y = calculateYCoordinate(
                maxEntryValue,
                entries[numberOfEntries - 1].entryValue,
                canvasHeight
            )
        )
        path.lineTo(
            x = size.width,
            y = size.height
        )
        path.lineTo(
            x = 0f,
            y = size.height
        )
        path.close()
        drawPath(
            path = path,
            color = contentColor.copy(alpha = 0.3f)
        )
    }
}

@Preview(widthDp = 320, heightDp = 80)
@Composable
fun PreviewFilledChart() {
    val entryList = listOf(
        ChartDataEntry(0.0, "0º"),
        ChartDataEntry(5.0, "5º"),
        ChartDataEntry(12.0, "12º"),
        ChartDataEntry(15.0, "15º"),
        ChartDataEntry(14.0, "14º"),
        ChartDataEntry(16.0, "16º"),
        ChartDataEntry(11.0, "11º")
    )
    FilledChart(
        entries = entryList,
        contentColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
        modifier = Modifier
            .fillMaxSize()
    )
}

private fun calculateYCoordinate(
    maxValue: Double,
    currentEntryValue: Double,
    canvasHeight: Float
): Float {
    val relativePercentage = (canvasHeight / maxValue) * 0.7f
    val yCoordinate = canvasHeight - (currentEntryValue.toFloat() * relativePercentage.toFloat())
    return if (yCoordinate.isNaN()) canvasHeight else yCoordinate
}