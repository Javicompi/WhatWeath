package es.jnsoft.whatweath.presentation.ui.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun LineChart(
    entries: List<ChartDataEntry>,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colors.onSurface
) {
    if (entries.isEmpty()) return
    Canvas(modifier = modifier) {
        val numberOfEntries = entries.size
        val maxEntryValue = entries.maxByOrNull { it.entryValue }?.entryValue ?: 0.0
        val minEntryValue = entries.minByOrNull { it.entryValue }?.entryValue ?: 0.0
        val lineDistance = size.width / numberOfEntries
        val canvasHeight = size.height
        val textPaint = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            textSize = 12.sp.toPx()
            textAlign = android.graphics.Paint.Align.CENTER
            color = contentColor.hashCode()
        }
        var currentXOffset = 0f
        val strokeWidth = 10.0f
        drawLine(
            start = Offset(
                x = currentXOffset,
                y = calculateYCoordinate(
                    minEntryValue,
                    maxEntryValue,
                    entries.first().entryValue,
                    canvasHeight
                )
            ),
            end = Offset(
                x = currentXOffset + (lineDistance / 2),
                y = calculateYCoordinate(
                    minEntryValue,
                    maxEntryValue,
                    entries.first().entryValue,
                    canvasHeight
                )
            ),
            color = contentColor,
            strokeWidth = strokeWidth
        )
        currentXOffset += lineDistance / 2
        entries.forEachIndexed { index, entry ->
            if (index <= entries.size - 2) {
                drawLine(
                    start = Offset(
                        x = currentXOffset,
                        y = calculateYCoordinate(
                            minEntryValue,
                            maxEntryValue,
                            entry.entryValue,
                            canvasHeight
                        )
                    ),
                    end = Offset(
                        x = currentXOffset + lineDistance,
                        y = calculateYCoordinate(
                            minEntryValue,
                            maxEntryValue,
                            entries[index + 1].entryValue,
                            canvasHeight
                        )
                    ),
                    color = contentColor,
                    strokeWidth = strokeWidth
                )
            }
            drawContext.canvas.nativeCanvas.drawText(
                entry.entryTitle,
                currentXOffset,
                calculateYCoordinate(
                    minEntryValue,
                    maxEntryValue,
                    entry.entryValue,
                    canvasHeight
                ) - size.height * 0.15f,
                textPaint
            )
            currentXOffset += lineDistance
        }
        drawLine(
            start = Offset(
                x = currentXOffset - lineDistance,
                y = calculateYCoordinate(
                    minEntryValue,
                    maxEntryValue,
                    entries.last().entryValue,
                    canvasHeight
                )
            ),
            end = Offset(
                x = currentXOffset + (lineDistance / 2),
                y = calculateYCoordinate(
                    minEntryValue,
                    maxEntryValue,
                    entries.last().entryValue,
                    canvasHeight
                )
            ),
            color = contentColor,
            strokeWidth = strokeWidth
        )
    }
}

@Preview(widthDp = 320, heightDp = 80)
@Composable
fun PreviewLineChart() {
    val entryList = listOf(
        ChartDataEntry(-2.0, "-2º"),
        ChartDataEntry(5.0, "5º"),
        ChartDataEntry(12.0, "12º"),
        ChartDataEntry(15.0, "15º"),
        ChartDataEntry(14.0, "14º"),
        ChartDataEntry(16.0, "16º"),
        ChartDataEntry(11.0, "11º")
    )
    LineChart(
        entries = entryList,
        contentColor = Color.Black,
        modifier = Modifier
            .fillMaxSize()
    )
}

private fun calculateYCoordinate(
    minvalue: Double,
    maxValue: Double,
    currentEntryValue: Double,
    canvasHeight: Float
): Float {
    val differenceMinMax = (maxValue - minvalue).toFloat()
    val relativePercentage = (canvasHeight / differenceMinMax) * 0.7f
    val relativeValue = (currentEntryValue - minvalue).toFloat()
    return canvasHeight - (relativePercentage * relativeValue)
}