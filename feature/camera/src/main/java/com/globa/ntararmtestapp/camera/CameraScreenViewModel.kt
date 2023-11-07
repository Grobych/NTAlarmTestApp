package com.globa.ntararmtestapp.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globa.ntalarmtestapp.common.util.DateFormatter
import com.globa.ntalarmtestapp.common.util.UriDataStore
import com.globa.ntalarmtestapp.location.api.LocationRepository
import com.globa.ntalarmtestapp.location.api.LocationResponse
import com.globa.ntalarmtestapp.photodetails.api.PhotoDetails
import com.globa.ntalarmtestapp.photodetails.api.PhotoDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class CameraScreenViewModel @Inject constructor(
    private val photosRepository: PhotoDetailsRepository,
    private val locationRepository: LocationRepository,
    private val uriDataStore: UriDataStore
): ViewModel() {
    private val _uiState = MutableStateFlow<CameraScreenUiState>(CameraScreenUiState.Creating)
    val uiState = _uiState.asStateFlow()

    private val _image = MutableStateFlow<Bitmap?>(null)
    private val _location = MutableStateFlow<Location?>(null)
    private val date = Date().time/1000
    private val _imageName = MutableStateFlow("IMG${DateFormatter.getSimpleDate(date)}")
    private val _path = MutableStateFlow<Uri?>(null)


    init {
        imageNameFlowInit()
        pathFlowInit()
    }
    fun onPhotoCaptured(byteArray: ByteArray) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                _uiState.update { CameraScreenUiState.Processing("Processing image...") }
                val bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
                _uiState.update { CameraScreenUiState.Processing("Getting location data...") }
                when (val response = locationRepository.getLocation()) {
                    is LocationResponse.Error -> _uiState.update { CameraScreenUiState.Error("Getting location error: ${response.message}") }
                    is LocationResponse.Success -> _uiState.update {
                        _location.value = response.location
                        _image.value = bitmap
                        CameraScreenUiState.ReadyToSave(
                            path = _path.value,
                            bitmap = _image.value!!,
                            location = "Lat: ${response.location.latitude}; Lng: ${response.location.longitude}",
                            name = _imageName.value,
                            date = DateFormatter.getExtendDate(date)
                        )
                    }
                }
            }
        }
    }
    fun onSaveButtonClick(context: Context) {
        val uri = _path.value ?: return
        if (uiState.value is CameraScreenUiState.ReadyToSave) {
            viewModelScope.launch {
                val image = _image.value
                image?.let {
                    try {
                        val df = DocumentFile.fromTreeUri(context,uri)?: return@launch
                        val file = df.createFile("image/jpeg",_imageName.value)
                        val fileUri = file!!.uri
                        val out = context.contentResolver.openOutputStream(fileUri)
                        if (out != null) {
                            it.compress(Bitmap.CompressFormat.JPEG, 90, out)
                            out.flush()
                            out.close()
                        } else _uiState.value = CameraScreenUiState.Error(message = "Error during saving file!")

                        _location.value?.let {location ->
                            photosRepository.savePhoto(
                                PhotoDetails(
                                    path = fileUri.toString(),
                                    title = _imageName.value,
                                    date = date,
                                    location = com.globa.ntalarmtestapp.photodetails.api.Location(
                                        latitude = location.latitude,
                                        longitude = location.longitude
                                    )
                                )
                            )
                        }
                        _uiState.value = CameraScreenUiState.Done
                    } catch (e: Exception) {
                        _uiState.value = CameraScreenUiState.Error(message = e.localizedMessage?: "Unknown Error!")
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    fun onImageNameChange(name: String) {
        _imageName.value = name
    }

    fun onPathChange(path: Uri) {
        viewModelScope.launch {
            uriDataStore.saveUri(path)
        }
    }

    private fun imageNameFlowInit() {
        _imageName.onEach { imageName ->
            val state = uiState.value
            if (state is CameraScreenUiState.ReadyToSave) {
                _uiState.value = state.copy(
                    name = imageName
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun pathFlowInit() {
        uriDataStore.getUri().onEach {
            _path.value = it
        }.launchIn(viewModelScope)

        _path.onEach { path ->
            val state = uiState.value
            if (state is CameraScreenUiState.ReadyToSave) {
                _uiState.value = state.copy(
                    path = path
                )
            }
        }.launchIn(viewModelScope)
    }
}