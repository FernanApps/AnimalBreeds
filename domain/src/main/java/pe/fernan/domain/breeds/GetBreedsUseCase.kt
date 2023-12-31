package pe.fernan.domain.breeds


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pe.fernan.domain.breeds.data.BreedInfo
import pe.fernan.domain.breeds.data.BreedsRepository
import pe.fernan.domain.common.capitalizeWords
import javax.inject.Inject

class GetBreedsUseCase @Inject constructor(
    private val breedsRepository: BreedsRepository
) {

    operator fun invoke(): Flow<List<BreedEntity>> {
        return breedsRepository.breedsFlow.map(::mapInfoToItems)
    }

    private fun mapInfoToItems(
        breedInfoList: List<BreedInfo>
    ): List<BreedEntity> {
        return breedInfoList.flatMap { breed ->
            val subBreedItems = breed.subBreedsNames.map { subBreedName ->
                BreedEntity(
                    buildDisplayName(breed.name, subBreedName),
                    "${breed.id}_${subBreedName}"
                )
            }

            listOf(
                BreedEntity(
                    breed.name,
                    breed.id
                )
            ) + subBreedItems
        }
    }
}

fun buildDisplayName(breedName: String, subBreedName: String?): String {
    val capitalizedBreed = breedName.capitalizeWords()
    val capitalizedSubBreed = subBreedName?.capitalizeWords()

    return if (capitalizedSubBreed != null) {
        "$capitalizedBreed ($capitalizedSubBreed)"
    } else {
        capitalizedBreed
    }
}

fun buildDisplayNameFromKey(breedName: String): String {
    val parts = breedName.split('/')
    val breed = parts[0].capitalizeWords()
    val subBreed = parts.getOrNull(1)?.capitalizeWords()

    return buildDisplayName(breed, subBreed)
}
