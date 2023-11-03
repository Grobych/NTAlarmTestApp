package com.globa.ntalarmtestapp.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.globa.ntalarmtestapp.common.ui.composable.LocationPermissions
import com.globa.ntalarmtestapp.photolocations.api.PhotoLocation
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel(),
    onMarkerCLick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            //TODO: Header
        }
    ) {
        LocationPermissions {
            viewModel.fetchPhotoLocations()
            val state = viewModel.uiState.collectAsState()
            MapScreenContent(
                modifier = Modifier.padding(it),
                locations = state.value.photos,
                onMarkerCLick = onMarkerCLick
            )
        }
    }
}

@Composable
fun MapScreenContent(
    modifier: Modifier = Modifier,
    locations: List<PhotoLocation>,
    onMarkerCLick: (Int) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = MapProperties(isMyLocationEnabled = true),
        ) {
            locations.forEach {photoLocation ->
                Marker(
                    state = rememberMarkerState(position = LatLng(photoLocation.latitude,photoLocation.longitude)),
                    onClick = {
                        onMarkerCLick(photoLocation.id)
                        true
                    }
                )
            }
        }
    }
}