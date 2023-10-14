package pe.fernan.domain.favorites

import pe.fernan.domain.images.AnimalImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeFavoritesRepository(
    initialDogImages: List<AnimalImage>
) : FavoritesRepository {

    private val _images = MutableStateFlow(initialDogImages)

    override val favoriteImagesFlow: Flow<List<AnimalImage>> =
        _images.map { images ->
            images.filter { it.isFavorite }
        }

    override suspend fun updateFavoriteStatus(url: String, isFavorite: Boolean) {
        val currentImages = _images.value.toMutableList()

        // Find the image with the given URL
        val imageIndex = currentImages.indexOfFirst { it.url == url }

        if (imageIndex != -1) {
            // Update the isFavorite status of the image
            val updatedImage = currentImages[imageIndex].copy(isFavorite = isFavorite)
            currentImages[imageIndex] = updatedImage
        }

        _images.value = currentImages
    }
}
