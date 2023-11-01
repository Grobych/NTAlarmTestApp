package com.globa.ntalarmtestapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun NavController() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.PhotoList.name) {
        composable(
            route = Routes.PhotoList.name
        ) {
            //Photo list Screen
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
            // Map Screen
        }
    }
}

enum class Routes {
    PhotoList, AddPhoto, ChooseFolder, Map, PhotoDetails
}