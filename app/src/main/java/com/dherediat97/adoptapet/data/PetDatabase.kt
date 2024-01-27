package com.dherediat97.adoptapet.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(Pet::class)], version = 1, exportSchema = false)
abstract class PetDatabase : RoomDatabase() {

    abstract fun petDao(): PetDao
}