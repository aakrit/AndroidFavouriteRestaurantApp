package com.bignerdranch.android.criminalintent.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//********************************
//This is a utility class that defines some constants
//and overrides two methods
//********************************
public class TodosSQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_TODOS = "todos"; //the name of the table
	public static final String COLUMN_ID = "_id";  //id, notice that it's name _id, this is a convention in sqlite
	public static final String COLUMN_COMMENT = "comment"; //column
	public static final String COLUMN_DETAIL = "detail"; //column

	private static final String DATABASE_NAME = "todos_db.db"; //the db name
	private static final int DATABASE_VERSION = 2; //the version. to upgrade (when schema changes) simply increment the version and it'll blow away existing data.

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_TODOS + "("
            + COLUMN_ID	+ " integer primary key autoincrement, "
			+ COLUMN_COMMENT + " text not null, " 
	        + COLUMN_DETAIL + " text not null);";

	//constructor
	public TodosSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	//this class has an onCreate method that we need to override, and pass in the create-string
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	// to upgrade, simply change the db_version
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.wtf(TodosSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);
		onCreate(db);
	}

} 