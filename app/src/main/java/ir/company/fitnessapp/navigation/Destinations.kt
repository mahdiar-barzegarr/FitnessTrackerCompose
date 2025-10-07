package ir.company.fitnessapp.navigation

sealed class Screen(val route: String) {
    object Workout : Screen("workout")
    object History : Screen("history")
    object Select : Screen("select")
    object Finish : Screen("finish")
    object Home : Screen("home")

    fun Screen.routeWithArgs(vararg args: String) =
        buildString {
            append(route)
            args.forEachIndexed { index, _ ->
                append(if (index == 0) "?" else "&")
                append("$index={$index}")
            }
        }

    fun Screen.paramsWithArgs(vararg args: String) =
        buildString {
            append(route)
            args.forEachIndexed { index, string ->
                append(if (index == 0) "?" else "&")
                append("$index=$string")
            }
        }
}
