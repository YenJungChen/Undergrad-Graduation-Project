package com.example.main;
//Instruction scene

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class TutorActivity extends Activity {
	private static final int MENU_ACTIVITY = 4;
	private ImageButton btn[]=new ImageButton[5];
	private ImageButton btn1[]=new ImageButton[5];
	private ImageView img[]=new ImageView[4];
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.tutor);
        btn[0]=(ImageButton)findViewById(R.id.user1);
        btn[1]=(ImageButton)findViewById(R.id.play1);
        btn[2]=(ImageButton)findViewById(R.id.view1);
        btn[3]=(ImageButton)findViewById(R.id.menu1);
        btn[4]=(ImageButton)findViewById(R.id.about1);
        
        btn1[0]=(ImageButton)findViewById(R.id.user2);
        btn1[1]=(ImageButton)findViewById(R.id.play2);
        btn1[2]=(ImageButton)findViewById(R.id.view2);
        btn1[3]=(ImageButton)findViewById(R.id.menu2);
        btn1[4]=(ImageButton)findViewById(R.id.about2);
        
        img[0]=(ImageView)findViewById(R.id.userintro);
        img[1]=(ImageView)findViewById(R.id.playintro);
        img[2]=(ImageView)findViewById(R.id.viewintro);
        img[3]=(ImageView)findViewById(R.id.aboutintro);
        click();
    }
    private void click(){    	
    	btn[0].setOnClickListener(new ImageButton.OnClickListener(){ //0 user
			@Override
			public void onClick(View v) {
				// TODO Auto-generated Stub
				if(btn[1].getVisibility()==View.VISIBLE){
					for(int i=0;i<5;i++){
						btn[i].setVisibility(View.INVISIBLE);
						btn[i].setClickable(false);
						btn1[i].setVisibility(View.VISIBLE);
						if(i<4)
							img[i].setVisibility(View.INVISIBLE);
					}
					img[0].setVisibility(View.VISIBLE);
				}else{
					for(int i=0;i<5;i++){
						btn1[i].setVisibility(View.INVISIBLE);
						btn[i].setVisibility(View.VISIBLE);
						btn[i].setClickable(true);
						if(i<4)
							img[i].setVisibility(View.INVISIBLE);
					}
				}
				btn1[0].setVisibility(View.INVISIBLE);
				btn[0].setVisibility(View.VISIBLE);
				btn[0].setClickable(true);
				
			}  		
    	});
    	
    	btn[1].setOnClickListener(new ImageButton.OnClickListener(){ //1 play
			@Override
			public void onClick(View v) {
				// TODO Auto-generated Stub
				if(btn[0].getVisibility()==View.VISIBLE){
					for(int i=0;i<5;i++){
						btn[i].setVisibility(View.INVISIBLE);
						btn[i].setClickable(false);
						btn1[i].setVisibility(View.VISIBLE);
						if(i<4)
							img[i].setVisibility(View.INVISIBLE);
					}
					img[1].setVisibility(View.VISIBLE);
				}else{
					for(int i=0;i<5;i++){
						btn1[i].setVisibility(View.INVISIBLE);
						btn[i].setVisibility(View.VISIBLE);
						btn[i].setClickable(true);
						if(i<4)
							img[i].setVisibility(View.INVISIBLE);
					}
				}
				btn1[1].setVisibility(View.INVISIBLE);
				btn[1].setVisibility(View.VISIBLE);
				btn[1].setClickable(true);
				
				
			}    		
    	});
    	
    	btn[2].setOnClickListener(new ImageButton.OnClickListener(){ //2 view
			@Override
			public void onClick(View v) {
				// TODO Auto-generated Stub
				if(btn[0].getVisibility()==View.VISIBLE){
					for(int i=0;i<5;i++){
						btn[i].setVisibility(View.INVISIBLE);
						btn[i].setClickable(false);
						btn1[i].setVisibility(View.VISIBLE);
						if(i<4)
							img[i].setVisibility(View.INVISIBLE);
					}
					img[2].setVisibility(View.VISIBLE);
				}else{
					for(int i=0;i<5;i++){
						btn1[i].setVisibility(View.INVISIBLE);
						btn[i].setVisibility(View.VISIBLE);
						btn[i].setClickable(true);
						if(i<4)
							img[i].setVisibility(View.INVISIBLE);
					}
				}
				btn1[2].setVisibility(View.INVISIBLE);
				btn[2].setVisibility(View.VISIBLE);
				btn[2].setClickable(true);
				
			}    		
    	});
    	
    	btn[3].setOnClickListener(new ImageButton.OnClickListener(){ //3 menu
			@Override
			public void onClick(View v) {
				// TODO Auto-generated Stub
				mHandler.sendEmptyMessageDelayed(MENU_ACTIVITY, 0);
			}    		
    	});
    	
    	btn[4].setOnClickListener(new ImageButton.OnClickListener(){ //4 about
			@Override
			public void onClick(View v) {
				// TODO Auto-generated Stub
				if(btn[0].getVisibility()==View.VISIBLE){
					for(int i=0;i<5;i++){
						btn[i].setVisibility(View.INVISIBLE);
						btn[i].setClickable(false);
						btn1[i].setVisibility(View.VISIBLE);
						if(i<4)
							img[i].setVisibility(View.INVISIBLE);
					}
					img[3].setVisibility(View.VISIBLE);
				}else{
					for(int i=0;i<5;i++){
						btn1[i].setVisibility(View.INVISIBLE);
						btn[i].setVisibility(View.VISIBLE);
						btn[i].setClickable(true);
						if(i<4)
							img[i].setVisibility(View.INVISIBLE);
					}
				}
				btn1[4].setVisibility(View.INVISIBLE);
				btn[4].setVisibility(View.VISIBLE);
				btn[4].setClickable(true);
				
			}    		
    	});    
    }
    private Handler mHandler = new Handler(){
        @Override
		public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MENU_ACTIVITY:
                    Intent intent = new Intent();
                    intent.setClass(TutorActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
}
