package pe.fernan.data

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class DogApiResponse<T>(
    val status: String,
    val message: T
)

@Serializable
class CatApiResponse(
    @SerialName("weight")
    val weight: Weight?,
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String?,
    /*
    @SerialName("cfa_url")
    val cfaUrl: String?,
    @SerialName("vetstreet_url")
    val vetstreetUrl: String?,
    @SerialName("vcahospitals_url")
    val vcahospitalsUrl: String?,
    @SerialName("temperament")
    val temperament: String?,
    @SerialName("origin")
    val origin: String?,
    @SerialName("country_codes")
    val countryCodes: String?,
    @SerialName("country_code")
    val countryCode: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("life_span")
    val lifeSpan: String?,
    @SerialName("indoor")
    val indoor: Int?,
    @SerialName("lap")
    val lap: Int?,
    @SerialName("alt_names")
    val altNames: String?,
    @SerialName("adaptability")
    val adaptability: Int?,
    @SerialName("affection_level")
    val affectionLevel: Int?,
    @SerialName("child_friendly")
    val childFriendly: Int?,
    @SerialName("dog_friendly")
    val dogFriendly: Int?,
    @SerialName("energy_level")
    val energyLevel: Int?,
    @SerialName("grooming")
    val grooming: Int?,
    @SerialName("health_issues")
    val healthIssues: Int?,
    @SerialName("intelligence")
    val intelligence: Int?,
    @SerialName("shedding_level")
    val sheddingLevel: Int?,
    @SerialName("social_needs")
    val socialNeeds: Int?,
    @SerialName("stranger_friendly")
    val strangerFriendly: Int?,
    @SerialName("vocalisation")
    val vocalisation: Int?,
    @SerialName("experimental")
    val experimental: Int?,
    @SerialName("hairless")
    val hairless: Int?,
    @SerialName("natural")
    val natural: Int?,
    @SerialName("rare")
    val rare: Int?,
    @SerialName("rex")
    val rex: Int?,
    @SerialName("suppressed_tail")
    val suppressedTail: Int?,
    @SerialName("short_legs")
    val shortLegs: Int?,
    @SerialName("wikipedia_url")
    val wikipediaUrl: String?,
    @SerialName("hypoallergenic")
    val hypoallergenic: Int?,
    @SerialName("reference_image_id")
    val referenceImageId: String?
    */
) {
    @Serializable
    class Weight(
        @SerialName("imperial")
        val imperial: String?,
        @SerialName("metric")
        val metric: String?
    )
}




class KtorHttpClient {
    val client: HttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }

    suspend inline fun <reified T : DogApiResponse<U>, U> safeApiCall(
        apiCall: HttpClient.() -> HttpResponse
    ): U {
        val response = apiCall.invoke(client)
        return when (response.status) {
            HttpStatusCode.OK -> {
                val obj = response.body<T>()
                if (obj.status != "success") {
                    error("Server responded with status: ${obj.status}")
                } else {
                    obj.message
                }
            }

            else -> error("Server responded with status: ${response.status}")
        }
    }

    suspend inline fun <reified T: List<CatApiResponse>> safeNormalApiCall(
        apiCall: HttpClient.() -> HttpResponse
    ): T {
        val response = apiCall.invoke(client)
        return when (response.status) {
            HttpStatusCode.OK -> {
                val body = response.body<T>()
                val json = Json.encodeToString(body)
                Log.d("catResponse", json)
                body
            }
            else -> error("Server responded with status: ${response.status}")
        }
    }
}