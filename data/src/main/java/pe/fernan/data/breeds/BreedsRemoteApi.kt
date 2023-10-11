package pe.fernan.data.breeds

import pe.fernan.data.KtorHttpClient
import pe.fernan.data.images.BASE_URL
import io.ktor.client.request.get
import javax.inject.Inject

class BreedsRemoteApi @Inject constructor(private val client: KtorHttpClient) {

    suspend fun getAllBreeds(): Map<String, List<String>> {
        return client.safeApiCall {
            get("${BASE_URL}breeds/list/all")
        }
    }
}