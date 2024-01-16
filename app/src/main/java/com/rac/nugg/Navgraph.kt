package com.rac.nugg

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun navgraph(){
    val navController = rememberNavController()
    val sharedViewModel = remember { SharedViewModel() }

    NavHost(navController = navController, startDestination = "A" ){

        composable(route="A"){

            Ascreen(navController,sharedViewModel)

        }

        composable("B"){
            Bscreen(sharedViewModel)
        }



    }
}