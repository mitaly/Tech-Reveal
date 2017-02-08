package com.techeducation.tech_reveal;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ContentProviderWords extends ContentProvider {
    SQLiteDatabase myDataBase;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        DataBaseHelper myDbHelper = new DataBaseHelper(getContext());

        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            myDbHelper.openDataBase();

        }catch(SQLException sqle){

            throw sqle;

        }
        return  false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor=myDataBase.query(uri.getLastPathSegment(), projection, selection, null, null, null,DictionaryUtil.COL_WORDS+" ASC");
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public class DataBaseHelper extends SQLiteOpenHelper {


        private final Context myContext;

        public DataBaseHelper(Context context) {

            super(context, DictionaryUtil.DB_NAME_WORDS, null, 1);
            this.myContext = context;
        }

        public void createDataBase() throws IOException{

            boolean dbExist = checkDataBase();

            if(dbExist){

            }else{

                this.getReadableDatabase();

                try {

                    copyDataBase();

                } catch (IOException e) {

                    throw new Error("Error copying database");

                }
            }

        }


        private boolean checkDataBase(){

            SQLiteDatabase checkDB = null;

            try{
                String myPath = DictionaryUtil.DB_PATH + DictionaryUtil.DB_NAME_WORDS;
                checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

            }catch(SQLiteException e){

                //database does't exist yet.

            }

            if(checkDB != null){

                checkDB.close();

            }

            return checkDB != null ? true : false;
        }

        private void copyDataBase() throws IOException{

            //Open your local db as the input stream
            InputStream myInput = myContext.getAssets().open(DictionaryUtil.DB_NAME_WORDS);

            // Path to the just created empty db
            String outFileName = DictionaryUtil.DB_PATH + DictionaryUtil.DB_NAME_WORDS;

            //Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }

            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();

        }

        public void openDataBase() throws SQLException {

            //Open the database
            String myPath = DictionaryUtil.DB_PATH + DictionaryUtil.DB_NAME_WORDS;
            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }

        @Override
        public synchronized void close() {

            if(myDataBase != null)
                myDataBase.close();

            super.close();

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }
}
