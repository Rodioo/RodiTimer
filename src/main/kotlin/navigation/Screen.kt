package navigation

sealed class Screen {
    object Home : Screen()
    object Timer : Screen()
    object Alarm : Screen()
    object Tags : Screen()
    object Settings : Screen()
    object About : Screen()
}