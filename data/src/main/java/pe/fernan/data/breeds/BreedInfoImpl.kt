package pe.fernan.data.breeds

import pe.fernan.domain.breeds.data.BreedInfo
import kotlinx.serialization.Serializable

@Serializable
data class AnimalImpl(
    val key: String,
    val breedInfoList: List<BreedInfoImpl>
)


@Serializable
data class BreedInfoImpl(
    override val id: String,
    override val name: String,
    override val subBreedsNames: List<String>
) : BreedInfo {
    companion object {
        fun fromMap(map: Map<String, List<String>>): List<BreedInfoImpl> {
            return map.map { (breed, subBreeds) ->
                BreedInfoImpl(id = breed, name = breed, subBreeds)
            }
        }
    }
}
