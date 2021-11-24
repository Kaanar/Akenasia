package com.example.akenasia.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException
import kotlin.system.exitProcess

class DatabaseHandler(context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME,null,
    DATABASE_VERSION
) {
    companion object {
        private val DATABASE_VERSION = 6
        private val DATABASE_NAME = "AkenasiaDatabase"
        private val TABLE_PLACE = "PlaceTable"
        private val TABLE_POSITION = "PositionTable"
        private val TABLE_ITEM = "ItemTable"
        private val TABLE_BAG = "BagTable"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_LATITUDE = "latitude"
        private val KEY_LONGITUDE = "longitude"
        private val KEY_PARTIE = "partie"
        private val KEY_DESC = "description"
        private val KEY_DEFENSE = "pointDefense"
        private val KEY_ATTACK = "pointAttack"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val CREATE_PLACE_TABLE = ("CREATE TABLE " + TABLE_PLACE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_LATITUDE + " DOUBLE," + KEY_LONGITUDE  + " DOUBLE" + ")")
        db?.execSQL(CREATE_PLACE_TABLE)

        val CREATE_POSITION_TABLE =("CREATE TABLE " + TABLE_POSITION + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_LATITUDE + " DOUBLE," + KEY_LONGITUDE + " DOUBLE," + KEY_PARTIE + " INTEGER" + ")" )
        db?.execSQL(CREATE_POSITION_TABLE)

        val CREATE_ITEM_TABLE =("CREATE TABLE " + TABLE_ITEM + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_DESC + "TEXT" + KEY_ATTACK + "INTEGER" + KEY_DEFENSE + "INTEGER" + ")" )
        db?.execSQL(CREATE_ITEM_TABLE)

        val CREATE_BAG_TABLE =("CREATE TABLE " + TABLE_BAG + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_DESC + "TEXT" + ")" )
        db?.execSQL(CREATE_BAG_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSITION)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BAG)

        onCreate(db)
    }

    //method to insert a Position
    fun addPosition(emp: PositionTable):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.posId)
        contentValues.put(KEY_LATITUDE, emp.posLat)
        contentValues.put(KEY_LONGITUDE, emp.posLong)
        contentValues.put(KEY_PARTIE, emp.partie)

        // Inserting Row
        val success = db.insert(TABLE_POSITION, null,  contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //method to read a list of Position
    fun viewPosition(partie: Int):List<PositionTable>{
        val empList:ArrayList<PositionTable> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_POSITION WHERE $KEY_PARTIE=$partie"
        val db = this.readableDatabase
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

    //method to delete Positions refreshed during a game
    fun deletePosition(emp: Int):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_PARTIE, emp)
        // Deleting Row
        val success = db.delete(TABLE_POSITION,"partie="+emp,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //method to insert a Place
    fun addPlace(emp: Place):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.placeId)
        contentValues.put(KEY_NAME, emp.placeName) // EmpModelClass Name
        contentValues.put(KEY_LATITUDE, emp.placeLat)
        contentValues.put(KEY_LONGITUDE, emp.placeLong)

        // Inserting Row
        val success = db.insert(TABLE_PLACE, null,  contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //methode to get a Place
    fun getPlace(id: Int): Place {
        val db = this.readableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_PLACE WHERE $KEY_ID = $id"
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
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
            val emp= Place(placeId = placeId, placeName = placeName, placeLat = placeLat, placeLong = placeLong)
            return emp
        }
        exitProcess(0)
    }
    //method to read a Place
    fun viewPlace():List<Place>{
        val empList:ArrayList<Place> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_PLACE"
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

    fun updatePlace(emp: Place):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.placeId-1)
        contentValues.put(KEY_NAME, emp.placeName)
        contentValues.put(KEY_LATITUDE, emp.placeLat)
        contentValues.put(KEY_LONGITUDE, emp.placeLong)

        // Updating Row
        val success = db.update(TABLE_PLACE, contentValues,"id="+emp.placeId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to delete a Place
    fun deletePlace(emp: Place):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.placeId) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_PLACE,"id="+emp.placeId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }


    //method to insert an Item
    fun addItem(emp: Item):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.Itemid)
        contentValues.put(KEY_NAME, emp.ItemName) // EmpModelClass Name
        contentValues.put(KEY_DESC, emp.ItemDesc)
        contentValues.put(KEY_ATTACK, emp.ItemAttack)
        contentValues.put(KEY_DEFENSE, emp.ItemDefense)

        // Inserting Row
        val success = db.insert(TABLE_ITEM, null,  contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }


    //methode to get an Item
    fun getItem(id: Int):Item{
        val db = this.readableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_ITEM WHERE $KEY_ID = $id"
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
        }
        val itemId: Int
        val itemName: String
        val itemDesc: String
        val itemAttack : Int
        val itemDefense : Int

        if (cursor != null) {
            cursor.moveToFirst()
            itemId = cursor.getInt(cursor.getColumnIndex("id").toInt())
            itemName = cursor.getString(cursor.getColumnIndex("name").toInt())
            itemDesc = cursor.getString(cursor.getColumnIndex("description").toInt())
            itemAttack = cursor.getInt(cursor.getColumnIndex("attack").toInt())
            itemDefense = cursor.getInt(cursor.getColumnIndex("defense").toInt())
            return Item(itemId, itemName, itemDesc, itemAttack, itemDefense)
        }
        exitProcess(0)
    }
    //method to read an Item
    fun viewItem():List<Item>{
        val empList:ArrayList<Item> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_ITEM"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var itemId: Int
        var itemName: String
        var itemDesc: String
        var itemAttack : Int
        var itemDefense : Int

        if (cursor.moveToFirst()) {
            do {
                itemId = cursor.getInt(cursor.getColumnIndex("id").toInt())
                itemName = cursor.getString(cursor.getColumnIndex("name").toInt())
                itemDesc = cursor.getString(cursor.getColumnIndex("description").toInt())
                itemAttack = cursor.getInt(cursor.getColumnIndex("attack").toInt())
                itemDefense = cursor.getInt(cursor.getColumnIndex("defense").toInt())

                val emp= Item(itemId,itemName,itemDesc,itemAttack,itemDefense)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }

    /*
    //Method to update an Item
    fun updateItem(emp: ItemBag):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.Itemid-1)
        contentValues.put(KEY_NAME, emp.ItemName)
        contentValues.put(KEY_DESC, emp.ItemDesc)

        // Updating Row
        val success = db.update(TABLE_ITEM, contentValues,"id="+emp.Itemid,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to delete an Item
    fun deleteItem(emp: ItemBag):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.Itemid) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_ITEM,"id="+emp.Itemid,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

     */

    //method to insert an Item in a Bag
    fun addItemBag(emp: ItemBag):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.Itemid)
        contentValues.put(KEY_NAME, emp.ItemName) // EmpModelClass Name
        contentValues.put(KEY_DESC, emp.ItemDesc)

        // Inserting Row
        val success = db.insert(TABLE_BAG, null,  contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //methode to get an Item to a Bag
    fun getItemBag(id: Int):ItemBag{
        val db = this.readableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_BAG WHERE $KEY_ID = $id"
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
        }
        val itemId: Int
        val itemName: String
        val itemDesc: String


        if (cursor != null) {
            cursor.moveToFirst()
            itemId = cursor.getInt(cursor.getColumnIndex("id").toInt())
            itemName = cursor.getString(cursor.getColumnIndex("name").toInt())
            itemDesc = cursor.getString(cursor.getColumnIndex("description").toInt())
            return ItemBag(itemId, itemName, itemDesc)
        }
        exitProcess(0)
    }

    //method to read an Item in a Bag
    fun viewItemBag():List<ItemBag>{
        val empList:ArrayList<ItemBag> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_BAG"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var itemId: Int
        var itemName: String
        var itemDesc: String


        if (cursor.moveToFirst()) {
            do {
                itemId = cursor.getInt(cursor.getColumnIndex("id").toInt())
                itemName = cursor.getString(cursor.getColumnIndex("name").toInt())
                itemDesc = cursor.getString(cursor.getColumnIndex("description").toInt())


                val emp= ItemBag(itemId,itemName,itemDesc)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }

    fun deleteItemBag(emp: ItemBag):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.Itemid) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_BAG,"id="+emp.Itemid,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }



}