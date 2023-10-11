package pe.fernan.ui.selector

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pe.fernan.ui.R
import pe.fernan.ui.Screen
import pe.fernan.ui.composables.bounceClick
import pe.fernan.ui.theme.AnimalBreedsTheme
import pe.fernan.ui.theme.darkPurple


val keyCat = "cat"
val keyDog = "dog"
val keyChicken = "chicken"

val images = listOf(
    keyCat to R.drawable.opt_cat,
    keyDog to R.drawable.opt_dog,
    //keyChicken to R.drawable.opt_chicken
)

@Composable
fun SelectorScreen(navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(200.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(color = darkPurple),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ) {
        items(images) {
            Box(
                modifier = Modifier.bounceClick {
                    navController.navigate(Screen.BreedsList.route)
                },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(200.dp),
                    painter = painterResource(id = it.second),
                    contentDescription = it.first
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashSelectorScreen() {
    val navController: NavHostController = rememberNavController()

    AnimalBreedsTheme() {
        SelectorScreen(navController)
    }

}