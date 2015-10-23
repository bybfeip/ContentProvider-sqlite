package com.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.db.*;
public class StudentProviderContent extends ContentProvider {
	private DBConnection dbconn;
	private SQLiteDatabase db;
	
	//UriMatcher.NO_MATCH不匹配任何uri资源
	private static UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);
	
	// 注册向外部程序提供的Uri 
	static{
		matcher.addURI("com.provider.stu", "student", 0);
		matcher.addURI("com.provider.stu", "student/#", 1);
		
	}
	public StudentProviderContent() {
		super();
		
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		int num = 0;
		// 根据条件删除数据,并获取删除的行数
		if(matcher.match(arg0) ==1 ){
			num=dbconn.getWritableDatabase().delete("student", arg1, arg2);
		}
		 getContext().getContentResolver().notifyChange(arg0, null);
		
		return num;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues values) {
		int op=matcher.match(arg0);//匹配要执行的uri路径
		switch (op) {
		case 0://增加
		{
			/***********************************************/
			String sname=(String) values.get("sname");
			String tel=(String) values.get("tel");
			String address=(String) values.get("address");
			String sql="insert into student(sname,tel,address) values(?,?,?)";
			try {
				db.execSQL(sql,new String[]{sname,tel,address});
				System.out.println("保存成功！");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//long sid=db.insert("student", null, values);
			 
			return arg0.withAppendedPath(arg0, ""+1);
		}
			

		default:
			break;
		}
		return null;
	}
	// 连接数据库  
	@Override
	public boolean onCreate() {
		System.out.println("onCreate执行初始化操作......");
		dbconn=new DBConnection(getContext());
		db=dbconn.getWritableDatabase();//获取数据库语句执行对象
		return true;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		int op=matcher.match(arg0);
		switch (op) {
		case 0:
		{
			String sql="select * from student";
			Cursor cursor=db.rawQuery(sql, null);
			return cursor;
		}
			
		default:
			break;
		}
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
    SQLiteDatabase database=dbconn.getWritableDatabase();
		
		int num=0;
		
		if(matcher.match(arg0)==1){
			database.update("student", arg1, arg2, arg3);
		}
		return num;
	}

}
