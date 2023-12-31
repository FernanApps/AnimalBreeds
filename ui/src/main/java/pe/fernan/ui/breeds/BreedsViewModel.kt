package pe.fernan.ui.breeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pe.fernan.domain.breeds.GetBreedsUseCase
import pe.fernan.ui.common.UiState
import pe.fernan.ui.common.asUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(
    getBreedsUseCase: GetBreedsUseCase
) : ViewModel() {

    val breedsState = getBreedsUseCase()
        .asUiState()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            UiState.Loading
        )
}
