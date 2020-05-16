package com.lms.View;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.lms.R;
import com.lms.databinding.ActivityPdflayoutBinding;
import com.lms.utility.ThemeClass;

public class WebViewer extends AppCompatActivity {
    WebView webview;
    ProgressBar progressbar;
    ActivityPdflayoutBinding mbinding;

    String datastr;
    String url=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        mbinding = DataBindingUtil.setContentView(this, R.layout.activity_pdflayout);
        if(getIntent() !=null){
            if(getIntent().hasExtra("data"))
            datastr=getIntent().getStringExtra("data");
            else{
               url=getIntent().getStringExtra("url");
            }
        }

        ThemeClass.changeHeaderColor(mbinding.llHeader,WebViewer.this);

        TextView tv_header=(TextView) mbinding.llHeader.findViewById(R.id.tv_header);
        tv_header.setText(getString(R.string.content));
        LinearLayout ll_back=(LinearLayout) mbinding.llHeader.findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();


            }
        });
        ll_back.setVisibility(View.VISIBLE);

        initializePlayer();
    }

    private void initializePlayer() {
        webview = (WebView)findViewById(R.id.pdfView);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        webview.getSettings().setJavaScriptEnabled(true);
        if(url!=null){
            webview.loadUrl(url);

        }
        else
        webview.loadData(datastr, "text/html; charset=UTF-8", null);

        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                progressbar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();


    }
}