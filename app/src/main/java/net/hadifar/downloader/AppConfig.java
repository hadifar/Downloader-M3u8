package net.hadifar.downloader;

import android.app.Application;

import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

/**
 * Created by Amir on 6/18/2017 AD
 * Project : Downloader
 * Hadifar.net
 */

public class AppConfig extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        // TODO 2
        //important... only do it once...!!!
        // You just need to put this once in your code
        if (SettingsManager.isFirstRun(this))
            initFFmpeg();


    }

    private void initFFmpeg() {

        FFmpeg ffmpeg = FFmpeg.getInstance(this);
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {

                @Override
                public void onStart() {
                }

                @Override
                public void onFailure() {
                }

                @Override
                public void onSuccess() {
                }

                @Override
                public void onFinish() {
                }
            });
        } catch (FFmpegNotSupportedException e) {
            // Handle if FFmpeg is not supported by device
        }
    }
}
