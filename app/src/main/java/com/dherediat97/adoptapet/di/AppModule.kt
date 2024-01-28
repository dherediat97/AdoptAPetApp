package com.dherediat97.adoptapet.di

import android.content.Context
import androidx.room.Room
import com.dherediat97.adoptapet.data.PetDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePetDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        PetDatabase::class.java,
        "pets_database"
    ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun providePetDao(db: PetDatabase) = db.petDao()
}