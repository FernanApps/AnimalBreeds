package pe.fernan.data.breeds

import android.util.Log
import pe.fernan.domain.breeds.data.BreedsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import pe.fernan.data.animals.AnimalDataStore
import pe.fernan.domain.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BreedsRepositoryImpl @Inject constructor(
    private val breedsApi: BreedsRemoteApi,
    private val animalDataStore: AnimalDataStore,
    private val breedsDataStore: BreedsDataStore
) : BreedsRepository {

    override val breedsFlow: Flow<List<BreedInfoImpl>> = flow {

        val animalKey = animalDataStore.currentAnimalKey

        // Try fetching from local first
        val localBreeds = getBreedsFromLocal(animalKey)
        if (localBreeds != null) {
            emit(localBreeds)
        }

        // Then always fetch from remote
        try {
            if(animalKey == Constants.keyDog){
                val remoteDogBreeds = breedsApi.getAllDogBreeds()
                val breeds = BreedInfoImpl.fromMap(remoteDogBreeds)
                breedsDataStore.save(animalKey, breeds)
                emit(breeds)
            } else if(animalKey == Constants.keyCat){
                val remoteCatResponse = breedsApi.getAllCatBreeds()
                val breeds = remoteCatResponse.mapNotNull {
                    if(it.id != null && it.name != null){
                        BreedInfoImpl(id = it.id, name = it.name, listOf())

                    } else {
                        null
                    }
                }
                breedsDataStore.save(animalKey, breeds)
                emit(breeds)
            }

        } catch (exception: Exception) {
            if (localBreeds == null) {
                throw exception
            }
        }
    }

    private suspend fun getBreedsFromLocal(animalKey: String): List<BreedInfoImpl>? {
        return breedsDataStore.get(animalKey).firstOrNull()
    }

}


