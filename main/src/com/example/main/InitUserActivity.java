package com.example.main;
// This file contains Chinese text
// User info setting initialization

import static android.provider.BaseColumns._ID;
import static com.example.main.DbConstants.KCAL;
import static com.example.main.DbConstants.MIN;
import static com.example.main.DbConstants.TABLE_NAME;
import static com.example.main.DbConstants.NAME;
import static com.example.main.DbConstants.HEIGHT;
import static com.example.main.DbConstants.WEIGHT;
import static com.example.main.DbConstants.BMI;

import com.example.main.DBHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InitUserActivity extends Activity {
	private static final int MENU_ACTIVITY = 1,PREVIEW_ACTIVITY=2;
	private Button backmenu;
	private Button Insert = null;
	private DBHelper dbhelper = null;
	private EditText editName = null;
	private EditText editHeight = null;
	private EditText editWeight = null;
	
	String name = "user";
	float weight = 50;
	float height = 170;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inituser);
		initView();
		openDatabase();
		click();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeDatabase();
	}

	private void openDatabase() {
		dbhelper = new DBHelper(this);
	}

	private void closeDatabase() {
		dbhelper.close();
	}
	
	private void initView() {
		editName = (EditText) findViewById(R.id.editName);
		editHeight = (EditText) findViewById(R.id.editHeight);
		editWeight = (EditText) findViewById(R.id.editWeight);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Intent intent = new Intent();
			switch (msg.what) {			
			case MENU_ACTIVITY:				
				intent.setClass(InitUserActivity.this, MenuActivity.class);
				startActivity(intent);
				finish();
				break;
			case PREVIEW_ACTIVITY:
                intent.setClass(InitUserActivity.this, PreViewActivity.class);
                startActivity(intent);
                finish();
                break;
			default:
				break;
			}
		}
	};

	private void click() {
		backmenu = (Button) findViewById(R.id.backmenu);
		backmenu.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				mHandler.sendEmptyMessageDelayed(MENU_ACTIVITY, 0);
			}
		});

		Insert = (Button) findViewById(R.id.Insert);
		Insert.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				add();
				mHandler.sendEmptyMessageDelayed(PREVIEW_ACTIVITY,0);
			}
		});
	}

	@SuppressWarnings("deprecation")
	private Cursor getCursor() {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		String[] columns = { _ID, NAME, HEIGHT, WEIGHT, BMI, MIN, KCAL };
		Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null,
				null, null);
		startManagingCursor(cursor);
		return cursor;
	}

    private void add(){
    	ContentValues values = new ContentValues();
    	if ("".equals(((Editable)editName.getText()).toString().trim())) { }
    	else
			name = ((Editable)editName.getText()).toString();
    	if ("".equals(editHeight.getText().toString().trim())) { }
    	else
    		height = Float.parseFloat(editHeight.getText().toString());
    	if ("".equals(editWeight.getText().toString().trim())) { }
    	else
		weight = Float.parseFloat(editWeight.getText().toString());		
		values.put(NAME, String.valueOf(name));
    	values.put(HEIGHT, String.valueOf(height));
    	values.put(WEIGHT, String.valueOf(weight));
    	values.put(BMI, String.valueOf(weight / height / height * 10000));
    	SQLiteDatabase db = dbhelper.getWritableDatabase();
    	db.insert(TABLE_NAME, null, values);
    	cleanEditText();
    }

	// private void show() {
		// Cursor cursor = getCursor();
		// StringBuilder baseData = new StringBuilder("基本資料:\n\n");
		// while (cursor.moveToNext()) {
			// String name = cursor.getString(1);
			// float height = cursor.getFloat(2);
			// float weight = cursor.getFloat(3);
			// float bmi = cursor.getFloat(4);
			
			// baseData.append("姓名 ");
			// baseData.append(name).append(" ; 身高 ");
			// baseData.append(height).append(" 公分; 體重 ");
			// baseData.append(weight).append(" 公斤; BMI ");
			// baseData.append(bmi).append(" 指數\n");
		// }
		// base.setText(baseData);
	// }

	private void cleanEditText() {
		editHeight.setText("");
		editWeight.setText("");
	}
}
