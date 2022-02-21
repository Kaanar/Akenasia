package com.example.akenasia.Handler

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.example.akenasia.database.Marqueur
import com.google.android.gms.maps.model.LatLng
import kotlin.system.exitProcess
import java.util.ArrayList


class MarqueurHandler(var context : Context): Handler {

    var dbHandler: DatabaseHandler = DatabaseHandler(context)

    //Method to get a Marker
    fun get(id: Int): Marqueur {
        val db = dbHandler.readableDatabase
        val selectQuery = "SELECT  * FROM ${dbHandler.TABLE_MARQUEUR} WHERE $KEY_ID = $id"
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
        }

        val Lat: Double
        val Long: Double
        val visible: Int
        val lastUpdated: Long


        if (cursor != null) {
            cursor.moveToFirst()
            Lat = cursor.getDouble(cursor.getColumnIndex("latitude").toInt())
            Long = cursor.getDouble(cursor.getColumnIndex("longitude").toInt())
            visible = cursor.getInt(cursor.getColumnIndex("visible").toInt())
            lastUpdated = cursor.getLong(cursor.getColumnIndex("last_updated").toInt())
            return Marqueur(id, Lat.toString(),Long.toString(), lastUpdated, visible)
        }
        exitProcess(0)
    }

    fun add(id: Int, emp: LatLng): Long {
        val contentValues = ContentValues()
        val db= dbHandler.writableDatabase
        contentValues.put(KEY_ID, id)
        contentValues.put(KEY_LATITUDE, emp.latitude)
        contentValues.put(KEY_LONGITUDE, emp.longitude)
        contentValues.put(KEY_VISIBLE, 1)
        contentValues.put(KEY_LASTUPDATED, System.currentTimeMillis())

        // Inserting Row
        val success = db.insert(dbHandler.TABLE_MARQUEUR, null,  contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //method to read a list of Position
    fun view(): List<Marqueur> {
        val empList:ArrayList<Marqueur> = ArrayList()
        val selectQuery = "SELECT * FROM ${dbHandler.TABLE_MARQUEUR}"
        val db = dbHandler.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
        }
        var posId: Int
        var posLat: Double
        var posLong: Double
        var visible: Int
        var lastUpdated: Long

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do{
                    posId = cursor.getInt(cursor.getColumnIndex("id").toInt())
                    posLat = cursor.getDouble(cursor.getColumnIndex("latitude").toInt())
                    posLong = cursor.getDouble(cursor.getColumnIndex("longitude").toInt())
                    visible = cursor.getInt(cursor.getColumnIndex("visible").toInt())
                    lastUpdated = cursor.getLong(cursor.getColumnIndex("last_updated").toInt())

                    val emp = Marqueur(posId,posLat.toString(), posLong.toString(),lastUpdated,visible)
                    empList.add(emp)
                } while (cursor.moveToNext())
            }
        }
        return empList
    }

    fun delete(id: Int): Int {
        val db = dbHandler.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, id)
        // Deleting Row
        val success = db.delete(dbHandler.TABLE_MARQUEUR,KEY_ID+"="+id,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun deleteAll(): Int{
        val db = dbHandler.writableDatabase
        // Deleting Row
        val success = db.delete(dbHandler.TABLE_MARQUEUR,null,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun update(emp: Marqueur): Int {
        val db = dbHandler.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.id)
        contentValues.put(KEY_LATITUDE, emp.latitude)
        contentValues.put(KEY_LONGITUDE, emp.longitude)
        contentValues.put(KEY_VISIBLE, emp.visible)
        contentValues.put(KEY_LASTUPDATED,emp.last_updated)

        // Updating Row
        val success = db.update(dbHandler.TABLE_MARQUEUR, contentValues,"id="+emp.id,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

}