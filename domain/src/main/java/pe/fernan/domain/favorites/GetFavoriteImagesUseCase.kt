package pe.fernan.domain.favorites

import pe.fernan.domain.images.AnimalImage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteImagesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    operator fun invoke(): Flow<List<AnimalImage>> =
        favoritesRepository.favoriteImagesFlow
}

