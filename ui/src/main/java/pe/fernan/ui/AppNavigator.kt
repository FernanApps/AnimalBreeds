package pe.fernan.ui

import android.os.Bundle
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pe.fernan.ui.breeds.BreedsScreen
import pe.fernan.ui.favorites.FavoriteCountBadge
import pe.fernan.ui.favorites.FavoritesScreen
import pe.fernan.ui.images.ImagesScreen
import pe.fernan.ui.selector.SelectorScreen
import pe.fernan.ui.splash.SplashScreen
import pe.fernan.ui.theme.DogBreedsTheme


val ANIMAL_IMAGES_ARGUMENT_KEY = "breed"
val ANIMAL_TYPE_ARGUMENT_KEY = "animal"

sealed class Screen(
    private val baseRoute: String,
    val argKeys: List<String> = emptyList(),
) {
    // Easy by FernanApps
    private val separator = "/"
    private val stringFormat = "%s"
    private val keysFormat = "{$stringFormat}"

    val routeFormat: String
        get() = if (argKeys.isEmpty()) throw Exception("The format does not exist since there are no ArgKeys") else {
            val builder = argKeys.joinToString(
                prefix = separator,
                separator = separator
            ) { stringFormat }
            baseRoute + builder
        }

    val route: String
        get() = if (argKeys.isEmpty()) baseRoute else {
            val builder = argKeys.joinToString(
                prefix = separator,
                separator = separator
            ) { keysFormat.format(it) }
            baseRoute + builder
        }

    private var arguments: Bundle? = null
    fun setArguments(arguments: Bundle?): Screen {
        this.arguments = arguments
        return this
    }

    open fun getArgumentValue(argKey: String): String? {
        return arguments?.getString(argKey)
    }

    //private fun parser(vararg path: String): String = Uri.encode(Gson().toJson(path.toList()))

    fun passValue(paths: List<String>) = routeFormat.format(*paths.toTypedArray())
    fun passValue(vararg paths: String) = routeFormat.format(*paths)

    /*
    fun get(arguments: Bundle?): List<Path>? {
        val pathArguments = arguments?.getString(PATHS_ARGUMENT_KEY) ?: return null
        val listType = object : TypeToken<List<Path>>() {}.type
        return Gson().fromJson(pathArguments, listType)

    }
    */

    data object Splash : Screen("splash")
    data object Selector : Screen("selector")
    data object BreedsList : Screen("breedsList", argKeys = listOf(ANIMAL_TYPE_ARGUMENT_KEY))
    data object Favorites : Screen("favorites")
    data object AnimalImages : Screen(baseRoute = "animalImages", argKeys = listOf(ANIMAL_IMAGES_ARGUMENT_KEY))

}


/*
composable(
                    route = Screen.Details.route,
                    arguments = listOf(
                        navArgument(PRODUCTS_DETAILS_ARGUMENT_KEY) {
                            type = ProductNavType
                        }
                    )
                ) { backStackEntry ->
                    Screen.Details.get(backStackEntry.arguments)?.let { product ->
                        DetailsScreen(product, navController)
                    }
                }
 */

public fun NavGraphBuilder.composable(
    screen: Screen,
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
        enterTransition,
    popExitTransition: (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
        exitTransition,
    //content: @Composable AnimatedContentScope.(NavBackStackEntry, screen: Screen) -> Unit = { _, _ -> }
    content: @Composable AnimatedContentScope.(screen: Screen) -> Unit = { _ -> }

) {
    composable(
        route = screen.route,
        screen.argKeys.map { argKey -> navArgument(argKey) { type = NavType.StringType } },
        deepLinks,
        enterTransition,
        exitTransition,
        popEnterTransition,
        popExitTransition,
    ) { backStackEntry ->
        content(screen.setArguments(backStackEntry.arguments))
    }
}


@Composable
fun MyNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        composable(screen = Screen.Splash) {
            SplashScreen(navController = navController)
        }

        composable(screen = Screen.Selector) {
            SelectorScreen(navController = navController)
        }

        composable(screen = Screen.BreedsList) {
            BreedsScreen(navController = navController)
        }

        /**
         * A Composable function responsible for navigating to and displaying images of a dog breed or sub-breed.
         *
         * The navigation argument 'breed' should be passed in the format "breed" or "breed_subBreed".
         * For example, to view images of the Bulldog breed, the argument should be "bulldog".
         * To view images of the Australian Shepherd sub-breed, the argument should be "shepherd_australian".
         *
         */
        composable(
            screen = Screen.AnimalImages
        ) {  screen ->
            val breedArg = screen.getArgumentValue(ANIMAL_IMAGES_ARGUMENT_KEY)
            if (breedArg != null) {
                // Split the 'breedArg' to separate the breed and sub-breed, if applicable.
                val (breed, subBreed) = breedArg.split('_').let {
                    it[0] to it.getOrNull(1)
                }

                ImagesScreen(
                    navController = navController,
                    breed = breed,
                    subBreed = subBreed
                )
            }
        }

        composable(route = Screen.Favorites.route) {
            FavoritesScreen(navController)
        }
    }
}

@Composable
fun AppNavigator() {
    val navController: NavHostController = rememberNavController()

    DogBreedsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            MainAppScreen(navController = navController)
        }
    }
}


@Composable
fun MainAppScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomNavigation(navController)


        }
    ) {
        Box(Modifier.padding(it)) {
            MyNavHost(navController)
        }
    }
}


val bottomNavItems = listOf(
    Screen.BreedsList,
    Screen.AnimalImages,
    Screen.BreedsList,
    Screen.Favorites
)
@Composable
fun BottomNavigation(navController: NavHostController){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    if (currentDestination?.route in bottomNavItems.map { item -> item.route }
    ) {
        BottomAppBar {
            NavigationBar {
                val currentRoute = currentDestination?.route
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.AutoMirrored.Filled.List,
                            contentDescription = null
                        )
                    },
                    label = { Text("Breeds") },
                    selected = currentRoute == Screen.BreedsList.route,
                    onClick = {
                        navController.navigate(Screen.BreedsList.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

                NavigationBarItem(
                    icon = {
                        FavoriteCountBadge()
                    },
                    label = { Text("Favorites") },
                    selected = currentRoute == Screen.Favorites.route,
                    onClick = {
                        navController.navigate(Screen.Favorites.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }


}

