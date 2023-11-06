package com.globa.ntalarmtestapp.photos

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.globa.ntalarmtestapp.common.theme.NTAlarmTestAppTheme
import com.globa.ntalarmtestapp.common.ui.Paddings
import com.globa.ntalarmtestapp.common.ui.composable.BaseHeader
import com.globa.ntalarmtestapp.common.ui.composable.DateField
import com.globa.ntalarmtestapp.common.util.DateFormatter
import com.globa.ntalarmtestapp.photos.api.Photo
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoListScreen(
    viewModel: PhotosViewModel = hiltViewModel(),
    onPhotoClick: (Int) -> Unit
) {
    val uiState = viewModel.photosUiState.collectAsState()

    val onDateChanged = fun(date: Long?) {
        viewModel.onDateChanged(date)
    }
    val datePickerState = rememberDatePickerState()

    Scaffold(
        topBar = {
            BaseHeader(
                text = "Photos"
            )
        }
    ) {
        when (val state = uiState.value) {
            is PhotosUiState.Loading -> {
                CircularProgressIndicator()
            }
            is PhotosUiState.Error -> {
                ErrorPhotoListScreen(
                    modifier = Modifier.padding(it),
                    errorMessage = state.message,
                    onReturnButtonClick = { viewModel.viewModelInit() }
                )
            }
            is PhotosUiState.Done -> {
                val photos = state.photos
                datePickerState.setSelection(state.date)
                DonePhotoListScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    photos = photos,
                    datePickerState = datePickerState,
                    onPhotoClick = onPhotoClick,
                    onDateChanged = onDateChanged
                )
            }
        }
    }
}

@Composable
private fun ErrorPhotoListScreen(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onReturnButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = errorMessage
        )
        Button(onClick = { onReturnButtonClick() }) {
            Text(text = "Return")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DonePhotoListScreen(
    modifier: Modifier = Modifier,
    photos: List<Photo>,
    datePickerState: DatePickerState,
    onPhotoClick: (Int) -> Unit,
    onDateChanged: (Long?) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        DateField(
            datePickerState = datePickerState,
            onDateChanged = onDateChanged
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.padding(Paddings.medium)
        ) {
            items(photos.size) { index ->
                val photo = photos[index]
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .size(100.dp)
                            .padding(
                                start = Paddings.medium,
                                end = Paddings.medium,
                                top = Paddings.medium
                            )
                            .clickable {
                                onPhotoClick(photo.id)
                            }
                            .clip(MaterialTheme.shapes.medium),
                        model = photo.path,
                        loading = { CircularProgressIndicator() },
                        error = {
                            Image(
                                painter = painterResource(id = com.globa.ntalarmtestapp.common.R.drawable.ic_broken),
                                contentDescription = "Image broken or not found"
                            )
                        },
                        contentDescription = "Photo ${photo.id}"
                    )
                    Text(text = DateFormatter.getSimpleDate(photo.date))
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PhotoListScreenDonePreview() {
    val list = listOf(
        Photo(
            id = 1,
            path = "logo.png",
            date = 1262307723
        ),
        Photo(
            id = 2,
            path = "logo.png",
            date = 1262307723
        ),
        Photo(
            id = 3,
            path = "logo.png",
            date = 1262307723
        ),
        Photo(
            id = 4,
            path = "logo.png",
            date = 1262307723
        ),
        Photo(
            id = 5,
            path = "logo.png",
            date = 1262307723
        ),
    )

    NTAlarmTestAppTheme {
        Surface {
            DonePhotoListScreen(
                photos = list,
                onPhotoClick = {},
                datePickerState = rememberDatePickerState(initialSelectedDateMillis = Date().time),
                onDateChanged = {}
            )
        }
    }
}