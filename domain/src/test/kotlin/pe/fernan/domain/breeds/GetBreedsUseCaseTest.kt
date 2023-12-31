package pe.fernan.domain.breeds

import pe.fernan.domain.breeds.data.BreedInfo
import pe.fernan.domain.breeds.data.BreedsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FakeBreedsRepository : BreedsRepository {
    var breedsData: List<BreedInfo> = emptyList()

    override val breedsFlow: Flow<List<BreedInfo>> = flow {
        emit(breedsData)
    }
}

class GetBreedsUseCaseTest {

    private val fakeBreedsRepository = FakeBreedsRepository()
    private val getBreedsUseCase = GetBreedsUseCase(fakeBreedsRepository)

    @Test
    fun `invoke returns mapped breed entities`() = runTest {
        // Given
        fakeBreedsRepository.breedsData =
            listOf(
                object : BreedInfo {
                    override val id: String = "dog"
                    override val name: String = "Dog"
                    override val subBreedsNames: List<String> = listOf("Lab", "Husky")
                },
            )


        // When
        val result = getBreedsUseCase.invoke().toList().first()

        // Then
        val expected =
            listOf(
                BreedEntity("Dog", "Dog"),
                BreedEntity("Dog (Lab)", "Dog_Lab"),
                BreedEntity("Dog (Husky)", "Dog_Husky")
            )

        assertEquals(expected, result)
    }

    @Test
    fun `buildDisplayName returns correctly formatted name without subBreed`() {
        val result = buildDisplayName("breed1", null)
        assertEquals("Breed1", result)
    }

    @Test
    fun `buildDisplayName returns correctly formatted name with subBreed`() {
        val result = buildDisplayName("breed1", "subBreedA")
        assertEquals("Breed1 (SubBreedA)", result)
    }

    @Test
    fun `buildDisplayNameFromKey returns correctly formatted name without subBreed`() {
        val result = buildDisplayNameFromKey("breed1")
        assertEquals("Breed1", result)
    }

    @Test
    fun `buildDisplayNameFromKey returns correctly formatted name with subBreed`() {
        val result = buildDisplayNameFromKey("breed1/subBreedA")
        assertEquals("Breed1 (SubBreedA)", result)
    }
}
