package com.kisahcode.fundamentalandroid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Room database class for managing Note entities.
 */
@Database(entities = [Note::class], version = 1)
abstract class NoteRoomDatabase : RoomDatabase() {

    /**
     * Provides access to the DAO interface for performing database operations on Note entities.
     *
     * @return The DAO interface for Note entities.
     */
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteRoomDatabase? = null

        /**
         * Gets the singleton instance of the NoteRoomDatabase.
         *
         * @param context The application context.
         * @return The singleton instance of the NoteRoomDatabase.
         */
        @JvmStatic
        fun getDatabase(context: Context): NoteRoomDatabase {
            if (INSTANCE == null) {
                synchronized(NoteRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NoteRoomDatabase::class.java, "note_database")
                        .build()
                }
            }
            return INSTANCE as NoteRoomDatabase
        }
    }
}