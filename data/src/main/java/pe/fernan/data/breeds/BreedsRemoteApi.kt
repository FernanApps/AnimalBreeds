package pe.fernan.data.breeds

import pe.fernan.data.KtorHttpClient
import pe.fernan.data.images.BASE_URL_DOG
import io.ktor.client.request.get
import pe.fernan.data.CatApiResponse
import pe.fernan.data.images.BASE_URL_CAT
import javax.inject.Inject

class BreedsRemoteApi @Inject constructor(private val client: KtorHttpClient) {

    suspend fun getAllDogBreeds(): Map<String, List<String>> {
        return client.safeApiCall {
            get("${BASE_URL_DOG}breeds/list/all")
        }
    }

    suspend fun getAllCatBreeds(): List<CatApiResponse> {
        // https://api.thecatapi.com/v1/breeds
        return client.safeNormalApiCall {
            get("${BASE_URL_CAT}breeds")
        }
    }


}