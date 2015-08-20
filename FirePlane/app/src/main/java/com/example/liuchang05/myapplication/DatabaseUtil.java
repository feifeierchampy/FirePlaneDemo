package com.example.liuchang05.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by liuchang05 on 2015-8-18.
 */
public class DatabaseUtil {
    //数据库的名字
    private static final String DATABASE_NAME = "rank_database";

    //数据库版本
    private static final int DATABASE_VERSION = 1;

    //数据库表名
    private static final String DATABASE_TABLE = "rank_table";

    //表中的字段名
    private static final String KEY_NAME = "username";
    private static final String KEY_GRAGE = "grade";
    private static final String KEY_ID = "userid";

    //建表语句
    private static final String CREATE_USER_TABLE =
            "create table " +
                    "" + DATABASE_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " +
                    KEY_NAME + " text not null, " + KEY_GRAGE + " integer not null);";

    private Context mContext = null;

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;


    //内部类
    private static class DatabaseHelper extends SQLiteOpenHelper{
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            Log.i("test:", "Creating Database: " + CREATE_USER_TABLE);
            db.execSQL(CREATE_USER_TABLE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("test:", "Upgrading database from version " + oldVersion + " to "
                    + newVersion);
        }
    }


    public DatabaseUtil(Context mContext)
    {
        this.mContext = mContext;
    }

    //创建或打开数据库连接
    public DatabaseUtil Open() throws SQLException
    {
        mDbHelper = new DatabaseHelper(mContext);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    //关闭数据库连接
    public void Close()
    {
        mDbHelper.close();
    }

    //插入数据
    public long InsertUser(String name, int grade)
    {
        ContentValues initialValues = new ContentValues();

        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_GRAGE, grade);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    //删除数据
    //public boolean DeleteUser(long id)
    //{

    //}

    //更新数据
    //public boolean UpdateUser(int id, String name, String grade)
    //{
        //ContentValues args = new ContentValues();
        //args.put(KEY_NAME, name)
    //}

    //得到所有数据按照分数降序排列
    public Cursor fetchAllData()
    {
//        return mDb.query(DATABASE_TABLE, new String[] {KEY_NAME, KEY_GRAGE},
//                null, null, null, null, null);
        return  mDb.rawQuery("SELECT "+ KEY_ID + " AS _id,"+KEY_NAME+","+KEY_GRAGE+" from "+DATABASE_TABLE +" order by "+KEY_GRAGE + " DESC", null);
    }

    //根据用户名得到数据
    public Cursor fetchDataByName(String name) throws SQLException
    {
        Cursor mCursor = mDb.query(true, DATABASE_TABLE, new String[] {KEY_NAME, KEY_GRAGE,KEY_ID},
                KEY_NAME + "=" + name, null, null, null, null, null);
        if(mCursor != null)
        {
            mCursor.moveToFirst();
        }
        return mCursor;
    }





}
