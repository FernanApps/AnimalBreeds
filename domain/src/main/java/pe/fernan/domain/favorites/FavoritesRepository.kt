package pe.fernan.domain.favorites

import pe.fernan.domain.images.AnimalImage
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    val favoriteImagesFlow: Flow<List<AnimalImage>>
    //fun favoriteImagesFlow(): Flow<List<AnimalImage>>
    suspend fun updateFavoriteStatus(url: String, isFavorite: Boolean)
}