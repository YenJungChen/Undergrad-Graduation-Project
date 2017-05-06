package com.example.main;
// main code of game
// algorithms for street view
// connection with arduino
// music setting
  
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;
  
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.hardware.SensorManager;
import android.hardware.SensorListener;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import static com.example.main.PreViewActivity.From;
import static com.example.main.MenuActivity.mBluetoothAdapter;
  
public class ViewActivity extends Activity implements SensorListener{  
    private static final String MAP_URL = "file:///android_asset/view.html";
    private static final int USER_ACTIVITY = 1;
    private Button backmenu,back,forward;
    private WebView webView;
    private boolean webviewReady = false;   
    final String tag = "IBMEyes";
    SensorManager sm = null;
    TextView bluetooth = null;
    TextView timer = null;
    float X=0;
    int counter=0;
    int leftRight=0;
    float angle=0;
      
      
    //bluetooth adapter, address, socket, UUID variables
    //=========================================================
      //private BluetoothAdapter mBluetoothAdapter = null;
      private static String address = "98:D3:31:B2:C6:C0";
      private BluetoothSocket btSocket = null;
      private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //=========================================================
        
    //get Handler¡BThread of msg
    //=========================================================
      private Handler btHandler = null;
      private ConnectedThread mConnectedThread;
      InputStream mmInputStream = null;
      //=========================================================
      
    //count the diffence of time of StringBuilder, start time, end time, difference
        //=========================================================
        private StringBuilder sb = new StringBuilder();
        final int RECEIVE_MESSAGE = 2;
        private long start = 0L;
        private long now = 0L;
        private long diff = 0L;
          
        public static int secs = 0;
        private long pre = 0L;
     //=========================================================
         
      
      //music
      //=========================================================
        private static MediaPlayer music;
      //=========================================================
    @Override
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view); 
        setupWebView();     
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE );
        //Log.i("from",From);
          
        click();
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        bluetooth = (TextView) findViewById(R.id.bluetooth);
        timer = (TextView) findViewById(R.id.calorie);
          
        btHandler = new Handler();
          
        //
        btHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
               switch (msg.what) {
               case RECEIVE_MESSAGE:                                   //if msg received
                   byte[] readBuf = (byte[]) msg.obj;
                   String strIncom = new String(readBuf, 0, msg.arg1);  //strIncom = the whole received stream
  
                   sb.append(strIncom);                                                
                   int endOfLineIndex = sb.indexOf("\r\n");                           
                   if (endOfLineIndex > 0) {                                            
                       sb.delete(0, sb.length());                                      
                   }
                     
                   String tmp = strIncom.substring(strIncom.length()-1);   //tmp = update msg
                   bluetooth.setText(tmp);
  
  
                   if(tmp.equals("1")){
                       now = SystemClock.uptimeMillis();
                       if(now-pre>=600){         //if the last sig-current sig <600ms, keep sending msg
                           if(start==0L)
                               start=SystemClock.uptimeMillis();
                           else{
                               diff = now - start;
                               secs = (int) (diff / 1000);
                               Log.i("switch on","time diff= "+String.valueOf(secs));
                               webView.loadUrl("javascript:forward()");
                           }
                       }
                   }
  
                     
                     
                   pre = now;
  
                   timer.setText(String.format("%f", secs*0.1361));
                   //estimated kcal=kg(50)*9.8/3600
                     
                   break;
               }
           };
       };
         
       //music play
       //=====================================
       music = MediaPlayer.create(this,R.raw.bg);
       music.setLooping(true);
       if (music.isPlaying()) {
           music.stop();
           music.prepareAsync();
           music.seekTo(0);
        } else {
            music.start();
        }
     //=====================================
    }
      
      
      
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case USER_ACTIVITY:
                    Intent intent = new Intent();
                    intent.setClass(ViewActivity.this, UserActivity.class);
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
                // TODO auto Stub
                mHandler.sendEmptyMessageDelayed(USER_ACTIVITY, 0);
            }           
        });
    }
    public int [] getLeftRightAngle(){
        int [] arr={leftRight,(int) angle};
        return arr;
    }
    /** Sets up the WebView object and loads the URL of the page **/
    private void setupWebView(){
          
        back = (Button) findViewById(R.id.turnback);
        back.setOnClickListener(new Button.OnClickListener(){ 
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:turnback()");
            }
        });
        forward = (Button) findViewById(R.id.forward);
        forward.setOnClickListener(new Button.OnClickListener(){ 
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:forward()");
            }
        });
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        //Wait for the page to load then send the location information
        webView.setWebViewClient(new WebViewClient(){ 
            @Override
            public void onPageFinished(WebView view, String url) 
            {
                webviewReady = true;//webview loaded
                Log.v("MyMessage", "setupWebView was executed."); 
            }
        });
        webView.loadUrl(MAP_URL); 
          
        Log.v("MyMessage", "webView.loadUrl was executed."); 
    }
    @Override
    public void onSensorChanged(int sensor, float[] values) {
        // TODO auto Stub
        if(webviewReady){
              
            counter++;
            synchronized (this) {
                //Log.d(tag, "onSensorChanged: " + sensor + ", x: " + values[0] + ", y: " + values[1] + ", z: " + values[2]);
				
                //Sensor orientation
                if(counter<100){
                    if (sensor == SensorManager.SENSOR_ORIENTATION) {
                        X=values[0];
                        webView.loadUrl("javascript:codeAddress('"+From+"')");
                    }
                }
                else{
                    if (sensor == SensorManager.SENSOR_ORIENTATION) {
                        float X2=values[0];
                        //bike handle right turn 
                        if((X2-X)>0){
                            if((X2-X)<180){
                                leftRight=1;
                                angle=X2-X;                                  
                            }
                            else{
                                leftRight=-1;
                                angle=360-(X2-X);
                            }
                        }
                        //bike handle left turn 
                        else if((X2-X)<0){
                            if((X2-X)>-180){
                                leftRight=-1;
                                angle=X-X2;
                            }
                            else{
                                leftRight=1;
                                angle=360-(X-X2);
                            }
                        }
                        if(leftRight==1 && (angle>=23 && angle<68) ){
                            webView.loadUrl("javascript:setPano2link('"+"8"+"')");
                        }
                        else if(leftRight==1 && (angle>=68 && angle<113) ){
                            webView.loadUrl("javascript:setPano2link('"+"7"+"')");
                        }
                        else if(leftRight==1 && (angle>=113 && angle<158) ){
                            webView.loadUrl("javascript:setPano2link('"+"6"+"')");
                        }                        
                           
                        else if(angle>=158 && angle<=180){
                            webView.loadUrl("javascript:setPano2link('"+"5"+"')");
                        }
       
                        else if(leftRight==-1 && (angle>=23 && angle<68) ){
                            webView.loadUrl("javascript:setPano2link('"+"2"+"')");
                        }
                        else if(leftRight==-1 && (angle>=68 && angle<113) ){
                            webView.loadUrl("javascript:setPano2link('"+"3"+"')");
                        }
                        else if(leftRight==-1 && (angle>=113 && angle<158) ){
                            webView.loadUrl("javascript:setPano2link('"+"4"+"')");
                        }                        
                           
                        else{
                            webView.loadUrl("javascript:setPano2link('"+"1"+"')");
                        }
                         
                    }
                }
            }
        }
          
    }
      
       
       
      
    //stream receice thread
    //===================================================================
    private class ConnectedThread extends Thread {
            private final InputStream mmInStream;
   
            public ConnectedThread(BluetoothSocket socket) {
               InputStream tmpIn = null;
       
               // input receive from socked
               try {
                   tmpIn = socket.getInputStream();
               } catch (IOException e) { }
       
               mmInStream = tmpIn;
           }
   
           public void run() {
               byte[] buffer = new byte[256];
               int bytes; //lenght of buffer
       
               // fetch msg
               while (true) {
                       try {
                           //read fetched msg
                           bytes = mmInStream.read(buffer);        // get buffer lenght
                           btHandler.obtainMessage(RECEIVE_MESSAGE, bytes, -1, buffer).sendToTarget();     // send msg to Handler queue
                       } catch (IOException e) {
                           break;
                       }
               }
           }
      }
    //===================================================================
      
      
    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {
        // TODO auto Stub
        Log.d(tag,"onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
    }
      
    @Override
    public void onResume() {
        super.onResume();
        sm.registerListener(this, 
                SensorManager.SENSOR_ORIENTATION |
                SensorManager.SENSOR_ACCELEROMETER,
                SensorManager.SENSOR_DELAY_NORMAL);
          
          
        // device address setting
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
  
        //socket connect
         try {
             btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
              btSocket.connect();
         } 
         catch (IOException e) {
         }
       // create stream to receive msg
          
       mConnectedThread = new ConnectedThread(btSocket);
       mConnectedThread.start();
      
    }
  
    @Override
    protected void onStop() {
        sm.unregisterListener(this);
        super.onStop();
        if (music.isPlaying()) {
           music.stop();
           music.prepareAsync();
           music.seekTo(0);
        }
         
        try {
            btSocket.close();
        } catch (IOException e) {
        }
    } 
}