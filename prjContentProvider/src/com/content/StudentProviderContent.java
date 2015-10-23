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
	
	//UriMatcher.NO_MATCH��ƥ���κ�uri��Դ
	private static UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);
	
	// ע�����ⲿ�����ṩ��Uri 
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
		// ��������ɾ������,����ȡɾ��������
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
		int op=matcher.match(arg0);//ƥ��Ҫִ�е�uri·��
		switch (op) {
		case 0://����
		{
			/***********************************************/
			String sname=(String) values.get("sname");
			String tel=(String) values.get("tel");
			String address=(String) values.get("address");
			String sql="insert into student(sname,tel,address) values(?,?,?)";
			try {
				db.execSQL(sql,new String[]{sname,tel,address});
				System.out.println("����ɹ���");
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
	// �������ݿ�  
	@Override
	public boolean onCreate() {
		System.out.println("onCreateִ�г�ʼ������......");
		dbconn=new DBConnection(getContext());
		db=dbconn.getWritableDatabase();//��ȡ���ݿ����ִ�ж���
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
