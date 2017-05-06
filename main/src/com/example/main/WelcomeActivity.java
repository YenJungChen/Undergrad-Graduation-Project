package com.example.main;
// Welcome scene

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeActivity extends Activity {
	private static final int MENU_ACTIVITY = 1;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        mHandler.sendEmptyMessageDelayed(MENU_ACTIVITY, 3000);
    }
    private Handler mHandler = new Handler(){
        @Override
		public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MENU_ACTIVITY:
                    Intent intent = new Intent();
                    intent.setClass(WelcomeActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
}
