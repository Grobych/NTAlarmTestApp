package com.globa.ntalarmtestapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.globa.ntalarmtestapp.common.ui.composable.Footer
import com.globa.ntalarmtestapp.details.PhotoDetailsScreen
import com.globa.ntalarmtestapp.map.MapScreen
import com.globa.ntalarmtestapp.photos.PhotoListScreen
import com.globa.ntararmtestapp.camera.CameraScreen

@Composable
fun NavController(
    viewModel: NavigationViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val onFooterItemClick = fun(i: Int) {
        viewModel.onSelectorItemClick(i)
    }
    val selected = viewModel.selectedItem.collectAsState()

    val navigateToList = fun() {
        navController.navigate(Routes.PhotoList.name)
    }
    val navigateToCamera = fun() {
        navController.navigate(Routes.AddPhoto.name)
    }
    val navigateToMap = fun() {
        navController.navigate(Routes.Map.name)
    }
    val navigateToDetails = fun(id: Int) {
        navController.navigate(Routes.PhotoDetails.name + "?photoId=$id")
    }
    val navigateToBack = fun() {
        navController.popBackStack()
    }

    Scaffold(
        bottomBar = {
            Footer(
                onPhotoListClick = {
                    onFooterItemClick(1)
                    navigateToList() },
                onMapClick = {
                    onFooterItemClick(3)
                    navigateToMap() },
                onCameraClick = {
                    onFooterItemClick(2)
                    navigateToCamera() },
                selected = selected.value
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.PhotoList.name,
            modifier = Modifier.padding(it)
        ) {
            composable(
                route = Routes.PhotoList.name
            ) {
                PhotoListScreen(
                    onPhotoClick = navigateToDetails,
                    onMapIconClick = navigateToMap,
                    onCameraButtonClick = navigateToCamera
                )
            }
            composable(
                route = "${Routes.PhotoDetails}?photoId={photoId}",
                arguments = listOf(navArgument("photoId") { type = NavType.IntType })
            ) {
                PhotoDetailsScreen(
                    onBackButtonClick = navigateToBack
                )
            }
            composable(
                route = Routes.AddPhoto.name
            ) {
                CameraScreen(
                    onBackButtonClick = navigateToBack
                )
            }
            composable(
                route = "${Routes.ChooseFolder.name}?=currentPath={currentPath}", // use default/saved???
                arguments = listOf(navArgument("currentPath") { type = NavType.StringType })
            ) {
                // Choose Folder Screen
            }
            composable(
                route = Routes.Map.name
            ) {
                MapScreen(onMarkerCLick = navigateToDetails)
            }
        }
    }


}

enum class Routes {
    PhotoList, AddPhoto, ChooseFolder, Map, PhotoDetails
}