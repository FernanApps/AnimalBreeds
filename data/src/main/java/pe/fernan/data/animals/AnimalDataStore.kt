package pe.fernan.data.animals

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import pe.fernan.data.DataStoreManager
import pe.fernan.data.breeds.BreedInfoImpl
import javax.inject.Inject
import javax.inject.Singleton


var _currentAnimalKey: String? = null

@Singleton
class AnimalDataStore @Inject constructor(
    private val dataStoreManager: DataStoreManager
) {
    private val animalCurrentKey = "animal_current_key"

    val currentAnimalKey get() = _currentAnimalKey!!

    //private val getCurrentAnimal = dataStoreManager.getOrNull<String>(animalCurrentKey)

    suspend fun saveCurrentAnimal(keyAnimal: String): Boolean {
        dataStoreManager.putString(animalCurrentKey, keyAnimal)
        _currentAnimalKey = keyAnimal
        return dataStoreManager.getOrNull<String>(animalCurrentKey).firstOrNull() != null
    }

}



