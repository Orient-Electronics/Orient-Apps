package com.orient.profresh;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodDatabaseOpenHelper extends SQLiteOpenHelper{
	
		private SQLiteDatabase myDB;
		private final Context myContext;
		
		
		
		public FoodDatabaseOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		this.myContext=context;
		this.myDB=this.getReadableDatabase();
		
	}

	
		/**
		 * Constructor Takes and keeps a reference of the passed context in
		 * order to access to the application assets and resources.
		 * 
		 * @param context
		 */
	

		/**
		 * Creates a empty database on the system and rewrites it with your own
		 * database.
		 * */
		public void createDataBase() throws IOException {

			boolean dbExist = checkDataBase();

			if (dbExist) {
				// do nothing - database already exist
			} else {

				// By calling this method and empty database will be created
				// into the default system path
				// of your application so we are gonna be able to overwrite that
				// database with our database.
				this.getReadableDatabase();
				
				try {

					copyDataBase();

				} catch (IOException e) {

					//throw new Error("Error copying database");
					throw new RuntimeException(e);

				}
			}

		}

		/**
		 * Check if the database already exist to avoid re-copying the file each
		 * time you open the application.
		 * 
		 * @return true if it exists, false if it doesn't
		 */
		private boolean checkDataBase() {SQLiteDatabase checkDB = null;
		 
			try{
			String myPath = FoodMetaData.DB_PATH +FoodMetaData.DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		 
			}catch(SQLiteException e){
		 
		//database does't exist yet.
		 
			}
		 
			if(checkDB != null){
		 
			checkDB.close();
		 
			}
		 
			return checkDB != null ? true : false;
		}

		
		private void copyDataBase() throws IOException {

			// Open your local db as the input stream
			InputStream myInput = myContext.getAssets().open(FoodMetaData.DB_NAME);

			// Path to the just created empty db
			String outFileName = FoodMetaData.DB_PATH +FoodMetaData.DB_NAME;

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
			String myPath = FoodMetaData.DB_PATH + FoodMetaData.DB_NAME;
			myDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		}

		@Override
		public synchronized void close() {

			if (myDB != null)
				myDB.close();

			super.close();

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			this.myDB=db;
			try {
				this.insertFromFile(this.myContext,R.raw.food);
				this.insertFromFile(this.myContext, R.raw.vitamin);
				this.insertFromFile(this.myContext, R.raw.icons);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
		
		public int insertFromFile(Context context, int resourceId) throws IOException {

			
			
			// Reseting Counter
		    int result = 0;

		    // Open the resource
		    InputStream insertsStream = context.getResources().openRawResource(resourceId);
		    BufferedReader insertReader = new BufferedReader(new InputStreamReader(insertsStream));

		    // Iterate through lines (assuming each insert has its own line and theres no other stuff)
		    while (insertReader.ready()) {
		        String insertStmt = insertReader.readLine();
		        this.myDB.execSQL(insertStmt);
		        result++;
		    }
		    insertReader.close();

		    // returning number of inserted rows
		    return result;
		}


}

