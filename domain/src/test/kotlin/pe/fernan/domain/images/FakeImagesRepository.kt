package pe.fernan.domain.images

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeImagesRepository(
    initialDogImages: List<AnimalImage>
) : ImagesRepository {

    private val _images = MutableStateFlow(initialDogImages)

    override suspend fun getImagesByBreed(breedKey: String): Flow<List<AnimalImage>> {
        return _images.map { images ->
            images.filter { it.breedKey == breedKey }
        }
    }
}
