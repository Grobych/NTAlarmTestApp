package com.globa.ntalarmtestapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.globa.ntalarmtestapp.details.PhotoDetailsScreen
import com.globa.ntalarmtestapp.map.MapScreen
import com.globa.ntalarmtestapp.photos.PhotoListScreen
import com.globa.ntararmtestapp.camera.CameraScreen

@Composable
fun NavController() {
    val navController = rememberNavController()

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

    NavHost(
        navController = navController,
        startDestination = Routes.PhotoList.name
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
            route = Routes.Map.name
        ) {
            MapScreen(onMarkerCLick = navigateToDetails)
        }
    }
}

enum class Routes {
    PhotoList, AddPhoto, Map, PhotoDetails
}