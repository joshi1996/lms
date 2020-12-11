package com.freepadhe.View;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.freepadhe.GsonModel.CourseContentList;
import com.freepadhe.R;
import com.freepadhe.utility.AlertClass;
import com.freepadhe.utility.SharePrefs;
import com.freepadhe.utility.Utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class YouTubeVideoPlayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,View.OnClickListener{
    YouTubePlayerView player;
    String videoURL = "http://blueappsoftware.in/layout_design_android_blog.mp4";
    CourseContentList mCourseContentList;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    private Handler mHandler;
    int videolength;  //in 30 second
    int buystatus;
    private LinearLayout mPlayButtonLayout,mPlayerLayout;
    private TextView mPlayTimeTextView;
    int count;

    private SeekBar mSeekBar;
    YouTubePlayer mYouTubePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.youtubeactivity);

        player = findViewById(R.id.youtube_player);
        mPlayButtonLayout = findViewById(R.id.video_control);
        mPlayerLayout = findViewById(R.id.player_layout);

        findViewById(R.id.play_video).setOnClickListener(this);
        findViewById(R.id.pause_video).setOnClickListener(this);

        mPlayTimeTextView = (TextView) findViewById(R.id.play_time);
        mSeekBar = (SeekBar) findViewById(R.id.video_seekbar);
        mSeekBar.setOnSeekBarChangeListener(mVideoSeekBarChangeListener);

        if(getIntent().getExtras() !=null){
          Bundle b=getIntent().getExtras();
            mCourseContentList= b.getParcelable("data");
            videoURL=b.getString("videourl");
            buystatus=b.getInt("buystatus");
        }

        initializePlayer();

        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //mPlayButtonLayout.setVisibility(View.VISIBLE);
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

                youTubePlayer.play();
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);

                mHandler = new Handler();
                mHandler.post(updateProgressAction);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        player.initialize("AIzaSyBQYR9rDQcMjPOaWdeZEIa2E0_RtKOHerA",onInitializedListener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect ContentBounds = new Rect();
        mPlayerLayout.getHitRect(ContentBounds);

        if (mPlayButtonLayout.isShown())
        {
            mPlayButtonLayout.setVisibility(View.GONE);
        }
        else
        {
            mPlayButtonLayout.setVisibility(View.VISIBLE);
        }
        return super.dispatchTouchEvent(ev);
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
                AlertClass.BaseAlert_done(YouTubeVideoPlayer.this, SharePrefs.getSetting(YouTubeVideoPlayer.this).getOrganizationName(), "please buy this course to watch complete video", getString(R.string.done), getString(R.string.no), false, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        mHandler.removeCallbacks(updateProgressAction);
                        Utils.hideKeyboard(YouTubeVideoPlayer.this);
                        YouTubeVideoPlayer.this.finish();

                    }
                }, null);
            } else {

                long delayMs = TimeUnit.SECONDS.toMillis(1);
                mHandler.postDelayed(updateProgressAction, delayMs);
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        int          height     = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200.0f,getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params     = (LinearLayout.LayoutParams) player.getLayoutParams();

        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            params.width  = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;

            player.requestLayout();
        }
        else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            params.width  = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = height;

            player.requestLayout();
        }

        super.onConfigurationChanged(newConfig);
    }

    YouTubePlayer.PlaybackEventListener mPlaybackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
            mHandler.removeCallbacks(updateProgressAction);
        }

        @Override
        public void onPlaying() {
            mHandler.postDelayed(updateProgressAction, 100);
            displayCurrentTime();
        }

        @Override
        public void onSeekTo(int arg0) {
            mHandler.postDelayed(updateProgressAction, 100);
        }

        @Override
        public void onStopped() {
            mHandler.removeCallbacks(updateProgressAction);
        }
    };

    YouTubePlayer.PlayerStateChangeListener mPlayerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
        }

        @Override
        public void onVideoStarted() {
            displayCurrentTime();
        }
    };

    SeekBar.OnSeekBarChangeListener mVideoSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            long lengthPlayed = (mYouTubePlayer.getDurationMillis() * progress) / 100;
            mYouTubePlayer.seekToMillis((int) lengthPlayed);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_video:
                if (null != mYouTubePlayer && !mYouTubePlayer.isPlaying())
                    mYouTubePlayer.play();
                break;
            case R.id.pause_video:
                if (null != mYouTubePlayer && mYouTubePlayer.isPlaying())
                    mYouTubePlayer.pause();
                break;
        }
    }

    private void displayCurrentTime() {
        if (null == mYouTubePlayer) return;
        String formattedTime = formatTime(mYouTubePlayer.getDurationMillis() - mYouTubePlayer.getCurrentTimeMillis());
        mPlayTimeTextView.setText(formattedTime);
    }

    @SuppressLint("DefaultLocale")
    private String formatTime(int millis) {
        int seconds = millis / 1000;
        int minutes = seconds / 60;
        int hours = minutes / 60;

        return (hours == 0 ? "--:" : hours + ":") + String.format("%02d:%02d", minutes % 60, seconds % 60);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if(mYouTubePlayer!=null && mYouTubePlayer.isPlaying()){
            mYouTubePlayer.pause();}

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (null == youTubePlayer) return;
        mYouTubePlayer = youTubePlayer;

        //displayCurrentTime();
        mYouTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        mYouTubePlayer.setOnFullscreenListener((YouTubePlayer.OnFullscreenListener) this);

        // Start buffering
        /*if (!b) {
            player.cueVideo(VIDEO_ID);
        }*/

        //player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        mPlayButtonLayout.setVisibility(View.VISIBLE);

        // Add listeners to YouTubePlayer instance
        mYouTubePlayer.setPlayerStateChangeListener(mPlayerStateChangeListener);
        mYouTubePlayer.setPlaybackEventListener(mPlaybackEventListener);
    };

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_LONG).show();
    }

    /*private void doLayout() {
        LinearLayout.LayoutParams playerParams =
                (LinearLayout.LayoutParams) player.getLayoutParams();
        if (fullscreen) {
            // When in fullscreen, the visibility of all other views than the player should be set to
            // GONE and the player should be laid out across the whole screen.
            playerParams.width = MATCH_PARENT;
            playerParams.height = MATCH_PARENT;

            //otherViews.setVisibility(View.GONE);
        }
    }



    @Override
    public void onFullscreen(boolean isFullscreen) {
        fullscreen = isFullscreen;
        doLayout();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        doLayout();
    }*/

}