package com.example.main;
//use SQLite to store user's data in local

import static com.example.main.DbConstants.TABLE_NAME;
import static android.provider.BaseColumns._ID;
import static com.example.main.DbConstants.NAME;
import static com.example.main.DbConstants.HEIGHT;
import static com.example.main.DbConstants.WEIGHT;
import static com.example.main.DbConstants.BMI;
import static com.example.main.DbConstants.MIN;
import static com.example.main.DbConstants.KCAL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	public static final String DATABASE_PATH = "android.resource://com.example.main/" ;//+ R.raw.otakurider; 
	private final static String DATABASE_NAME = "otakubiker.db";
	private final static int DATABASE_VERSION = 1;
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
								  _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
								  NAME + " CHAR, " +
								  HEIGHT + " FLOAT, " +
								  WEIGHT + " FLOAT, " +
								  BMI + " FLOAT, " +
								  MIN + " FLOAT, " +
								  KCAL + " FLOAT);"; 
		db.execSQL(INIT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(DROP_TABLE);
		onCreate(db);
	}

}
