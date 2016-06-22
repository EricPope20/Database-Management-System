package info.androidhive.materialdesign.activity;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Sqlitehelper {
	
	public static final String key_rowid = "_id";
    public static final String key_details_info_column = "detinfo";

	public static final String dbase_name = "EricDatabase";
	public static final String dtable_name = "Eric";

	public static final int dbase_versn = 1;

	private Dbhelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourdDase;

	private static class Dbhelper extends SQLiteOpenHelper {

		public Dbhelper(Context context) {
			super(context, dbase_name, null, dbase_versn);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + dtable_name);
			onCreate(db);
		}
	}

    public void CreateTable(String tabName){
        ourdDase.execSQL("CREATE TABLE IF NOT EXISTS " + tabName + " (" +
                        key_rowid + " INTEGER PRIMARY KEY AUTOINCREMENT, " + key_details_info_column + " TEXT); ");
    }

	public Sqlitehelper(Context c) {
		ourContext = c;
	}

	public Sqlitehelper open() throws SQLException {
		ourHelper = new Dbhelper(ourContext);
		ourdDase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourHelper.close();
	}

    //this is to store My friends list
    public long storeData(String tabName,String data){
        ContentValues cv = new ContentValues();
        cv.put(key_details_info_column, data);

        return ourdDase.insert(tabName, null, cv);
    }

    public ArrayList<String> retrieveData(String tab){

        ArrayList<String> returnData =  new ArrayList<>();

        Cursor cursor = ourdDase.rawQuery("Select * from " + tab, null);
        if(cursor != null) {
			cursor.moveToFirst();

			for (int i = 0; i < cursor.getCount(); i++) {
				//Log.d("dataaaaaaaaaaaa", cursor.getString(1));

					String data = cursor.getString(1);
					returnData.add(data);

					cursor.moveToNext();
				}

            cursor.close();
        }
        return returnData;
    }

	public void dropTable(String table_name) {
		ourdDase.execSQL("DROP TABLE IF EXISTS " + table_name);
	}

    public boolean CheckTableExists(String tabName){
        Cursor cursor = ourdDase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tabName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
}
