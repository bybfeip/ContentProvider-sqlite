package com.accp;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemLongClickListener,OnItemClickListener{
	private Cursor cursor;
	private ListView lv;
	
	private EditText etname;
	private EditText ettel;
	private EditText etaddress;
	
	private Button btok;
	
	private SimpleCursorAdapter adapter;
	private ContentResolver contentResolver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etname=(EditText) findViewById(R.id.main_etname);
		ettel=(EditText) findViewById(R.id.main_ettel);
		etaddress=(EditText) findViewById(R.id.main_etaddress);
		btok=(Button) findViewById(R.id.main_btok);
		
		lv=(ListView) findViewById(R.id.main_lv);
		lv.setOnItemLongClickListener(this);
		contentResolver=getContentResolver();
		
		//获取查询的uri
		cursor=contentResolver.query(Uri.parse("content://com.provider.stu/student/10"), null, null, null, null);
		
		//使用数据库游标适配器创建
		adapter=new SimpleCursorAdapter(this, R.layout.item_layout, cursor, new String[]{"_id","sname","tel","address"}, new int[]{R.id.item_sid,R.id.item_sname,R.id.item_tel,R.id.item_address});
		
		//设置到ListView上
		lv.setAdapter(adapter);
		
		btok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String sname=etname.getText().toString();
				String tel=ettel.getText().toString();
				String address=etaddress.getText().toString();
				
				ContentValues values=new ContentValues();
				values.put("sname", sname);
				values.put("tel", tel);
				values.put("address", address);
				
				Uri uri=contentResolver.insert(Uri.parse("content://com.provider.stu/student"), values);
				if(uri!=null){
					Toast.makeText(MainActivity.this, "保存成功！", 4).show();
					
					//获取查询的uri
					cursor=contentResolver.query(Uri.parse("content://com.provider.stu/student"), null, null, null, null);
					
					//使用数据库游标适配器创建
					adapter=new SimpleCursorAdapter(MainActivity.this, R.layout.item_layout, cursor, new String[]{"_id","sname","tel","address"}, new int[]{R.id.item_sid,R.id.item_sname,R.id.item_tel,R.id.item_address});
					
					//设置到ListView上
					lv.setAdapter(adapter);
				}else{
					Toast.makeText(MainActivity.this, "保存失败！", 4).show();
				}
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		//长按更新某一条数据
		
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		//点击删除某一条代码省略
	}

}
