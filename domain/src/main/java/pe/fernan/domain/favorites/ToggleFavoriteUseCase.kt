package pe.fernan.domain.favorites

import pe.fernan.domain.images.DogImage
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(breedImage: DogImage) {
        favoritesRepository.updateFavoriteStatus(
            breedImage.url,
            !breedImage.isFavorite
        )
    }
}