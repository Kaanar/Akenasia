package com.example.akenasia.Handler

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.example.akenasia.database.Place
import kotlin.system.exitProcess

class PlaceHandler(var context: Context): Handler {

    var dbHandler: DatabaseHandler = DatabaseHandler(context)

    //Method to get a Position
    fun get(id: Int): Place {
        val db = dbHandler.readableDatabase
        val selectQuery = "SELECT  * FROM ${dbHandler.TABLE_PLACE} WHERE $KEY_ID = $id"
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
        }
        val placeId: Int
        val placeName: String
        val placeLat: Double
        val placeLong: Double

        if (cursor != null) {
            cursor.moveToFirst()
            placeId = cursor.getInt(cursor.getColumnIndex("id").toInt())
            placeName = cursor.getString(cursor.getColumnIndex("name").toInt())
            placeLat = cursor.getDouble(cursor.getColumnIndex("latitude").toInt())
            placeLong = cursor.getDouble(cursor.getColumnIndex("longitude").toInt())
            val emp = Place(
                placeId = placeId,
                placeName = placeName,
                placeLat = placeLat,
                placeLong = placeLong
            )
            return emp
        }
        exitProcess(0)
    }

    fun add(emp: Place): Long {
        val contentValues = ContentValues()
        val db= dbHandler.writableDatabase
        contentValues.put(KEY_ID, emp.placeId)
        contentValues.put(KEY_NAME, emp.placeName) // EmpModelClass Name
        contentValues.put(KEY_LATITUDE, emp.placeLat)
        contentValues.put(KEY_LONGITUDE, emp.placeLong)

        // Inserting Row
        val success = db.insert(dbHandler.TABLE_PLACE, null,  contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //method to read a list of Position
    fun view(): List<Place> {
        val empList:ArrayList<Place> = ArrayList()
        val selectQuery = "SELECT  * FROM ${dbHandler.TABLE_PLACE}"
        val db = dbHandler.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var placeId: Int
        var placeName: String
        var placeLat: Double
        var placeLong: Double

        if (cursor.moveToFirst()) {
            do {
                placeId = cursor.getInt(cursor.getColumnIndex("id").toInt())
                placeName = cursor.getString(cursor.getColumnIndex("name").toInt())
                placeLat = cursor.getDouble(cursor.getColumnIndex("latitude").toInt())
                placeLong = cursor.getDouble(cursor.getColumnIndex("longitude").toInt())

                val emp= Place(placeId = placeId, placeName = placeName, placeLat = placeLat, placeLong = placeLong)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }

    fun delete(id: Int): Int {
        val db = dbHandler.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, id) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(dbHandler.TABLE_PLACE,"id="+id,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun update(emp: Place): Int {
        val db = dbHandler.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.placeId-1)
        contentValues.put(KEY_NAME, emp.placeName)
        contentValues.put(KEY_LATITUDE, emp.placeLat)
        contentValues.put(KEY_LONGITUDE, emp.placeLong)

        // Updating Row
        val success = db.update(dbHandler.TABLE_PLACE, contentValues,"id="+emp.placeId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
}