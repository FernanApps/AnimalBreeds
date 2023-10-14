package pe.fernan.ui.selector

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.fernan.domain.animals.SaveCurrentAnimalUseCase
import javax.inject.Inject

@HiltViewModel
class SelectorViewModel @Inject constructor(
    private val saveCurrentAnimalUseCase: SaveCurrentAnimalUseCase
) : ViewModel() {

    private val _animalSaved = mutableStateOf<Boolean?>(null)
    val animalSaved: State<Boolean?>
        get() = _animalSaved

    fun saveCurrentAnimal(currentAnimal: String) {
        viewModelScope.launch {
            val result = saveCurrentAnimalUseCase.invoke(currentAnimal)
            _animalSaved.value = result

        }
    }

}