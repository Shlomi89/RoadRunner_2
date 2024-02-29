package com.example.hw224a10357.Utility;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;

import com.example.hw224a10357.R;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SoundManager {
        private Context context;
        private Executor executor;
        private Handler handler;
        private MediaPlayer mediaPlayer;
        private int resID;

        public SoundManager(Context context, int resId) {
            this.context = context;
            this.executor = Executors.newSingleThreadExecutor();
            this.handler = new Handler(Looper.getMainLooper());
            this.resID = resId;
        }

        public void playSound(){
            executor.execute(() -> {
                mediaPlayer = MediaPlayer.create(context, this.resID);
                mediaPlayer.setVolume(1.0f,1.0f);
                mediaPlayer.start();
            });
        }
        public void stopSound(){
            if (mediaPlayer != null){
                executor.execute(() -> {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                });
            }
        }
    }