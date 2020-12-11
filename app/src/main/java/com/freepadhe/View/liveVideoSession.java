package com.freepadhe.View;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.freepadhe.GsonModel.CourseContentList;
import com.freepadhe.R;
import com.freepadhe.utility.AlertClass;
import com.freepadhe.utility.SharePrefs;
import com.freepadhe.utility.Utils;

import java.util.concurrent.TimeUnit;

public class liveVideoSession extends YouTubeBaseActivity {

    YouTubePlayerView player;
    String videoURL = "https://www.youtube.com/watch?v=7Cdh-HVukuM";
    CourseContentList mCourseContentList;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    private Handler mHandler;
    int videolength;  //in 30 second
    int buystatus;
    YouTubePlayer mYouTubePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_live_video_session);
        player = findViewById(R.id.youtube_player);

        videoURL = getIntent().getStringExtra("playVideo");

        /*if(getIntent().getExtras() !=null){
            Bundle b=getIntent().getExtras();
            mCourseContentList= b.getParcelable("data");
            videoURL=b.getString("videourl");
            buystatus=b.getInt("buystatus");
        }*/

        initializePlayer();

    }

    private void initializePlayer() {
        /*if(mCourseContentList.getContentType().equalsIgnoreCase("video") && mCourseContentList.getContentPlanType().equalsIgnoreCase("free") && mCourseContentList.getVideoType().equalsIgnoreCase("upload")){
            videolength=10;
        }else{
            videolength=-1;
        }*/

        onInitializedListener = new YouTubePlayer.OnInitializedListener(){

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                mYouTubePlayer=youTubePlayer;
                videoURL=videoURL.replace("https://www.youtube.com/watch?v=","");
                youTubePlayer.loadVideo(videoURL.trim());
                youTubePlayer.seekToMillis(0);
                youTubePlayer.play();

                mHandler = new Handler();
                mHandler.post(updateProgressAction);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        player.initialize("AIzaSyBQYR9rDQcMjPOaWdeZEIa2E0_RtKOHerA",onInitializedListener);
    }

    private final Runnable updateProgressAction = new Runnable() {
        @Override
        public void run() {
            updateProgress();
        }
    };

    private void updateProgress() {
        if(mYouTubePlayer!=null &&mYouTubePlayer.isPlaying()) {
            if (videolength == (mYouTubePlayer.getCurrentTimeMillis() / 1000)) {

                mYouTubePlayer.pause();
                AlertClass.BaseAlert_done(liveVideoSession.this, SharePrefs.getSetting(liveVideoSession.this).getOrganizationName(), "please buy this course to watch complete video", getString(R.string.done), getString(R.string.no), false, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        mHandler.removeCallbacks(updateProgressAction);
                        Utils.hideKeyboard(liveVideoSession.this);
                        liveVideoSession.this.finish();

                    }
                }, null);
            } else {

                long delayMs = TimeUnit.SECONDS.toMillis(1);
                mHandler.postDelayed(updateProgressAction, delayMs);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mYouTubePlayer!=null && mYouTubePlayer.isPlaying()){
            mYouTubePlayer.pause();}

    }
}