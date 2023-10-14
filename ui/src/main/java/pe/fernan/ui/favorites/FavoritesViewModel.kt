package pe.fernan.ui.favorites

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import pe.fernan.domain.favorites.GetFavoriteImagesUseCase
import pe.fernan.domain.favorites.ToggleFavoriteUseCase
import pe.fernan.domain.images.AnimalImage
import pe.fernan.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    val getFavoriteImagesUseCase: GetFavoriteImagesUseCase,
    val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : MoleculeViewModel<Event, UiState<FavoritesModel>>() {

    @Composable
    override fun models(events: Flow<Event>): UiState<FavoritesModel> {
        return FavoritesPresenter(
            events = events,
            favoriteImagesFlow = getFavoriteImagesUseCase()
        )
    }

    fun toggleBreedFilter(breed: ChipInfo) {
        take(Event.ToggleSelectedBreed(breed))
    }

    val favoriteCount: StateFlow<Int> = getFavoriteImagesUseCase().map {
        it.size
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = 0
    )

    fun toggleFavorite(breedImage: AnimalImage) {
        viewModelScope.launch {
            toggleFavoriteUseCase(breedImage)
        }
    }

}