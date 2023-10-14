package pe.fernan.domain.animals


interface AnimalRepository {
    suspend fun saveCurrent(currentAnimal: String): Boolean
}

