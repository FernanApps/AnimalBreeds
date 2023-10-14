package pe.fernan.domain.images

data class AnimalImage(
    val breedName: String,
    val breedKey: String,
    val isFavorite: Boolean,
    val url: String,
    val animalKey: String
)

fun buildBreedKey(subBreed: String?, breed: String) =
    if (subBreed == null) breed else "$breed/$subBreed"