package pe.fernan.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import pe.fernan.data.breeds.BreedsDataStore
import pe.fernan.data.breeds.BreedsRemoteApi
import pe.fernan.data.breeds.BreedsRepositoryImpl
import pe.fernan.data.favorites.FavoritesRepositoryImpl
import pe.fernan.data.images.AppDatabase
import pe.fernan.data.images.BreedImagesApi
import pe.fernan.data.images.BreedImagesApiImpl
import pe.fernan.data.images.AnimalImageDao
import pe.fernan.data.images.ImagesRepositoryImpl
import pe.fernan.domain.breeds.data.BreedsRepository
import pe.fernan.domain.favorites.FavoritesRepository
import pe.fernan.domain.images.ImagesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pe.fernan.data.animals.AnimalDataStore
import pe.fernan.data.animals.AnimalRepositoryImpl
import pe.fernan.domain.animals.AnimalRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private val Context.dataStore by preferencesDataStore("animal_breeds")

    @Provides
    @Singleton
    fun provideHttpClient(): KtorHttpClient = KtorHttpClient()

    @Provides
    @Singleton
    fun provideBreedImagesApi(
        httpClient: KtorHttpClient
    ): BreedImagesApi = BreedImagesApiImpl(httpClient)

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(dataStore: DataStore<Preferences>): DataStoreManager {
        return DataStoreManager(dataStore)
    }

    @Provides
    @Singleton
    fun provideFavoritesRepository(animalDataStore: AnimalDataStore, dogImageDao: AnimalImageDao): FavoritesRepository {
        return FavoritesRepositoryImpl(animalDataStore,dogImageDao)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideDogImageDao(database: AppDatabase): AnimalImageDao {
        return database.dogImageDao()
    }

    @Provides
    @Singleton
    fun provideBreedsRepository(
        dogBreedApiService: BreedsRemoteApi,
        animalDataStore: AnimalDataStore,
        breedsDataStore: BreedsDataStore,
    ): BreedsRepository {
        return BreedsRepositoryImpl(
            dogBreedApiService,
            animalDataStore,
            breedsDataStore
        )
    }


    @Provides
    @Singleton
    fun provideAnimalRepository(
        animalDataStore: AnimalDataStore
    ): AnimalRepository {
        return AnimalRepositoryImpl(
            animalDataStore,
        )
    }

    @Provides
    @Singleton
    fun provideBreedImagesRepository(
        dogBreedApiService: BreedImagesApi,
        dogImageDao: AnimalImageDao,
        animalDataStore: AnimalDataStore
    ): ImagesRepository {
        return ImagesRepositoryImpl(
            dogBreedApiService,
            dogImageDao,
            animalDataStore
        )
    }
}