package pe.fernan.domain.favorites

import pe.fernan.domain.images.DogImage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteImagesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    operator fun invoke(): Flow<List<DogImage>> =
        favoritesRepository.favoriteImagesFlow
}

