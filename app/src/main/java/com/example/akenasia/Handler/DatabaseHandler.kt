package com.example.akenasia.Handler

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME,null,
    DATABASE_VERSION
), Handler {

    companion object {
        private val DATABASE_VERSION = 21
        private val DATABASE_NAME = "AkenasiaDatabase"
    }

        val TABLE_PLACE = "PlaceTable"
        val TABLE_POSITION = "PositionTable"
        val TABLE_ITEM = "ItemTable"
        val TABLE_BAG = "BagTable"
        val TABLE_PERSONNAGE = "PersonnnageTable"
        val TABLE_MARQUEUR = "MarqueurTable"
        val TABLE_STATS = "StatsTable"
        val TABLE_ACHIEVEMENT = "AchievementTable"




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
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ARGENT + " INTEGER," + KEY_HP + " DOUBLE," + KEY_ATT + " DOUBLE," + KEY_DEF + " DOUBLE," + KEY_ARMURE + " INTEGER,"
                + KEY_BOUCLIER + " INTEGER," + KEY_EPEE + " INTEGER," + KEY_CHAUSSURES + " INTEGER, FOREIGN KEY(" + KEY_ARMURE + ") REFERENCES " + TABLE_ITEM + "(" + KEY_ID + ")" +
                ", FOREIGN KEY(" + KEY_BOUCLIER + ") REFERENCES " + TABLE_ITEM + "(" + KEY_ID + ")" +
                ", FOREIGN KEY(" + KEY_EPEE + ") REFERENCES " + TABLE_ITEM + "(" + KEY_ID + ")" +
                ", FOREIGN KEY(" + KEY_CHAUSSURES + ") REFERENCES " + TABLE_ITEM + "(" + KEY_ID + "))")
        db?.execSQL(CREATE_PERSONNAGE_TABLE)

        val CREATE_MARQUEUR_TABLE =("CREATE TABLE " + TABLE_MARQUEUR + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_LATITUDE + " DOUBLE," + KEY_LONGITUDE + " DOUBLE," + KEY_VISIBLE + " INTEGER," + KEY_LASTUPDATED + " INTEGER" + ")")
        db?.execSQL(CREATE_MARQUEUR_TABLE)

        val CREATE_STATS_TABLE =("CREATE TABLE " + TABLE_STATS + "("
                + KEY_REFJOUEUR + " INTEGER PRIMARY KEY," + KEY_TOTALMARQUEUR + " INTEGER," + KEY_TOTALMONSTRE + " INTEGER," + KEY_TOTALITEM + " INTEGER" + ")")
        db?.execSQL(CREATE_STATS_TABLE)

        val CREATE_ACHIEVEMENT_TABLE =("CREATE TABLE " + TABLE_ACHIEVEMENT + "("
                + KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DESC + " TEXT," + KEY_UNLOCKED + " INTEGER" + ")")
        db?.execSQL(CREATE_ACHIEVEMENT_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_PLACE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_POSITION")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ITEM")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BAG")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PERSONNAGE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MARQUEUR")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_STATS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ACHIEVEMENT")

        onCreate(db)
    }
}