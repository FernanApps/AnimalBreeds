package pe.fernan.ui.images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pe.fernan.domain.images.AnimalImage
import pe.fernan.domain.images.GetBreedImagesUseCase
import pe.fernan.domain.images.buildBreedKey
import pe.fernan.ui.common.UiState
import pe.fernan.ui.common.asUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val getImagesByBreedUseCase: GetBreedImagesUseCase,
) : ViewModel() {

    private val _animalImagesState =
        MutableStateFlow<UiState<List<AnimalImage>>>(UiState.Loading)
    val animalImagesState: StateFlow<UiState<List<AnimalImage>>> get() = _animalImagesState

    // Store a reference to the last launched coroutine
    private var lastFetchJob: Job? = null

    fun fetchDogImages(breed: String, subBreed: String?) {
        // Cancel the last coroutine if it exists
        lastFetchJob?.cancel()

        val breedKey = buildBreedKey(subBreed, breed)

        lastFetchJob = viewModelScope.launch(Dispatchers.IO) {
            getImagesByBreedUseCase(breedKey)
                .asUiState()
                .collect { state -> _animalImagesState.value = state }
        }
    }
}
