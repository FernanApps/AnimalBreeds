@file:OptIn(ExperimentalMaterial3Api::class)

package pe.fernan.ui.breeds

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import pe.fernan.domain.breeds.BreedEntity
import pe.fernan.domain.common.capitalizeWords
import pe.fernan.ui.Screen
import pe.fernan.ui.common.UiStateWrapper
import pe.fernan.ui.common.UiState

@Composable
fun BreedsScreen(
    viewModel: BreedsViewModel = hiltViewModel(),
    navController: NavController
) {
    val breedListState by viewModel.breedsState.collectAsStateWithLifecycle()

    BreedsScreen(
        breedListState = breedListState,
        navigateToDogImages = { breedItem: BreedEntity ->
            Log.d("BreedsScreen", "breedItem $breedItem")
            navController.navigate(
                Screen.AnimalImages.passValues(
                    breedTitle = breedItem.name,
                    breedRoute = breedItem.route
                )
            )
            //navController.navigate(Screen.AnimalImages.passValue(breedItem.route))
        }
    )
}

@ExperimentalMaterial3Api
@Composable
fun BreedsScreen(
    breedListState: UiState<List<BreedEntity>>,
    navigateToDogImages: (BreedEntity) -> Unit
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        "Animal Breeds",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            UiStateWrapper(breedListState) { data ->
                BreedList(
                    breeds = data,
                    onItemClick = navigateToDogImages
                )
            }
        }
    }
}

@Composable
fun BreedList(breeds: List<BreedEntity>, onItemClick: (BreedEntity) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ) {
        items(breeds) { breed ->
            BreedListItem(breed = breed, onClick = { onItemClick(breed) })
        }
    }
}

@Composable
fun BreedListItem(breed: BreedEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(bottom = 4.dp)
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = breed.name.capitalizeWords(),
                )
            },
            Modifier.clickable(onClick = onClick),
            tonalElevation = 4.dp,
        )
    }
}