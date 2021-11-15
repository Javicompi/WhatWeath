package es.jnsoft.whatweath.presentation.ui.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun BarChart(
    entries: List<ChartDataEntry>,
    modifier: Modifier,
    contentColor: Color = MaterialTheme.colors.onSurface
) {
    if (entries.isEmpty()) return
    Canvas(modifier = modifier) {
        val numberOfEntries = entries.size
        val maxEntryValue = entries.maxByOrNull { it.entryValue }?.entryValue ?: 0.0
        val barWidth = ((size.width / numberOfEntries) * 0.8).toFloat()
        val barPadding = ((size.width / numberOfEntries) * 0.1).toFloat()
        val canvasWidth = size.width / numberOfEntries
        val canvasHeight = size.height
        val textPaint = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            textSize = 12.sp.toPx()
            textAlign = android.graphics.Paint.Align.CENTER
            color = contentColor.hashCode()
        }
        var currentXOffset = 0f
        entries.forEach { entry ->
            val currentYOffset = calculateYCoordinate(
                maxValue = maxEntryValue,
                currentEntryValue = entry.entryValue,
                canvasHeight = canvasHeight
            )
            drawRect(
                color = contentColor,
                topLeft = Offset(
                    x = currentXOffset + barPadding,
                    y = currentYOffset
                ),
                size = Size(
                    width = barWidth,
                    height = canvasHeight - currentYOffset
                )
            )
            drawContext.canvas.nativeCanvas.drawText(
                entry.entryTitle,
                currentXOffset + canvasWidth / 2,
                calculateYCoordinate(
                    maxValue = maxEntryValue,
                    currentEntryValue = entry.entryValue,
                    canvasHeight = canvasHeight
                ) - size.height * 0.15f,
                textPaint
            )
            currentXOffset += canvasWidth
        }
    }
}

@Preview(widthDp = 320, heightDp = 80)
@Composable
fun PreviewBarChart() {
    val entryList = listOf(
        ChartDataEntry(1.0, "1 kmh"),
        ChartDataEntry(5.0, "5 kmh"),
        ChartDataEntry(2.0, "2 kmn"),
        ChartDataEntry(0.0, "0 kmh"),
        ChartDataEntry(0.0, "0 kmh"),
        ChartDataEntry(2.0, "2 kmh"),
        ChartDataEntry(3.0, "3 kmh")
    )
    BarChart(
        entries = entryList,
        modifier = Modifier.fillMaxSize(),
        contentColor = MaterialTheme.colors.onSurface
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