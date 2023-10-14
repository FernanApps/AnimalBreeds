package pe.fernan.domain.animals

import javax.inject.Inject

class SaveCurrentAnimalUseCase @Inject constructor(
    private val repository: AnimalRepository
) {

    suspend operator fun invoke(currentAnimal: String) = repository.saveCurrent(currentAnimal)

}