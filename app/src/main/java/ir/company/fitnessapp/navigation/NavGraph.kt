package ir.company.fitnessapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ir.company.fitnessapp.navigation.Screen.Finish.routeWithArgs
import ir.company.fitnessapp.screens.FinishWorkOutScreen
import ir.company.fitnessapp.screens.HistoryScreen
import ir.company.fitnessapp.screens.HomeScreen
import ir.company.fitnessapp.screens.SelectScreen
import ir.company.fitnessapp.screens.WorkoutScreen

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            Screen.Workout.routeWithArgs("0","1"),
            arguments = listOf(
                navArgument("0") { type = NavType.StringType },
                navArgument("1") { type = NavType.StringType },
                )
        ) {
            val arg0 = it.arguments?.getString("0") ?: ""
            val arg1 = it.arguments?.getString("1") ?: ""
            WorkoutScreen(navController, arg0,arg1)
        }

            composable(Screen.Home.route) { HomeScreen(navController) }
//        composable(
//            Screen.History.routeWithArgs("0","1","2","3"),
//            arguments = listOf(
//                navArgument("0") {type = NavType.StringType},
//                navArgument("1") {type = NavType.StringType},
//                navArgument("2") {type = NavType.StringType},
//                navArgument("3") {type = NavType.StringType},
//            )
//        )
//        {
//            val arg0 = it.arguments?.getString("0") ?: ""
//            val arg1 = it.arguments?.getString("1") ?: ""
//            val arg2 = it.arguments?.getString("2") ?: ""
//            val arg3 = it.arguments?.getString("3") ?: ""
//            HistoryScreen(
//                navController,
//                arg0,
//                arg1,
//                arg2,
//                arg3
//            )
//        }
        composable(Screen.History.route) {
            HistoryScreen(navController)
        }
        composable(Screen.Select.route) { SelectScreen(navController) }
        composable(
            Screen.Finish.routeWithArgs("0", "1","2"),
            arguments = listOf(
                navArgument("0") { type = NavType.StringType },
                navArgument("1") { type = NavType.StringType },
                navArgument("2") { type = NavType.StringType },
                )
        ) {
            val arg0 = it.arguments?.getString("0") ?: ""
            val arg1 = it.arguments?.getString("1") ?: ""
            val arg2 = it.arguments?.getString("2") ?: ""
            FinishWorkOutScreen(navController, arg0, arg1,arg2)
        }
    }
}
