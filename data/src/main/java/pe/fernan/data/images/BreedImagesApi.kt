package pe.fernan.data.images

import pe.fernan.data.KtorHttpClient
import io.ktor.client.request.get
import javax.inject.Inject



const val BASE_URL_DOG = "https://dog.ceo/api/"

// https://api.thecatapi.com/v1/breeds?limit=10&page=0
const val BASE_URL_CAT = "https://api.thecatapi.com/v1/"

interface BreedImagesApi {
    suspend fun getBreedImages(animalKey: String, breed: String): List<String>
}

class BreedImagesApiImpl @Inject constructor(
    private val client: KtorHttpClient
) : BreedImagesApi {

    /**
     * Fetches a list of image URLs for a given dog breed or sub-breed.
     *
     * @param breed The name of the dog breed or sub-breed in the format "breed/subBreed".
     *              For example, for a sub-breed like "Australian Shepherd", you would use "shepherd/australian".
     *              For a main breed like "bulldog", just use "bulldog".
     *
     * @return ApiResponse<List<String>> A response object containing either a list of image URLs or an error.
     *
     * Usage:
     * 1. To get images for a main breed:
     *      getBreedImages("bulldog")
     * 2. To get images for a sub-breed:
     *      getBreedImages("shepherd/australian")
     */
    override suspend fun getBreedImages(animalKey: String, breed: String): List<String> {
        // Cat Or Dog
        return client.safeApiCall {
            get("${BASE_URL_DOG}breed/$breed/images")
        }
    }
}

