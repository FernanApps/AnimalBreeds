package pe.fernan.data.animals;

import pe.fernan.domain.animals.AnimalRepository
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class AnimalRepositoryImpl @Inject constructor(
    private val breedsDataStore: AnimalDataStore
) : AnimalRepository {
    override suspend fun saveCurrent(currentAnimal: String): Boolean {
        return breedsDataStore.saveCurrentAnimal(currentAnimal)
    }
}