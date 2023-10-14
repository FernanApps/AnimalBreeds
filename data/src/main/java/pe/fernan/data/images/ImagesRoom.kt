package pe.fernan.data.images

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase
import pe.fernan.domain.images.AnimalImage

@Entity(tableName = "dog_images")
data class AnimalImageDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "breed_name") val breedName: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
    @ColumnInfo(name = "breed_key") val breedKey: String,
    @ColumnInfo(name = "animal_key") val animalKey: String
) {
    companion object {
        fun AnimalImage.fromAnimalImageEntity(): AnimalImageDataEntity {
            return AnimalImageDataEntity(
                url = url,
                breedName = breedName,
                isFavorite = isFavorite,
                breedKey = breedKey,
                animalKey = animalKey
            )
        }

        fun AnimalImageDataEntity.toAnimalImageEntity(): AnimalImage {
            return AnimalImage(
                url = url,
                breedName = breedName,
                isFavorite = isFavorite,
                breedKey = breedKey,
                animalKey = animalKey
            )
        }
    }
}


@Database(entities = [AnimalImageDataEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dogImageDao(): AnimalImageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}