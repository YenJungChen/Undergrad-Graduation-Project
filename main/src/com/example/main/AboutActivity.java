package com.example.main;
//Intro of this group
//Actions of introduction page

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
public class AboutActivity extends Activity {
    private static final int MENU_ACTIVITY = 1;
    private Button backmenu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        click();
    }
    private Handler mHandler = new Handler(){
        @Override
		public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MENU_ACTIVITY:
                    Intent intent = new Intent();
                    intent.setClass(AboutActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
    //change scene to menu
    private void click(){
        backmenu=(Button)findViewById(R.id.backmenu);
        backmenu.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessageDelayed(MENU_ACTIVITY, 0);
            }           
        });
    }
}