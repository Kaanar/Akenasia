package com.example.akenasia.database

import android.app.Person
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
        private val DATABASE_VERSION = 16
        private val DATABASE_NAME = "AkenasiaDatabase"
        private val TABLE_PLACE = "PlaceTable"
        private val TABLE_POSITION = "PositionTable"
        private val TABLE_ITEM = "ItemTable"
        private val TABLE_BAG = "BagTable"
        private val TABLE_PERSONNAGE = "PersonnnageTable"

        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_LATITUDE = "latitude"
        private val KEY_LONGITUDE = "longitude"
        private val KEY_PARTIE = "partie"
        private val KEY_DESC = "description"
        private val KEY_HP = "HP"
        private val KEY_ATT = "ATT"
        private val KEY_DEF = "DEF"
        private val KEY_ARMURE = "armure"
        private val KEY_BOUCLIER = "bouclier"
        private val KEY_EPEE = "epee"
        private val KEY_CHAUSSURES = "chaussures"
        private val KEY_TYPE ="type"



    }

    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val CREATE_PLACE_TABLE = ("CREATE TABLE " + TABLE_PLACE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_LATITUDE + " DOUBLE," + KEY_LONGITUDE  + " DOUBLE" + ")")
        db?.execSQL(CREATE_PLACE_TABLE)

        val CREATE_POSITION_TABLE =("CREATE TABLE " + TABLE_POSITION + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_LATITUDE + " DOUBLE," + KEY_LONGITUDE + " DOUBLE," + KEY_PARTIE + " INTEGER" + ")")
        db?.execSQL(CREATE_POSITION_TABLE)

        val CREATE_ITEM_TABLE =("CREATE TABLE " + TABLE_ITEM + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TYPE + " TEXT," + KEY_NAME + " TEXT," + KEY_DESC + " TEXT," + KEY_ATT + " DOUBLE," + KEY_DEF + " DOUBLE" + ")")
        db?.execSQL(CREATE_ITEM_TABLE)

        val CREATE_BAG_TABLE =("CREATE TABLE " + TABLE_BAG + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT)" )
        db?.execSQL(CREATE_BAG_TABLE)

        val CREATE_PERSONNAGE_TABLE =("CREATE TABLE " + TABLE_PERSONNAGE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_HP + " DOUBLE," + KEY_ATT + " DOUBLE," + KEY_DEF + " DOUBLE," + KEY_ARMURE + " INTEGER,"
                + KEY_BOUCLIER + " INTEGER," + KEY_EPEE + " INTEGER," + KEY_CHAUSSURES + " INTEGER, FOREIGN KEY(" + KEY_ARMURE + ") REFERENCES " + TABLE_ITEM + "(" + KEY_ID + ")" +
                ", FOREIGN KEY(" + KEY_BOUCLIER + ") REFERENCES " + TABLE_ITEM + "(" + KEY_ID + ")" +
                ", FOREIGN KEY(" + KEY_EPEE + ") REFERENCES " + TABLE_ITEM + "(" + KEY_ID + ")" +
                ", FOREIGN KEY(" + KEY_CHAUSSURES + ") REFERENCES " + TABLE_ITEM + "(" + KEY_ID + "))")
        db?.execSQL(CREATE_PERSONNAGE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACE)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSITION)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BAG)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONNAGE)

        onCreate(db)
    }


    //method to create a Personnage
    fun createPersonnage():Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID,1)
        contentValues.put(KEY_HP, 20.0)
        contentValues.put(KEY_ATT, 12.0)
        contentValues.put(KEY_DEF, 10.0)
        contentValues.put(KEY_ARMURE,-1)
        contentValues.put(KEY_BOUCLIER, -1)
        contentValues.put(KEY_EPEE, -1)
        contentValues.put(KEY_CHAUSSURES, -1)


        // Inserting Row
        val success = db.insert(TABLE_PERSONNAGE, null,  contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //method to read a list of Personnage
    fun viewPersonnage():List<PersonnageTable>{
        val empList:ArrayList<PersonnageTable> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_PERSONNAGE "
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var persoId: Int
        var persoHp: Double
        var persoAtt: Double
        var persoDef: Double
        var armure: Int
        var bouclier: Int
        var epee: Int
        var chaussures: Int

        if (cursor != null && cursor.moveToFirst()) {
            do {
                persoId = cursor.getInt(cursor.getColumnIndex("id").toInt())
                persoHp = cursor.getDouble(cursor.getColumnIndex("HP").toInt())
                persoAtt = cursor.getDouble(cursor.getColumnIndex("ATT").toInt())
                persoDef = cursor.getDouble(cursor.getColumnIndex("DEF").toInt())
                armure = cursor.getInt(cursor.getColumnIndex("armure").toInt())
                bouclier = cursor.getInt(cursor.getColumnIndex("bouclier").toInt())
                epee = cursor.getInt(cursor.getColumnIndex("epee").toInt())
                chaussures = cursor.getInt(cursor.getColumnIndex("chaussures").toInt())

                val emp= PersonnageTable(persoId= persoId, persoHp= persoHp, persoAtt= persoAtt, persoDef=persoDef,armure=armure,
                    bouclier=bouclier,epee=epee,chaussures=chaussures)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }

    //methode to get a Personnage
    fun getPersonnage(id: Int): PersonnageTable {
        val db = this.readableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_PERSONNAGE WHERE $KEY_ID = $id"
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
        }
        var persoId: Int
        var persoHp: Double
        var persoAtt: Double
        var persoDef: Double
        var armure: Int
        var bouclier: Int
        var epee: Int
        var chaussures: Int

        if (cursor != null) {
            cursor.moveToFirst()
            persoId = cursor.getInt(cursor.getColumnIndex("id").toInt())
            persoHp = cursor.getDouble(cursor.getColumnIndex("HP").toInt())
            persoAtt = cursor.getDouble(cursor.getColumnIndex("ATT").toInt())
            persoDef = cursor.getDouble(cursor.getColumnIndex("DEF").toInt())
            armure = cursor.getInt(cursor.getColumnIndex("armure").toInt())
            bouclier = cursor.getInt(cursor.getColumnIndex("bouclier").toInt())
            epee = cursor.getInt(cursor.getColumnIndex("epee").toInt())
            chaussures = cursor.getInt(cursor.getColumnIndex("chaussures").toInt())
            val emp= PersonnageTable(persoId= persoId, persoHp= persoHp, persoAtt= persoAtt, persoDef=persoDef,armure=armure,
                bouclier=bouclier,epee=epee,chaussures=chaussures)
            return emp
        }
        exitProcess(0)
    }


    /*fun updatePersonnage(emp: Item)Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
    }*/



    ////////////////////////////////////////////////////////////////////////////////////////////////////
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
////////////////////////////////////////////////////////////////////////////////////////////////////
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

////////////////////////////////////////////////////////////////////////////////////////////////////

    //method to insert an Item
    fun addItem(emp: Item):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.getItemid())
        contentValues.put(KEY_TYPE, emp.getItemType())
        contentValues.put(KEY_NAME, emp.getItemName())
        contentValues.put(KEY_DESC, emp.getItemDesc())
        contentValues.put(KEY_ATT, emp.getItemAtt())
        contentValues.put(KEY_DEF, emp.getItemDef())

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
        val itemType: String
        val itemName: String
        val itemDesc: String
        val itemAtt: Double
        val itemDef: Double


        if (cursor != null) {
            if(cursor.moveToFirst()){
                itemId = cursor.getInt(cursor.getColumnIndex("id").toInt())
                itemType= cursor.getString(cursor.getColumnIndex("type").toInt())
                itemName = cursor.getString(cursor.getColumnIndex("name").toInt())
                itemDesc = cursor.getString(cursor.getColumnIndex("name").toInt())
                itemAtt = cursor.getDouble(cursor.getColumnIndex("ATT").toInt())
                itemDef = cursor.getDouble(cursor.getColumnIndex("DEF").toInt())

                return Item(itemId,itemType, itemName, itemDesc, itemAtt, itemDef)
            }
            else return Item(-1,"none", "vide", "", 0.0, 0.0)
        }
        else return Item(-1,"none", "vide", "", 0.0, 0.0)
    }
    //method to read an Item
    fun viewItem():List<Item> {
        val empList: ArrayList<Item> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_ITEM"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var itemId: Int
        var itemType: String
        var itemName: String
        var itemDesc: String
        var itemAtt: Double
        var itemDef: Double

        if (cursor != null) {
            if(cursor.moveToFirst()){
                do {
                    itemId = cursor.getInt(cursor.getColumnIndex("id").toInt())
                    itemType= cursor.getString(cursor.getColumnIndex("type").toInt())
                    itemName = cursor.getString(cursor.getColumnIndex("name").toInt())
                    itemDesc = cursor.getString(cursor.getColumnIndex("name").toInt())
                    itemAtt = cursor.getDouble(cursor.getColumnIndex("ATT").toInt())
                    itemDef = cursor.getDouble(cursor.getColumnIndex("DEF").toInt())

                    val emp = Item(
                        Itemid = itemId,
                        ItemType = itemType,
                        ItemName = itemName,
                        ItemDesc = itemDesc,
                        ItemAtt=itemAtt,
                        ItemDef=itemDef,
                        )
                    empList.add(emp)
                } while (cursor.moveToNext())
            }
        }
        return empList
    }

    fun deleteItem(emp: Int):Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_ITEM, "id=" + emp, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun updateItem(emp: Item):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.Itemid-1)
        contentValues.put(KEY_TYPE, emp.ItemType)
        contentValues.put(KEY_NAME, emp.ItemName)
        contentValues.put(KEY_DESC, emp.ItemDesc)
        contentValues.put(KEY_ATT, emp.ItemAtt)
        contentValues.put(KEY_DEF, emp.ItemDef)

        // Updating Row
        val success = db.update(TABLE_ITEM, contentValues,"id="+emp.Itemid,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

}