package com.example.akenasia.Handler

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.database.DatabaseUtils




class StatsHandler(var context: Context): Handler {


    var dbHandler: DatabaseHandler = DatabaseHandler(context)


    //Method to Initialize stats of a player
    fun create(id: Int): Long {
        val contentValues = ContentValues()
        val db= dbHandler.writableDatabase
        contentValues.put(KEY_REFJOUEUR, id)
        contentValues.put(KEY_TOTALMARQUEUR, 0)
        contentValues.put(KEY_TOTALMONSTRE, 0)
        contentValues.put(KEY_TOTALITEM, 0)

        // Inserting Row
        val success = db.insert(dbHandler.TABLE_STATS, null,  contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //method to read a list of Stats of a player
    fun getStatsCount(): Long {
        val db = dbHandler.readableDatabase
        return DatabaseUtils.queryNumEntries(db, dbHandler.TABLE_STATS)
    }

    //Method to get all visited markers by a player
    fun getTotalMarqueur(id: Int): Int {
        val db = dbHandler.readableDatabase
        val selectQuery = "SELECT * FROM ${dbHandler.TABLE_STATS} WHERE $KEY_REFJOUEUR = $id"
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
        }
        var totalMarqueur=0
        if (cursor != null && cursor.moveToFirst()) {
            totalMarqueur = cursor.getInt(cursor.getColumnIndex("marqueur_t").toInt())
        }
        return totalMarqueur
    }



    //Method to get all defeated monsters by a player
    fun getTotalMonstre(id: Int): Int {
        val db = dbHandler.readableDatabase
        val selectQuery = "SELECT  * FROM ${dbHandler.TABLE_STATS} WHERE $KEY_REFJOUEUR = $id"
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
        }
        var totalMonstre=0
        if (cursor != null) {
            cursor.moveToFirst()
            totalMonstre = cursor.getInt(cursor.getColumnIndex("monstre_t").toInt())
        }
        return totalMonstre
    }

    //Method to get all collected by a player
    fun getTotalItem(id: Int): Int {
        val db = dbHandler.readableDatabase
        val selectQuery = "SELECT  * FROM ${dbHandler.TABLE_STATS} WHERE $KEY_REFJOUEUR = $id"
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
        }
        var totalItem=0
        if (cursor != null) {
            cursor.moveToFirst()
            totalItem = cursor.getInt(cursor.getColumnIndex("item_t").toInt())
        }
        return totalItem
    }

    fun upTotalMarqueur(id: Int): Int{
        val db = dbHandler.writableDatabase
        val total = this.getTotalMarqueur(id) + 1
        val contentValues = ContentValues()
        contentValues.put(KEY_REFJOUEUR, id)
        contentValues.put(KEY_TOTALMARQUEUR, total)

        // Updating Row
        val success = db.update(dbHandler.TABLE_STATS, contentValues, "$KEY_REFJOUEUR=$id",null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun upTotalMonstre(id: Int): Int{
        val db = dbHandler.writableDatabase
        val total = this.getTotalMonstre(id) + 1
        val contentValues = ContentValues()
        contentValues.put(KEY_REFJOUEUR, id)
        contentValues.put(KEY_TOTALMONSTRE, total)

        // Updating Row
        val success = db.update(dbHandler.TABLE_STATS, contentValues,"$KEY_REFJOUEUR=$id",null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun upTotalItem(id: Int): Int{
        val db = dbHandler.writableDatabase
        val total = this.getTotalItem(id) + 1
        val contentValues = ContentValues()
        contentValues.put(KEY_REFJOUEUR, id)
        contentValues.put(KEY_TOTALITEM, total)

        // Updating Row
        val success = db.update(dbHandler.TABLE_STATS, contentValues,"$KEY_REFJOUEUR=$id",null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
}