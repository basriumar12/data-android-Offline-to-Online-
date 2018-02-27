package info.blogbasbas.fromoflinetoonline.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 26/02/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "NamesDb";
    public static final String TABLE_NAME = "names";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STATUS = "status";

    public static final int DB_VERSION = 1;


    public DatabaseHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+ TABLE_NAME
                    +"(" + COLUMN_ID+
                    " INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_NAME+
                    " VARCHAR, " +COLUMN_STATUS +
                    " TINYINT);";
                db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Persons";
        db.execSQL(sql);
        onCreate(db);

    }
    public  boolean addName (String name, int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,name);
        values.put(COLUMN_STATUS,status);
        db.insert(TABLE_NAME,null,values);
        db.close();
        return true;
    }
    public boolean updateNameStatus (int id, int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS,status);
        db.update(TABLE_NAME,values,COLUMN_ID +"=" +id,null);
        db.close();
        return true;
    }
    public Cursor getNames (){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_NAME + " ORDER BY " +COLUMN_ID + " ASC;";
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }
    public  Cursor getUnsyncedNames (){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_NAME + " WHERE " +COLUMN_STATUS + " = 0;";
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;

    }
}
