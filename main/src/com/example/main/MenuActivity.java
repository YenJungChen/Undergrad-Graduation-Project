package com.example.main;
// main menu

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

public class MenuActivity extends Activity {
	private static final int USER_ACTIVITY = 1;
	private static final int PLAY_ACTIVITY = 2;
	private static final int VIEW_ACTIVITY = 3;
	private static final int TUTOR_ACTIVITY = 4;
	private static final int ABOUT_ACTIVITY = 5;
	private ImageButton user,play,view,tutor,about;
	public static BluetoothAdapter mBluetoothAdapter = null;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.menu);
        click();
      //bluetooth open
        BT_on();
    }
	
	//bluetooth
    //=======================================================================
    private void BT_on(){
        mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if(!mBluetoothAdapter.isEnabled()){
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, 1);
        }
    }
	
	//change class when scene changed
    private Handler mHandler = new Handler(){
        @Override
		public void handleMessage(android.os.Message msg) {
        	Intent intent = new Intent();;
            switch (msg.what) {
            case USER_ACTIVITY:
            	intent.setClass(MenuActivity.this, UserActivity.class);
            	startActivity(intent);
            	finish();
            	break;
            case PLAY_ACTIVITY:
            	intent.setClass(MenuActivity.this, PlayActivity.class);
            	startActivity(intent);
            	finish();
            	break;
            case VIEW_ACTIVITY:
            	intent.setClass(MenuActivity.this, InitUserActivity.class);
            	startActivity(intent);
            	finish();
                break;
            case TUTOR_ACTIVITY:
            	intent.setClass(MenuActivity.this, TutorActivity.class);
                startActivity(intent);
                finish();
                break;
            case ABOUT_ACTIVITY:
            	intent.setClass(MenuActivity.this, AboutActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
            	break;
            }
        }
    };
	
	//buttons actions
    private void click(){
    	user=(ImageButton)findViewById(R.id.user);
    	user.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated Stub
				mHandler.sendEmptyMessageDelayed(USER_ACTIVITY, 0);
			}    		
    	});
    	play=(ImageButton)findViewById(R.id.play);
    	play.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated Stub
				mHandler.sendEmptyMessageDelayed(PLAY_ACTIVITY, 0);
			}    		
    	});
    	view=(ImageButton)findViewById(R.id.view);
    	view.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated Stub
				mHandler.sendEmptyMessageDelayed(VIEW_ACTIVITY, 0);
			}    		
    	});
    	tutor=(ImageButton)findViewById(R.id.tutor);
    	tutor.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated Stub
				mHandler.sendEmptyMessageDelayed(TUTOR_ACTIVITY, 0);
			}    		
    	});
    	about=(ImageButton)findViewById(R.id.about);
    	about.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated Stub
				mHandler.sendEmptyMessageDelayed(ABOUT_ACTIVITY, 0);
			}    		
    	});
    
    }
}
