package com.example.main;
// This file contains Chinese text
// User info display

import static android.provider.BaseColumns._ID;
import static com.example.main.DbConstants.TABLE_NAME;
import static com.example.main.DbConstants.NAME;
import static com.example.main.DbConstants.HEIGHT;
import static com.example.main.DbConstants.WEIGHT;
import static com.example.main.DbConstants.BMI;
import static com.example.main.DbConstants.MIN;
import static com.example.main.DbConstants.KCAL;

import com.example.main.DBHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import static com.example.main.ViewActivity.secs;


public class UserActivity extends Activity {
	private static final int MENU_ACTIVITY = 1;
	private Button backmenu;
	private Button Update = null;
	private DBHelper dbhelper = null;
	private EditText editName = null;
	private EditText editHeight = null;
	private EditText editWeight = null;
	private TextView base = null;
	private TextView result = null;
	
	String name = "user";
	float weight = 50;
	float height = 170;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user);
		initView();
		openDatabase();
		click();
		show();
		if(secs > 5)
			update();
		Log.v("sec",String.valueOf(secs));
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
		base = (TextView) findViewById(R.id.txtBase);
		result = (TextView) findViewById(R.id.txtResult);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MENU_ACTIVITY:
				Intent intent = new Intent();
				intent.setClass(UserActivity.this, MenuActivity.class);
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

	private void update() {
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		ContentValues values = new ContentValues();	
		Cursor cursor = getCursor();
		if(cursor.moveToLast())
		{
			float id = cursor.getFloat(0);
			float weight = cursor.getFloat(3);
			values.put(MIN, String.valueOf(secs / 60));
			values.put(KCAL, String.valueOf(secs * weight * 9.8 / 3600));
			db.update(TABLE_NAME, values , _ID + "=" + id, null);
		}
		secs = 0;
		show();
	}

	private void show() {
		float mins = 0, kcals = 0;
		Cursor cursor = getCursor();
		StringBuilder baseData = new StringBuilder("基本資料:\n\n");
		StringBuilder resultData = new StringBuilder(
				"旅程資料:\n運動時間  * 體重  * 係數9.8 = 消耗的卡路里\n\n");
		while (cursor.moveToNext()) {
			String name = cursor.getString(1);
			float height = cursor.getFloat(2);
			float weight = cursor.getFloat(3);
			float bmi = cursor.getFloat(4);
			float min = cursor.getFloat(5);
			float kcal = cursor.getFloat(6);
			
			baseData.append("姓名 ");
			baseData.append(name).append(" ; 身高 ");
			baseData.append(height).append(" 公分; 體重 ");
			baseData.append(weight).append(" 公斤; BMI ");
			baseData.append(bmi).append(" 指數\n");

			mins = mins + min;
			kcals = kcals + kcal;
			resultData.append("使用者 ").append(name);
			resultData.append(" 的運動結果:  ");
			resultData.append(min).append(" 分鐘; 消耗 ");
			resultData.append(kcal).append(" 大卡\n");
		}
		base.setText(baseData);
		resultData.append("\n總計運動 ");
		resultData.append(mins).append(" 分鐘\n總計消耗 ");
		resultData.append(kcals).append(" 大卡\n");
		result.setText(resultData);
	}
}
