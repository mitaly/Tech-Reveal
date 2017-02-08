package com.techeducation.tech_reveal;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class ContentProviderRecFav extends ContentProvider {
    DBHelper dbHelper;
    public  static SQLiteDatabase sqLiteDatabase;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int rows=sqLiteDatabase.delete(uri.getLastPathSegment(),selection,null);
        return  rows;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri dummyUri=null;
        String tableName=uri.getLastPathSegment();
        long rowId= sqLiteDatabase.insert(tableName,null,values);
        dummyUri=Uri.parse("dummyUri/"+rowId);

        return dummyUri;
    }

    @Override
    public boolean onCreate() {

        dbHelper=new DBHelper(getContext(),DictionaryUtil.DB_NAME,null,DictionaryUtil.DB_VERSION);
        sqLiteDatabase=dbHelper.getWritableDatabase();

        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor=sqLiteDatabase.query(uri.getLastPathSegment(), projection, null, null, null, null,DictionaryUtil.COL_ID+" DESC");
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DictionaryUtil.CREATE_TABLE_QUERY_RECENT);
            db.execSQL(DictionaryUtil.CREATE_TABLE_QUERY_FAVOURITES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS "+DictionaryUtil.TABLE_NAME_RECENT);
            db.execSQL("DROP TABLE IF EXISTS "+DictionaryUtil.TABLE_NAME_FAVOURITES);
            onCreate(db);
        }
    }
}
