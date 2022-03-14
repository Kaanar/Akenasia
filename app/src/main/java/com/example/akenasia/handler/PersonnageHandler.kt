package com.example.akenasia.handler

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.example.akenasia.database.Item
import com.example.akenasia.database.PersonnageTable
import kotlin.system.exitProcess

class PersonnageHandler(var context: Context): Handler {

    var dbHandler: DatabaseHandler = DatabaseHandler(context)
    var itemHandler: ItemHandler = ItemHandler(context)

    //method to create a Personnage
    fun create():Long{
        val db = dbHandler.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID,1)
        contentValues.put(KEY_HP, 20.0)
        contentValues.put(KEY_ATT, 12.0)
        contentValues.put(KEY_DEF, 10.0)
        contentValues.put(KEY_ARMURE,-1)
        contentValues.put(KEY_BOUCLIER, -1)
        contentValues.put(KEY_EPEE, -1)
        contentValues.put(KEY_CHAUSSURES, -1)
        contentValues.put(KEY_POINT, 0)
        contentValues.put(KEY_LEVEL, 0)

        contentValues.put(KEY_ARGENT, 0)


        // Inserting Row
        val success = db.insert(dbHandler.TABLE_PERSONNAGE, null,  contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //method to reset a Personnage's stats
    fun reset(emp: Int):Int{
        val db = dbHandler.writableDatabase
        val contentValues = ContentValues()
        val personnage = get(emp)
        val id = personnage.persoId

        contentValues.put(KEY_ID, personnage.persoId)
        contentValues.put(KEY_HP, 20.0)
        contentValues.put(KEY_ATT,12.0)
        contentValues.put(KEY_DEF, 10.0)
        contentValues.put(KEY_ARMURE, -1)
        contentValues.put(KEY_BOUCLIER, -1)
        contentValues.put(KEY_EPEE, -1)
        contentValues.put(KEY_CHAUSSURES, -1)
        contentValues.put(KEY_POINT, 0)
        contentValues.put(KEY_LEVEL, 0)


        // Updating Row
        val success = db.update(dbHandler.TABLE_PERSONNAGE, contentValues,"id = $id  ",null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }


    fun upArgent(emp: Int): Int {
        val db = dbHandler.writableDatabase
        val contentValues = ContentValues()
        val personnage = get(1)

        contentValues.put(KEY_ID, personnage.persoId)
        contentValues.put(KEY_HP, personnage.persoHp)
        contentValues.put(KEY_ATT, personnage.persoAtt)
        contentValues.put(KEY_DEF, personnage.persoDef)
        contentValues.put(KEY_ARMURE, personnage.armure)
        contentValues.put(KEY_BOUCLIER, personnage.bouclier)
        contentValues.put(KEY_EPEE, personnage.epee)
        contentValues.put(KEY_CHAUSSURES, personnage.chaussures)
        contentValues.put(KEY_ARGENT, emp)

        // Updating Row
        val success = db.update(dbHandler.TABLE_PERSONNAGE, contentValues,"id= ${personnage.persoId} ",null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun upPoint(emp: Int): Int {
        val db = dbHandler.writableDatabase
        val contentValues = ContentValues()
        val personnage= this.get(emp)

        contentValues.put(KEY_ID, emp)
        contentValues.put(KEY_HP, personnage.persoHp)
        contentValues.put(KEY_ATT,personnage.persoAtt)
        contentValues.put(KEY_DEF, personnage.persoDef)
        contentValues.put(KEY_ARMURE,personnage.armure)
        contentValues.put(KEY_BOUCLIER, personnage.bouclier)
        contentValues.put(KEY_EPEE, personnage.epee)
        contentValues.put(KEY_CHAUSSURES, personnage.chaussures)
        contentValues.put(KEY_POINT,personnage.points+3)
        contentValues.put(KEY_LEVEL, personnage.level)


        // Updating Row
        val success = db.update(dbHandler.TABLE_PERSONNAGE, contentValues,"id= $emp ",null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }


    fun upLevel(emp: Int): Int {
        val db = dbHandler.writableDatabase
        val contentValues = ContentValues()
        val personnage= this.get(emp)

        contentValues.put(KEY_ID, emp)
        contentValues.put(KEY_HP, personnage.persoHp)
        contentValues.put(KEY_ATT,personnage.persoAtt)
        contentValues.put(KEY_DEF, personnage.persoDef)
        contentValues.put(KEY_ARMURE,personnage.armure+2)
        contentValues.put(KEY_BOUCLIER, personnage.bouclier+2)
        contentValues.put(KEY_EPEE, personnage.epee+2)
        contentValues.put(KEY_CHAUSSURES, personnage.chaussures+2)
        contentValues.put(KEY_POINT,0)
        contentValues.put(KEY_LEVEL, personnage.level+1)


        // Updating Row
        val success = db.update(dbHandler.TABLE_PERSONNAGE, contentValues,"id= $emp ",null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun UpHP(emp:Int, hp: Double): Int{
        val db = dbHandler.writableDatabase
        val contentValues = ContentValues()
        val personnage= this.get(emp)

        contentValues.put(KEY_ID, emp)
        contentValues.put(KEY_HP,personnage.persoHp+hp)
        contentValues.put(KEY_ATT,personnage.persoAtt)
        contentValues.put(KEY_DEF, personnage.persoDef)
        contentValues.put(KEY_ARMURE,personnage.armure)
        contentValues.put(KEY_BOUCLIER, personnage.bouclier)
        contentValues.put(KEY_EPEE, personnage.epee)
        contentValues.put(KEY_CHAUSSURES, personnage.chaussures)
        contentValues.put(KEY_POINT,personnage.points)
        contentValues.put(KEY_LEVEL, personnage.level)

        // Updating Row
        val success = db.update(dbHandler.TABLE_PERSONNAGE, contentValues,"id= $emp ",null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun UpATT(emp:Int, att: Double): Int{
        val db = dbHandler.writableDatabase
        val contentValues = ContentValues()
        val personnage= this.get(emp)

        contentValues.put(KEY_ID, emp)
        contentValues.put(KEY_HP,personnage.persoHp)
        contentValues.put(KEY_ATT,personnage.persoAtt+att)
        contentValues.put(KEY_DEF, personnage.persoDef)
        contentValues.put(KEY_ARMURE,personnage.armure)
        contentValues.put(KEY_BOUCLIER, personnage.bouclier)
        contentValues.put(KEY_EPEE, personnage.epee)
        contentValues.put(KEY_CHAUSSURES, personnage.chaussures)
        contentValues.put(KEY_POINT,personnage.points)
        contentValues.put(KEY_LEVEL, personnage.level)

        // Updating Row
        val success = db.update(dbHandler.TABLE_PERSONNAGE, contentValues,"id= $emp ",null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun UpDEF(emp:Int, def: Double): Int{
        val db = dbHandler.writableDatabase
        val contentValues = ContentValues()
        val personnage= this.get(emp)

        contentValues.put(KEY_ID, emp)
        contentValues.put(KEY_HP,personnage.persoHp)
        contentValues.put(KEY_ATT,personnage.persoAtt)
        contentValues.put(KEY_DEF,personnage.persoDef+def)
        contentValues.put(KEY_ARMURE,personnage.armure)
        contentValues.put(KEY_BOUCLIER, personnage.bouclier)
        contentValues.put(KEY_EPEE, personnage.epee)
        contentValues.put(KEY_CHAUSSURES, personnage.chaussures)
        contentValues.put(KEY_POINT,personnage.points)
        contentValues.put(KEY_LEVEL, personnage.level)

        // Updating Row
        val success = db.update(dbHandler.TABLE_PERSONNAGE, contentValues,"id= $emp ",null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }


    //method to update your Personnage's affected items
    fun update(emp: Item):Int{
        val db = dbHandler.writableDatabase
        val contentValues = ContentValues()
        val id = emp.Itemid
        val personnage = get(1)
        //vérifie l'item sélectionné est ne change rien si déjà équipé//

        when (emp.getItemType()) {
            "ARMURE" -> {
                if(emp.getItemid().equals(personnage.armure)){
                    contentValues.put(KEY_ARMURE, personnage.armure)
                }
                else{
                    contentValues.put(KEY_ATT,personnage.persoAtt +(emp.ItemAtt - itemHandler.get(personnage.armure).getItemAtt()))
                    contentValues.put(KEY_DEF, personnage.persoDef +(emp.ItemDef - itemHandler.get(personnage.armure).getItemDef()))
                    contentValues.put(KEY_ARMURE, id)

                }
                contentValues.put(KEY_BOUCLIER, personnage.bouclier)
                contentValues.put(KEY_EPEE, personnage.epee)
                contentValues.put(KEY_CHAUSSURES, personnage.chaussures)
            }
            "EPEE" -> {
                if(emp.getItemid().equals(personnage.epee)){
                    contentValues.put(KEY_EPEE, personnage.epee)
                }
                else{
                    contentValues.put(KEY_ATT,personnage.persoAtt +(emp.ItemAtt - itemHandler.get(personnage.epee).getItemAtt()))
                    contentValues.put(KEY_DEF, personnage.persoDef +(emp.ItemDef - itemHandler.get(personnage.epee).getItemDef()))
                    contentValues.put(KEY_EPEE, id)
                }
                contentValues.put(KEY_ARMURE, personnage.armure)
                contentValues.put(KEY_BOUCLIER, personnage.bouclier)
                contentValues.put(KEY_CHAUSSURES, personnage.chaussures)
            }
            "BOUCLIER" ->{
                if(emp.getItemid().equals(personnage.bouclier)){
                    contentValues.put(KEY_BOUCLIER, personnage.bouclier)
                }
                else{
                    contentValues.put(KEY_ATT,personnage.persoAtt +(emp.ItemAtt - itemHandler.get(personnage.bouclier).getItemAtt()))
                    contentValues.put(KEY_DEF, personnage.persoDef +(emp.ItemDef - itemHandler.get(personnage.bouclier).getItemDef()))
                    contentValues.put(KEY_BOUCLIER,id)
                }
                contentValues.put(KEY_ARMURE, personnage.armure)
                contentValues.put(KEY_BOUCLIER,id)
                contentValues.put(KEY_EPEE, personnage.epee)
                contentValues.put(KEY_CHAUSSURES, personnage.chaussures)
            }
            "CHAUSSURES" -> {
                if(emp.getItemid().equals(personnage.chaussures)){
                    contentValues.put(KEY_CHAUSSURES, personnage.chaussures)
                }
                else{
                    contentValues.put(KEY_ATT,personnage.persoAtt +(emp.ItemAtt - itemHandler.get(personnage.chaussures).getItemAtt()))
                    contentValues.put(KEY_DEF, personnage.persoDef +(emp.ItemDef - itemHandler.get(personnage.chaussures).getItemDef()))
                    contentValues.put(KEY_CHAUSSURES,id)
                }
                contentValues.put(KEY_ARMURE, personnage.armure)
                contentValues.put(KEY_BOUCLIER, personnage.bouclier)
                contentValues.put(KEY_EPEE, personnage.epee)
                contentValues.put(KEY_CHAUSSURES, id)
            }
            else -> {
                contentValues.put(KEY_ARMURE, personnage.armure)
                contentValues.put(KEY_BOUCLIER, personnage.bouclier)
                contentValues.put(KEY_EPEE, personnage.epee)
                contentValues.put(KEY_CHAUSSURES, personnage.chaussures)
            }
        }
        contentValues.put(KEY_ID, personnage.persoId)
        contentValues.put(KEY_HP, personnage.persoHp)


        // Updating Row
        val success = db.update(dbHandler.TABLE_PERSONNAGE, contentValues,"id = 1 ",null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //method to read a list of Personnage
    fun view():List<PersonnageTable>{
        val empList:ArrayList<PersonnageTable> = ArrayList()
        val selectQuery = "SELECT * FROM ${dbHandler.TABLE_PERSONNAGE} "
        val db = dbHandler.readableDatabase
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
        var point : Int
        var level : Int

        var argent : Int

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
                point = cursor.getInt(cursor.getColumnIndex("point").toInt())
                level = cursor.getInt(cursor.getColumnIndex("level").toInt())
                argent = cursor.getInt(cursor.getColumnIndex("argent").toInt())

                val emp= PersonnageTable(persoId= persoId.toString(), persoHp= persoHp, persoAtt= persoAtt, persoDef=persoDef,armure=armure,
                    bouclier=bouclier,epee=epee,chaussures=chaussures, points=point, level=level,  argent = argent)

                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }

    //methode to get a Personnage
    fun get(id: Int): PersonnageTable {
        val db = dbHandler.readableDatabase
        val selectQuery = "SELECT  * FROM ${dbHandler.TABLE_PERSONNAGE} WHERE $KEY_ID = $id"
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
        }
        val persoId: Int
        val persoHp: Double
        val persoAtt: Double
        val persoDef: Double
        val armure: Int
        val bouclier: Int
        val epee: Int
        val chaussures: Int
        val point : Int
        val level : Int


        val argent: Int

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
            point = cursor.getInt(cursor.getColumnIndex("point").toInt())
            level = cursor.getInt(cursor.getColumnIndex("level").toInt())
            argent = cursor.getInt(cursor.getColumnIndex("argent").toInt())
            
            val emp= PersonnageTable(persoId= persoId.toString(), persoHp= persoHp, persoAtt= persoAtt, persoDef=persoDef,armure=armure,
                bouclier=bouclier,epee=epee,chaussures=chaussures, points=point, level=level, argent = argent)

            return emp
        }
        exitProcess(0)
    }
}