package com.kisahcode.fundamentalandroid.db


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.kisahcode.fundamentalandroid.db.DatabaseContract.NoteColumns
import com.kisahcode.fundamentalandroid.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME

/**
 * Helper class to manage database creation and version management.
 * This class extends SQLiteOpenHelper, providing methods to create and upgrade the database.
 *
 * @param context The context in which the database is created.
 */
internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    /**
     * Defines constants for the database name and version.
     * Also, it contains the SQL statement to create the note table.
     */
    companion object {
        private const val DATABASE_NAME = "dbnoteapp"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NAME" +
                " (${NoteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${NoteColumns.TITLE} TEXT NOT NULL," +
                " ${NoteColumns.DESCRIPTION} TEXT NOT NULL," +
                " ${NoteColumns.DATE} TEXT NOT NULL)"
    }

    /**
     * Called when the database is created for the first time.
     * It executes the SQL statement to create the note table.
     *
     * @param db The SQLiteDatabase instance representing the database.
     */
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    /**
     * Called when the database needs to be upgraded.
     * It drops the existing table and recreates it by calling onCreate().
     *
     * @param db The SQLiteDatabase instance representing the database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}