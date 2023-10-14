package pe.fernan.data.favorites

import pe.fernan.data.images.AnimalImageDao
import pe.fernan.data.images.AnimalImageDataEntity.Companion.toAnimalImageEntity
import pe.fernan.domain.favorites.FavoritesRepository
import pe.fernan.domain.images.AnimalImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pe.fernan.data.animals.AnimalDataStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepositoryImpl @Inject constructor(
    private val animalDataStore: AnimalDataStore,
    private val imagesDao: AnimalImageDao
) : FavoritesRepository {

    //override val favoriteImagesFlow: Flow<List<DogImage>> =
    //    imagesDao.getFavoriteAnimalImages(animalDataStore.getCurrentAnimal.firstOrNull()).map { it.map { it.toDogImageEntity() } }

    override val favoriteImagesFlow =
        imagesDao.getFavoriteAnimalImages(animalDataStore.currentAnimalKey).map { it.map { it.toAnimalImageEntity() } }

    override suspend fun updateFavoriteStatus(url: String, isFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            imagesDao.updateFavoriteStatus(
                url = url,
                isFavorite = isFavorite
            )
        }
    }
}


