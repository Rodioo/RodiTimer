package navigation

sealed class Screen {
    object Timer : Screen()
    object Settings : Screen()
}