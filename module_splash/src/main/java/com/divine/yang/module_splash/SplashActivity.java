package com.divine.yang.module_splash;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import com.sankuai.waimai.router.Router;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {
    private VideoView videoAD;
    private ImageView imageAD;
    private Button btnTimer;

    private boolean firstLaunchApp;

    private String videoUriStr;
    private Handler mHandler = new Handler();
    private int count = 3;
    private boolean isJumpToNext;
    private Runnable mTimer = new Runnable() {
        @Override
        public void run() {
            btnTimer.setText("跳过(" + count + ")");
            if (count > 0) {
                mHandler.postDelayed(this, 1000);
                count--;
                btnTimer.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(videoUriStr)) {
                    btnTimer.setText("跳过");
                }
            } else {
                stopTimer();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    public void initView() {
        isJumpToNext = false;

        videoAD = findViewById(R.id.video_advertisement);
        imageAD = findViewById(R.id.image_advertisement);
        btnTimer = findViewById(R.id.timer_view);


        videoUriStr = "android.resource://" + getPackageName() + "/" + R.raw.video_advertisement;

        startVideo();
        startTimer();


        btnTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        firstLaunchApp = sharedPreferences.getBoolean("first_launch_app", true);
        if (firstLaunchApp) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("first_launch_app", true);
            editor.apply();
        }
    }

    protected String getMainRouteUri() {
        return null;
    }

    protected boolean getIsFirstLaunchApp() {
        return firstLaunchApp;
    }

    private void startTimer() {
        mHandler.post(mTimer);
    }

    private void stopTimer() {
        if (!isJumpToNext) {
            isJumpToNext = true;
            Router.startUri(this, getMainRouteUri());
            this.finish();
        }
    }

    private boolean startVideo() {
        if (TextUtils.isEmpty(videoUriStr)) {
            return false;
        }
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.video_advertisement);
        int duration = mediaPlayer.getDuration();
        if (duration % 1000 > 0) {
            count = duration / 1000 + 1;
        } else {
            count = duration / 1000;
        }
        videoAD.setVisibility(View.VISIBLE);
        videoAD.setVideoURI(Uri.parse(videoUriStr));
        videoAD.start();

        videoAD.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                startTimer();
            }
        });
        videoAD.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                stopTimer();
            }
        });
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mTimer);
        mTimer = null;
        if (null != videoAD) {
            videoAD.setOnPreparedListener(null);
            videoAD.setOnCompletionListener(null);
            videoAD.suspend();
        }
        btnTimer.setVisibility(View.GONE);
        imageAD.setVisibility(View.GONE);
        videoAD.setVisibility(View.GONE);
    }
}