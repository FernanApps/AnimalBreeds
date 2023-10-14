package pe.fernan.data.images

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface AnimalImageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(dogImages: List<AnimalImageDataEntity>)

    @Query("SELECT * FROM dog_images WHERE breed_key = :breedKey AND animal_key = :animalKey")
    fun getAnimalImagesByBreedKey(animalKey: String, breedKey: String): Flow<List<AnimalImageDataEntity>>

    @Query("SELECT * FROM dog_images WHERE is_favorite = 1 AND animal_key = :animalKey")
    fun getFavoriteAnimalImages(animalKey: String): Flow<List<AnimalImageDataEntity>>

    @Query("UPDATE dog_images SET is_favorite = :isFavorite WHERE url = :url")
    fun updateFavoriteStatus(url: String, isFavorite: Boolean): Int
}