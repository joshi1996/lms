package com.freepadhe.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.freepadhe.GsonModel.CourseContentList;
import com.freepadhe.R;

public class WebVimeo extends AppCompatActivity {

    WebView webView;
    String videoURL = "http://blueappsoftware.in/layout_design_android_blog.mp4";
    CourseContentList mCourseContentList;
    private Handler mHandler;
    int videolength;  //in 30 second
    int buystatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_web_vimeo);
        webView = findViewById(R.id.webVimeo);

        if(getIntent().getExtras() !=null){
            Bundle b=getIntent().getExtras();
            mCourseContentList= b.getParcelable("data");
            videoURL=b.getString("videourl");
            buystatus=b.getInt("buystatus");
        }

        webView = new WebView(this);

        webView.getSettings().setJavaScriptEnabled(true);
        final Activity activity = this;

        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
        });

        webView .loadUrl(videoURL);
        setContentView(webView );
    }
}