package es.jnsoft.whatweath.presentation.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.jnsoft.domain.model.Result
import es.jnsoft.whatweath.R
import es.jnsoft.whatweath.presentation.model.CurrentPresentation
import es.jnsoft.whatweath.presentation.model.HourlyPresentation
import es.jnsoft.whatweath.utils.DrawableLoader
import es.jnsoft.whatweath.utils.DrawableType
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun CurrentContent(
    scaffoldState: BottomSheetScaffoldState,
    currentValue: Result<CurrentPresentation>,
    hourliesValue: Result<List<HourlyPresentation>>,
    modifier: Modifier = Modifier
) {
    val currentResult = if (currentValue is Result.Success) currentValue.value else null
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            CurrentBottomSheet(
                current = currentResult,
                hourlies = hourliesValue
            )
        },
        sheetPeekHeight = if (currentValue is Result.Success) {
            dimensionResource(id = R.dimen.bottom_sheet_peek_height)
        } else {
            0.dp
        },
        sheetElevation = 0.dp,
        sheetShape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        modifier = modifier
    ) {
        CurrentBackDrop(
            currentResult = currentValue,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun CurrentBackDrop(
    currentResult: Result<CurrentPresentation?>,
    modifier: Modifier = Modifier
) {
    when (currentResult) {
        is Result.Loading -> {
            CurrentProgressIndicator(
                modifier = modifier
            )
        }
        is Result.Success -> {
            CurrentBackDropBackground(
                current = currentResult.value,
                modifier = modifier
            )
        }
        else -> {
            CurrentEmptyMessage(
                modifier = modifier
            )
        }
    }
}

@Composable
private fun CurrentBackDropBackground(
    current: CurrentPresentation?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Surface {
        Box(modifier = modifier) {
            Image(
                painter = painterResource(
                    id = DrawableLoader.loadDrawable(
                        current?.icon ?: "01d",
                        DrawableType.FULLSCREEN,
                        context
                    )
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = (current?.temp ?: "¿? º"),
                textAlign = TextAlign.End,
                fontSize = 48.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(top = 72.dp, end = 24.dp)
                    .fillMaxWidth()
            )
            Text(
                text = (current?.description ?: "Unknown description"),
                fontSize = 32.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(
                        bottom = dimensionResource(id = R.dimen.bottom_sheet_peek_height) + 24.dp,
                        start = 24.dp
                    )
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
            )
        }
    }
}

@Composable
private fun CurrentEmptyMessage(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(start = 24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = (stringResource(id = R.string.search_message_empty_title)),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = (stringResource(id = R.string.search_message_empty_message)),
            fontSize = 18.sp,
            fontWeight = FontWeight.Light
        )
    }
}

@Composable
private fun CurrentProgressIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}