package pe.fernan.domain.favorites

import pe.fernan.domain.images.AnimalImage
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(breedImage: AnimalImage) {
        favoritesRepository.updateFavoriteStatus(
            breedImage.url,
            !breedImage.isFavorite
        )
    }
}