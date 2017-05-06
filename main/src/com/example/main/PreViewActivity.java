package com.example.main;
// This file contains Chinese text
// check setting before play

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.provider.Settings;
 
@SuppressLint("JavascriptInterface")
public class PreViewActivity extends Activity{  
    private static final String MAP_URL = "file:///android_asset/preview.html";
    private WebView webView;	
    private EditText txtFrom;
    private Button btnSubmit,turnback,view;
    private boolean webviewReady = false;
    private static final int MENU_ACTIVITY = 1,VIEW_ACTIVITY=2;
    public static String From=null;
     
    public void locationchange() {
         
        txtFrom = (EditText) findViewById(R.id.From);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new Button.OnClickListener(){ 
            @Override
            public void onClick(View v) {                   
                // TODO Auto-generated method stub               
                if (webviewReady) {
                    Editable EFrom=txtFrom.getText();
                    From=EFrom.toString();
                    webView.loadUrl("javascript:codeAddress('" + From + "')");
                    Log.v("MyMessage", "onClick was executed."); 
                }
            }   
        });
        setupWebView();//set webview    
    }
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            Intent intent = new Intent();
            switch (msg.what) {
                case MENU_ACTIVITY:                    
                    intent.setClass(PreViewActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case VIEW_ACTIVITY:
                    intent.setClass(PreViewActivity.this, ViewActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
    private void click(){
        turnback=(Button)findViewById(R.id.turnback);
        turnback.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated Stub
                mHandler.sendEmptyMessageDelayed(MENU_ACTIVITY, 0);
                Log.i(Integer.toString(MENU_ACTIVITY),Integer.toString(MENU_ACTIVITY));
            }           
        });
        view=(Button)findViewById(R.id.gostart);
        view.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated Stub
                mHandler.sendEmptyMessageDelayed(VIEW_ACTIVITY, 0);
                Log.i(Integer.toString(VIEW_ACTIVITY),Integer.toString(VIEW_ACTIVITY));
            }           
        });
    }
	// location-based service check
    @Override
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocationManager status = (LocationManager) (this.getSystemService(Context.LOCATION_SERVICE));
        if (status.isProviderEnabled(LocationManager.GPS_PROVIDER) || status.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
             
        } else {
            Toast.makeText(this, "請開啟定位服務", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));    //open setting scene
        }
        setContentView(R.layout.preview); 
        this.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE );
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
         
        locationchange();
        click();
    } 
    /** Sets up the WebView object and loads the URL of the page **/
    private void setupWebView(){         
        webView.setWebViewClient(new WebViewClient(){ 
            @Override
            public void onPageFinished(WebView view, String url){
                webviewReady = true;
                Log.v("MyMessage", "setupWebView was executed."); 
            }
        });
        final JavaScriptInterface myJavaScriptInterface = new JavaScriptInterface(this);
        webView.addJavascriptInterface(myJavaScriptInterface, "JSP");
        webView.loadUrl(MAP_URL); 
    }
	
	//google maps street view check
    private class JavaScriptInterface {     
        private Context context;
        JavaScriptInterface(Context c){
          context=c;          
        }
        @JavascriptInterface
        public void showToast(String str) {
            Toast.makeText(PreViewActivity.this, str+"\n請更換地點", Toast.LENGTH_SHORT).show();
        }

    }
 
}