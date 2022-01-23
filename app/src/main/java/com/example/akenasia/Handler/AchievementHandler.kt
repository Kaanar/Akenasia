package com.example.akenasia.Handler

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException

class AchievementHandler(var context: Context): Handler  {

    var dbHandler: DatabaseHandler = DatabaseHandler(context)


    fun add(id: Int, desc: String):Long{
        val contentValues = ContentValues()
        val db= dbHandler.writableDatabase
        contentValues.put(KEY_ID, id)
        contentValues.put(KEY_DESC, desc)
        contentValues.put(KEY_UNLOCKED,0)

        // Inserting Row
        val success = db.insert(dbHandler.TABLE_ACHIEVEMENT, null,  contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //method to read a list of Achievements
    fun view(): HashMap<String,Int> {
        val empList:HashMap<String,Int> = HashMap()
        val selectQuery = "SELECT * FROM ${dbHandler.TABLE_ACHIEVEMENT} ORDER BY id"
        val db = dbHandler.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
        }
        var desc: String
        var unlocked: Int

        if (cursor != null && cursor.moveToFirst()) {
            do {
                desc = cursor.getString(cursor.getColumnIndex("description").toInt())
                unlocked = cursor.getInt(cursor.getColumnIndex("unlocked").toInt())
                empList[desc] = unlocked
            } while (cursor.moveToNext())
        }
        return empList
    }

    fun unlock(id: Int): Int {
        val db = dbHandler.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_UNLOCKED, 1)
        // Updating Row
        return db.update(dbHandler.TABLE_ACHIEVEMENT, contentValues, "id = $id ", null)
    }
}