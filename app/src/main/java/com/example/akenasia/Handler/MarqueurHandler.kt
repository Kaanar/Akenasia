package com.example.akenasia.Handler

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.example.akenasia.database.PositionTable
import com.google.android.gms.maps.model.LatLng
import java.util.HashMap
import kotlin.system.exitProcess

class MarqueurHandler(var context : Context): Handler {

    var dbHandler: DatabaseHandler = DatabaseHandler(context)

    //Method to get a Marker
    fun get(id: Int): Pair<LatLng,Int> {
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


        if (cursor != null) {
            cursor.moveToFirst()
            Lat = cursor.getDouble(cursor.getColumnIndex("latitude").toInt())
            Long = cursor.getDouble(cursor.getColumnIndex("longitude").toInt())
            visible = cursor.getInt(cursor.getColumnIndex("visible").toInt())
            val emp= LatLng(Lat,Long)
            val marqueur: Pair<LatLng,Int> = Pair(emp, visible)
            return marqueur
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

        // Inserting Row
        val success = db.insert(dbHandler.TABLE_MARQUEUR, null,  contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //method to read a list of Position
    fun view(): HashMap<LatLng,Int> {
        val empList:HashMap<LatLng,Int> = HashMap()
        val selectQuery = "SELECT * FROM ${dbHandler.TABLE_MARQUEUR} order by $KEY_VISIBLE desc"
        val db = dbHandler.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
        }
        var posLat: Double
        var posLong: Double
        var visible: Int


        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    posLat = cursor.getDouble(cursor.getColumnIndex("latitude").toInt())
                    posLong = cursor.getDouble(cursor.getColumnIndex("longitude").toInt())
                    visible = cursor.getInt(cursor.getColumnIndex("visible").toInt())
                    val emp= LatLng(posLat,posLong)
                    empList[emp] = visible
                    cursor.moveToNext()
                }
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

    fun update(id: Int, emp: LatLng, visible: Int): Int {
        val db = dbHandler.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, id)
        contentValues.put(KEY_LATITUDE, emp.latitude)
        contentValues.put(KEY_LONGITUDE, emp.longitude)
        contentValues.put(KEY_VISIBLE, visible)


        // Updating Row
        val success = db.update(dbHandler.TABLE_MARQUEUR, contentValues,"id="+id,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

}