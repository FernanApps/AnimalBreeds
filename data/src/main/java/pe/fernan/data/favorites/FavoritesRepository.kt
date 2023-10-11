package pe.fernan.data.favorites

import pe.fernan.data.images.DogImageDao
import pe.fernan.data.images.DogImageDataEntity.Companion.toDogImageEntity
import pe.fernan.domain.favorites.FavoritesRepository
import pe.fernan.domain.images.DogImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepositoryImpl @Inject constructor(
    private val imagesDao: DogImageDao
) : FavoritesRepository {

    override val favoriteImagesFlow: Flow<List<DogImage>> =
        imagesDao.getFavoriteDogImages().map { it.map { it.toDogImageEntity() } }

    override suspend fun updateFavoriteStatus(url: String, isFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            imagesDao.updateFavoriteStatus(
                url = url,
                isFavorite = isFavorite
            )
        }
    }
}


