package com.example.akenasia.Handler

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.example.akenasia.database.Item
import java.io.Serializable

class ItemHandler(var context: Context): Handler {

    var dbHandler: DatabaseHandler = DatabaseHandler(context)


    //method to insert an Item
    fun add(emp: Item):Long{
        val db = dbHandler.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.getItemid())
        contentValues.put(KEY_TYPE, emp.getItemType())
        contentValues.put(KEY_NAME, emp.getItemName())
        contentValues.put(KEY_DESC, emp.getItemDesc())
        contentValues.put(KEY_ATT, emp.getItemAtt())
        contentValues.put(KEY_DEF, emp.getItemDef())

        // Inserting Row
        val success = db.insert(dbHandler.TABLE_ITEM, null,  contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }


    //methode to get an Item
    fun get(id: Int): Item {
        val db = dbHandler.readableDatabase
        val selectQuery = "SELECT  * FROM ${dbHandler.TABLE_ITEM} WHERE $KEY_ID = $id"
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
    fun viewByType(type: Serializable):List<Item> {
        val empList: ArrayList<Item> = ArrayList()
        val selectQuery = "SELECT * FROM ${dbHandler.TABLE_ITEM} WHERE $KEY_TYPE = '$type'"
        val db = dbHandler.readableDatabase
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

    //method to read an Item
    fun view():List<Item> {
        val empList: ArrayList<Item> = ArrayList()
        val selectQuery = "SELECT  * FROM ${dbHandler.TABLE_ITEM}"
        val db = dbHandler.readableDatabase
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

    fun delete(emp: Int):Int {
        val db = dbHandler.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(dbHandler.TABLE_ITEM, "id=" + emp, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //On augmente les stats de l'item de 1 en atk et 1 en déf
    fun upItem(emp: Item): Int {
        val db = dbHandler.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.Itemid)
        contentValues.put(KEY_TYPE, emp.ItemType)
        contentValues.put(KEY_NAME, emp.ItemName)
        contentValues.put(KEY_DESC, emp.ItemDesc)
        contentValues.put(KEY_ATT, emp.ItemAtt+1)
        contentValues.put(KEY_DEF, emp.ItemDef+1)

        // Updating Row
        val success = db.update(dbHandler.TABLE_ITEM, contentValues,"id= "+ emp.Itemid,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun update(emp: Item):Int{
        val db = dbHandler.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.Itemid-1)
        contentValues.put(KEY_TYPE, emp.ItemType)
        contentValues.put(KEY_NAME, emp.ItemName)
        contentValues.put(KEY_DESC, emp.ItemDesc)
        contentValues.put(KEY_ATT, emp.ItemAtt)
        contentValues.put(KEY_DEF, emp.ItemDef)

        // Updating Row
        val success = db.update(dbHandler.TABLE_ITEM, contentValues,"id="+emp.Itemid,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
}