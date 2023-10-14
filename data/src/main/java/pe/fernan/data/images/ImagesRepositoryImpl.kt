package pe.fernan.data.images

import pe.fernan.data.images.AnimalImageDataEntity.Companion.fromAnimalImageEntity
import pe.fernan.data.images.AnimalImageDataEntity.Companion.toAnimalImageEntity
import pe.fernan.domain.breeds.buildDisplayNameFromKey
import pe.fernan.domain.images.AnimalImage
import pe.fernan.domain.images.ImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import pe.fernan.data.animals.AnimalDataStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesRepositoryImpl @Inject constructor(
    private val dogBreedApiService: BreedImagesApi,
    private val imagesDataStore: AnimalImageDao,
    private val animalDataStore: AnimalDataStore
) : ImagesRepository {

    private val animalKey = animalDataStore.currentAnimalKey

    override fun getImagesByBreed(breedKey: String): Flow<List<AnimalImage>> =
        getImagesByBreedFromLocal(breedKey)
            .onStart { fetchAndSave(breedKey) }
            .distinctUntilChanged()

    private suspend fun fetchAndSave(breedKey: String) {
        val remoteData = getRemoteBreedImages(breedKey)
        imagesDataStore.insertAll(remoteData.map { it.fromAnimalImageEntity() })
    }

    private suspend fun getRemoteBreedImages(breedKey: String): List<AnimalImage> {
        return dogBreedApiService.getBreedImages(animalKey, breedKey).map { url ->
            AnimalImage(
                breedName = buildDisplayNameFromKey(breedKey),
                isFavorite = false,
                url = url,
                breedKey = breedKey,
                animalKey = animalKey
            )
        }
    }

    private fun getImagesByBreedFromLocal(breedKey: String): Flow<List<AnimalImage>> {
        return imagesDataStore.getAnimalImagesByBreedKey(animalKey, breedKey).map {
            it.map { entity ->
                entity.toAnimalImageEntity()
            }
        }
    }
}



