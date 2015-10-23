package com.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnection extends SQLiteOpenHelper {
	private static final String dbname="person.db";
	private static final int version=2;
	
	public DBConnection(Context context) {
		super(context, dbname, null, version);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql="create table student(" +
				"  _id Integer primary key autoincrement," +
				"  sname varchar(50) not null," +
				"  tel varchar(50) not null," +
				"  address varchar(50)" +
				")";
		try {
			db.execSQL(sql);
			System.out.println("创建表成功！");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		arg0.execSQL("drop table student");
		onCreate(arg0);

	}

}
