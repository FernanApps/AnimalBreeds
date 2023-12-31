package pe.fernan.domain.images

import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    fun getImagesByBreed(breedKey: String): Flow<List<AnimalImage>>
}