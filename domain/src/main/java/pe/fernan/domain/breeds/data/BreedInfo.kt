package pe.fernan.domain.breeds.data

interface BreedInfo {
    val id: String
    val name: String
    val subBreedsNames: List<String>
}