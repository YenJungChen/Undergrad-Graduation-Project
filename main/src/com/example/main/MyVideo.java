package com.example.main;
//This is a demo of how this system works
//a video view

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;
 
public class MyVideo extends VideoView {
 
 public MyVideo(Context context) {
  super(context);
  // TODO Auto-generated constructor stub
 }
 
 public MyVideo(Context context, AttributeSet attrs) {
  super(context, attrs);
  // TODO Auto-generated constructor stub
 }
 
 public MyVideo(Context context, AttributeSet attrs, int defStyle) {
  super(context, attrs, defStyle);
  // TODO Auto-generated constructor stub
 }
  
 @Override
 protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
 {
    int width = getDefaultSize(0, widthMeasureSpec);
    int height = getDefaultSize(0, heightMeasureSpec);
    setMeasuredDimension(width , height);
 }
}
