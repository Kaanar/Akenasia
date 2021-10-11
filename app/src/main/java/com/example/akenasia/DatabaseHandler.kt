package com.example.akenasia

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException

class DatabaseHandler(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 5
        private val DATABASE_NAME = "AkenasiaDatabase"
        private val TABLE_CONTACTS = "PlaceTable"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_LATITUDE = "latitude"
        private val KEY_LONGITUDE = "longitude"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_LATITUDE + " DOUBLE," + KEY_LONGITUDE  + " DOUBLE" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        onCreate(db)
    }

    //method to insert data
    fun addPlace(emp: Place):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.placeId)
        contentValues.put(KEY_NAME, emp.placeName) // EmpModelClass Name
        contentValues.put(KEY_LATITUDE, emp.placeLat)
        contentValues.put(KEY_LONGITUDE, emp.placeLong)

        // Inserting Row
        val success = db.insert(TABLE_CONTACTS, null,  contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to read data
    fun viewPlace():List<Place>{
        val empList:ArrayList<Place> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
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
                placeId = cursor.getInt(cursor.getColumnIndex("id"))
                placeName = cursor.getString(cursor.getColumnIndex("name"))
                placeLat = cursor.getDouble(cursor.getColumnIndex("latitude"))
                placeLong = cursor.getDouble(cursor.getColumnIndex("longitude"))

                val emp= Place(placeId = placeId, placeName = placeName, placeLat = placeLat, placeLong = placeLong)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }

    fun updatePlace(emp: Place):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.placeId-1)
        contentValues.put(KEY_NAME, emp.placeName)
        contentValues.put(KEY_LATITUDE, emp.placeLat)
        contentValues.put(KEY_LONGITUDE, emp.placeLong)

        // Updating Row
        val success = db.update(TABLE_CONTACTS, contentValues,"id="+emp.placeId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to delete data
    fun deletePlace(emp: Place):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.placeId) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS,"id="+emp.placeId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
}
