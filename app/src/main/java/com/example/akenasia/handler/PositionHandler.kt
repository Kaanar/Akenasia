package com.example.akenasia.handler

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.example.akenasia.database.PositionTable
import kotlin.system.exitProcess

class PositionHandler(var context: Context) : Handler {


    var dbHandler: DatabaseHandler = DatabaseHandler(context)

    //Method to get a Position
    fun get(id: Int): PositionTable {
        val db = dbHandler.readableDatabase
        val selectQuery = "SELECT  * FROM ${dbHandler.TABLE_POSITION} WHERE $KEY_ID = $id"
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
        }

        val posId: Int
        val posLat: Double
        val posLong: Double
        val posPartie: Int

        if (cursor != null) {
            cursor.moveToFirst()
            posId = cursor.getInt(cursor.getColumnIndex("id").toInt())
            posLat = cursor.getDouble(cursor.getColumnIndex("latitude").toInt())
            posLong = cursor.getDouble(cursor.getColumnIndex("longitude").toInt())
            posPartie = cursor.getInt(cursor.getColumnIndex("partie").toInt())
            val emp= PositionTable(posId= posId, posLat= posLat, posLong= posLong, partie=posPartie)
            return emp
        }
        exitProcess(0)
    }

    fun add(emp: PositionTable): Long {
        val contentValues = ContentValues()
        val db= dbHandler.writableDatabase
        contentValues.put(KEY_ID, emp.posId)
        contentValues.put(KEY_LATITUDE, emp.posLat)
        contentValues.put(KEY_LONGITUDE, emp.posLong)
        contentValues.put(KEY_PARTIE, emp.partie)

        // Inserting Row
        val success = db.insert(dbHandler.TABLE_POSITION, null,  contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //method to read a list of Position
    fun view(partie: Int): List<PositionTable> {
        val empList:ArrayList<PositionTable> = ArrayList()
        val selectQuery = "SELECT * FROM ${dbHandler.TABLE_POSITION} WHERE $KEY_PARTIE=$partie"
        val db = dbHandler.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var posId: Int
        var posLat: Double
        var posLong: Double
        var posPartie: Int

        if (cursor != null && cursor.moveToFirst()) {
            do {
                posId = cursor.getInt(cursor.getColumnIndex("id").toInt())
                posLat = cursor.getDouble(cursor.getColumnIndex("latitude").toInt())
                posLong = cursor.getDouble(cursor.getColumnIndex("longitude").toInt())
                posPartie = cursor.getInt(cursor.getColumnIndex("partie").toInt())
                val emp= PositionTable(posId= posId, posLat= posLat, posLong= posLong, partie=posPartie)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }

    fun delete(id: Int): Int {
        val db = dbHandler.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_PARTIE, id)
        // Deleting Row
        val success = db.delete(dbHandler.TABLE_POSITION,"partie="+id,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun update(emp: PositionTable): Int {
        val db = dbHandler.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.posId)
        contentValues.put(KEY_LATITUDE, emp.posLat)
        contentValues.put(KEY_LONGITUDE, emp.posLong)
        contentValues.put(KEY_PARTIE, emp.partie)

        // Updating Row
        val success = db.update(dbHandler.TABLE_POSITION, contentValues,"id="+emp.posId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
}