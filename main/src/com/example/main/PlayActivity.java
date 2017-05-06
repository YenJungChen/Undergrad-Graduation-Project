package com.example.main;
//setting default locations
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import static com.example.main.PreViewActivity.From;
 
public class PlayActivity extends Activity {
    private static final int MENU_ACTIVITY=1,INITPLAY_ACTIVITY=2;
    private Button backmenu, ice, gold, small;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);        
        click();
    }
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            Intent intent = new Intent();
            switch (msg.what) {
                case MENU_ACTIVITY:
                    intent.setClass(PlayActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case INITPLAY_ACTIVITY:
                    intent.setClass(PlayActivity.this, InitPlayActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
    private void click(){
        backmenu=(Button)findViewById(R.id.backmenu);
        backmenu.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated Stub
                mHandler.sendEmptyMessageDelayed(MENU_ACTIVITY, 0);
            }           
        });
        ice = (Button) findViewById(R.id.ice);
        ice.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated Stub
                From=String.valueOf(63.6157994)+","+String.valueOf(-19.9899938);
                mHandler.sendEmptyMessageDelayed(INITPLAY_ACTIVITY, 0);
            }           
        });
        gold = (Button) findViewById(R.id.gold);
        gold.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated Stub
                From=String.valueOf(37.8083448)+","+String.valueOf(-122.4767294);
                mHandler.sendEmptyMessageDelayed(INITPLAY_ACTIVITY, 0);
            }           
        });
        small = (Button) findViewById(R.id.small);
        small.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated Stub
                From=String.valueOf(22.338023)+","+String.valueOf(120.361639);
                mHandler.sendEmptyMessageDelayed(INITPLAY_ACTIVITY, 0);
            }           
        });
    }
}