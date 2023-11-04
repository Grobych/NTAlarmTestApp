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
import com.globa.ntalarmtestapp.map.MapScreen
import com.globa.ntalarmtestapp.photos.PhotoListScreen

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
        navController.navigate(Routes.PhotoDetails.name + "photoId=$id")
        navController.popBackStack()
    }

    Scaffold(
        bottomBar = {
            Footer(
                onPhotoListClick = {
                    onFooterItemClick(1)
                    navigateToList() },
                onMapClick = {
                    onFooterItemClick(2)
                    navigateToMap() },
                onCameraClick = {
                    onFooterItemClick(3)
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
                PhotoListScreen(onPhotoClick = { TODO() })
            }
            composable(
                route = "${Routes.PhotoDetails}?photoId={photoId}",
                arguments = listOf(navArgument("photoId") { type = NavType.IntType })
            ) {
                //Photo Details Screen
            }
            composable(
                route = Routes.AddPhoto.name
            ) {
                // Add Photo Screen
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
                MapScreen(onMarkerCLick = { TODO() })
            }
        }
    }


}

enum class Routes {
    PhotoList, AddPhoto, ChooseFolder, Map, PhotoDetails
}