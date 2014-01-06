package com.appiari.parkaspharmacy.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	String DB_PATH = null;
	private static String DB_NAME = "PAPDATABASE";
	private SQLiteDatabase myDataBase;
	private final Context myContext;
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.myContext = context;
		DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
	}
	
	public void createDataBase() throws IOException {
//		boolean dbExist = checkDataBase();
//
//		if (dbExist) {
//			// do nothing - database already exist
//			Toast.makeText(myContext, "WE ALREADY EXIST", Toast.LENGTH_LONG).show();
//		} else {
//
//			// By calling this method and empty database will be created into
//			// the default system path
//			// of your application so we are gonna be able to overwrite that
//			// database with our database.
			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
//		}

	}

	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			// database does't exist yet.
		}

		if (checkDB != null) {
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}

	private void copyDataBase() throws IOException {
		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;
		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);
		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	public void openDataBase() throws SQLException {
		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);
	}

	@Override
	public synchronized void close() {
		if (myDataBase != null)
			myDataBase.close();
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	public Cursor getLeaves(String flowerColor, String leafShape, String leafMargin, String leafArrangement) throws SQLException {
		String toBeSearched = "SELECT COMMON_NAME,SCIENTIFIC_NAME,IMAGE FROM PAPDATABASE WHERE ";
		
		if(flowerColor!=null){
			toBeSearched += " AND " +flowerColor;  
		}
		
		if(leafShape!=null){
			toBeSearched += " AND " +leafShape; 
		}
		
		if(leafMargin!=null){
			toBeSearched += " AND " +leafMargin;
		}
		
		if(leafArrangement!=null){
			toBeSearched += " AND " +leafArrangement;
		}
		
		toBeSearched = toBeSearched.replaceFirst(" AND ","");
		return myDataBase.rawQuery(toBeSearched,null);
	}
	
	public Cursor getColumnValues(String column, String columnResources){
		String columnQuery = column; 
		if(columnResources!=null){
			columnQuery += "," + columnResources;
		}
		return myDataBase.rawQuery("SELECT " + columnQuery + " FROM QUERYVARS WHERE " + column + " LIKE '%%'" , null);
	}
	
	public Cursor getLeavesByMedicinalProperties(String medicinalProperty){
		String toBeSearched = "SELECT COMMON_NAME,IMAGE FROM PAPDATABASE WHERE MEDICINAL_USES LIKE '%"+medicinalProperty+"%'";
		return myDataBase.rawQuery(toBeSearched,null);
	}
	
	public Cursor getByName(String name){
		String toBeSearched = "SELECT * FROM PAPDATABASE WHERE COMMON_NAME = "+"'"+name+"'";
		return myDataBase.rawQuery(toBeSearched,null);
	}
	
	public Cursor getColumns(){
		return myDataBase.rawQuery("PRAGMA table_info(PAPDATABASE)", null);
	}
	

}