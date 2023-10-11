package pe.fernan.domain.favorites

import pe.fernan.domain.images.DogImage
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    val favoriteImagesFlow: Flow<List<DogImage>>

    suspend fun updateFavoriteStatus(url: String, isFavorite: Boolean)
}