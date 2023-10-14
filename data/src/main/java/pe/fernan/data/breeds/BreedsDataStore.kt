package pe.fernan.data.breeds

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import pe.fernan.data.DataStoreManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BreedsDataStore @Inject constructor(
    private val dataStoreManager: DataStoreManager
) {
    private val animalBreedsKey = "animal_breeds"

    /*val get: Flow<List<BreedInfoImpl>?> = dataStore.data.map { preferences ->
        preferences[animalBreedsKey]?.let { json ->
            try {
                Json.decodeFromString<List<BreedInfoImpl>>(json)
            } catch (e: Exception) {
                null
            }
        }
    }*/


    fun get(animalKey: String) =
        dataStoreManager.getOrNull<String>(animalBreedsKey + animalKey).map { jsonOrNull ->
            jsonOrNull?.let { json ->
                try {
                    Json.decodeFromString<List<BreedInfoImpl>>(json)
                } catch (e: Exception) {
                    null
                }
            }
        }

//    val get: Flow<List<BreedInfoImpl>?> =
//        dataStoreManager.getOrNull<String>(animalBreedsKey).map { jsonOrNull ->
//            jsonOrNull?.let { json ->
//            try {
//                Json.decodeFromString<List<BreedInfoImpl>>(json)
//            } catch (e: Exception) {
//                null
//            }
//        }
//    }


    suspend fun save(animalKey: String, breeds: List<BreedInfoImpl>) {
        val json = Json.encodeToString(breeds)
        dataStoreManager.putString(animalBreedsKey + animalKey, json)
    }


}



