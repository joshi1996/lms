package com.freepadhe.View;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.freepadhe.GsonModel.CourseContentList;
import com.freepadhe.R;
import com.freepadhe.utility.AlertClass;
import com.freepadhe.utility.SharePrefs;
import com.freepadhe.utility.Utils;

import java.util.concurrent.TimeUnit;

public class VideoPlayer extends AppCompatActivity {
    PlayerView playerView;
    SimpleExoPlayer player;
    String videoURL = "http://blueappsoftware.in/layout_design_android_blog.mp4";
    CourseContentList mCourseContentList;
    private Handler mHandler;
    int videolength;  //in 30 second
    int buystatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_videolayout);
        playerView = findViewById(R.id.video_view);

        if(getIntent().getExtras() !=null){
          Bundle b=getIntent().getExtras();
            mCourseContentList= b.getParcelable("data");
            videoURL=b.getString("videourl");
            buystatus=b.getInt("buystatus");
        }

        initializePlayer();
    }

    private void initializePlayer() {
        if(mCourseContentList.getContentType().equalsIgnoreCase("video") && mCourseContentList.getContentPlanType().equalsIgnoreCase("paid") && mCourseContentList.getVideoType().equalsIgnoreCase("upload") && buystatus!=1){
            videolength=10;
        }
        else if(mCourseContentList.getContentType().equalsIgnoreCase("video") && mCourseContentList.getContentPlanType().equalsIgnoreCase("paid") && mCourseContentList.getVideoType().equalsIgnoreCase("vimeo") && buystatus!=1)
        {
            videolength=10;
        }
        else{
            videolength=-1;
        }

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());

        Uri uri = Uri.parse(videoURL);

        ExtractorMediaSource audioSource = new ExtractorMediaSource(
                uri,
                new DefaultDataSourceFactory(this, "MyExoplayer"),
                new DefaultExtractorsFactory(),
                null,
                null
        );

        player.prepare(audioSource);
        playerView.setPlayer(player);
        player.setPlayWhenReady(true);
        mHandler = new Handler();
        mHandler.post(updateProgressAction);

    }

    private final Runnable updateProgressAction = new Runnable() {
        @Override
        public void run() {
            updateProgress();
        }
    };

    private void updateProgress() {
        if(videolength==(player.getCurrentPosition()/1000)){
            player.stop();
            AlertClass.BaseAlert_done(VideoPlayer.this,SharePrefs.getSetting(VideoPlayer.this).getOrganizationName(), "please buy this course to watch complete video", getString(R.string.done), getString(R.string.no), false, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    mHandler.removeCallbacks(updateProgressAction);
                    Utils.hideKeyboard(VideoPlayer.this);
                    VideoPlayer.this.finish();

                }
            }, null);
        }else{

            long delayMs = TimeUnit.SECONDS.toMillis(1);
            mHandler.postDelayed(updateProgressAction, delayMs);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(player!=null){
            player.stop();}

    }
}