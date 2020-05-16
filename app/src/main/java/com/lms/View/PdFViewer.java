package com.lms.View;

import android.net.Uri;
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

public class PdFViewer extends AppCompatActivity {
    WebView webview;
    ProgressBar progressbar;
    ActivityPdflayoutBinding mbinding;
    String pdfFileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mbinding = DataBindingUtil.setContentView(this, R.layout.activity_pdflayout);
        if(getIntent() !=null){
            pdfFileName=getIntent().getStringExtra("videourl");
        }

        ThemeClass.changeHeaderColor(mbinding.llHeader,PdFViewer.this);

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
        webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdfFileName);

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